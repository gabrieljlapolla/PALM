package palm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class PALM {
    // Stores items associated with logged-in user
    private static ItemManager<Item> items = new ItemManager<>();

    public static ItemManager<Item> getItems() {
        return items;
    }


    /**
     * Writes all items to a file using user's password to encrypt them
     *
     * @param filename    Name to save file to
     * @param itemsToSave Stream of Items to be written to file
     * @return True if success, false if failure
     * @preconditions Items must contain at least one item
     * @postconditions All items are saved to file
     */
    public static boolean writePlainTextItemsToFile(String filename, Stream<Item> itemsToSave) {
        // TODO: filtering of items to export
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename + ".txt"))) {
            for (Item item : itemsToSave.toList()) {
                bw.append(item.toString());
                bw.append("%n");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Could not export items to file.");
        }
        return false;
    }

    /**
     * Creates a thread to automatically save user items after a certain delay
     * Automatically stops when program exits
     *
     * @param username Current user
     * @param password Password used to encrypt items
     * @preconditions User must be logged in and items loaded
     * @postconditions Thread running that automatically saves items
     */
    private static void automaticSave(String username, String password) {
        final int DELAY = 5; // Delay in seconds
        PALMDB pdb = new PALMDB();
        // Create thread to execute after a delay to save items
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            // Make daemon so Thread is killed when main program exits
            t.setDaemon(true);
            return t;
        });

        ses.scheduleWithFixedDelay(() ->
                pdb.writeItems(username, password, items), 0, DELAY, TimeUnit.SECONDS);
    }

    /**
     * Searches for a specific credential by name and prints it if found
     *
     * @param search Item to search for
     * @param mode   Way to search for item Mode 1: Name Mode 2: Username (Login
     *               only) Mode 3: Contains in name Mode 4: Contains in any place
     *               Mode 5: Exact search
     * @return Found item or null if not found
     * @preconditions Items must contain items
     * @postconditions Matching item is returned or null if not found
     */
    public static Stream<Item> searchItem(String search, int mode) {
        return switch (mode) {
            case 1 -> // Name
                    items.stream().filter(item -> item.getName().equalsIgnoreCase(search));

            case 2 -> // Username (Login only)
                    items.stream().filter(
                            item -> item instanceof Login login && (login).getUsername().toLowerCase().contains(search));

            case 3 -> // Contains in name
                    items.stream().filter(item -> item.getName().toLowerCase().contains(search.toLowerCase()));

            case 4 -> // Contains in any place
                    items.stream().filter(item -> item.toString().toLowerCase().contains(search));

            case 5 -> // Exact search
                    items.stream().filter(item -> item.getName().equals(search));

            default -> Stream.empty();
        };
    }

    /**
     * Prints items of given class type
     *
     * @param type Type of class to print
     * @return Steam of matching items
     * @preconditions Items must contain items
     * @postconditions Matching items are returned
     */
    public static Stream<Item> viewItemType(Class<?> type) {
        return items.stream().filter(type::isInstance);
    }

    /**
     * Delete item with given name
     *
     * @param itemName Name of item to delete
     * @return True if item is deleted successfully, false if not
     * @preconditions Items ArrayList must contain data
     * @postconditions Item is removed from items List if found
     */
    public static boolean deleteItem(String itemName) {
        return items.remove(itemName) != null;
    }

    /**
     * Method used to test PALM
     *
     * @param testItems ArrayList of testing items
     */
    public static void initTesting(ItemManager<Item> testItems) {
        items = testItems;
    }

    /**
     * Loads items to ItemManager from database
     *
     * @param username Current user
     * @param password User's password
     * @preconditions User must be logged-in
     * @postconditions User items are loaded to items list
     */
    public static void loadItems(String username, String password) {
        PALMDB pdb = new PALMDB();
        // Read in items using a background thread
        Thread loadItemsThread = new Thread(() -> {
            pdb.readItems(username, password, items);
            // Automatic save thread is called after items are loaded as an incomplete item list should not be saved
            automaticSave(username, password);
        });
        loadItemsThread.start();
    }

    /**
     * Generates password
     *
     * @param length  Length of pasword
     * @param nums    Include numbers
     * @param caps    Include uppercase letters
     * @param symbols Include symbols
     * @return Generate password
     */
    public static String generatePassword(int length, boolean nums, boolean caps, boolean symbols) {
        String chars = "abcdefghijuklmnopqrstuvxyz";
        if (nums) {
            chars += "0123456789";
        }
        if (caps) {
            chars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (symbols) {
            chars += "!@#$%^&*()-_=+[{]}\\|:;\"'<,>.?/";
        }

        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        PALMDB pdb = new PALMDB();

        String[] login = UserPrompts.userLoginPrompt();
        String username = login[0];
        String password = login[1];
        loadItems(username, password);

        UserPrompts.mainLoop(items);

        // Save changes to file on quit
        if (!pdb.writeItems(username, password, items)) {
            System.err.println("Error saving items...");
        }
    }
}
