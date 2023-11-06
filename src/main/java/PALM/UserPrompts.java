package PALM;

import java.util.HashMap;
import java.util.Scanner;

public class UserPrompts {

    private final static Scanner in = new Scanner(System.in);

    /**
     * Prints the given String and returns user's response
     *
     * @param prompt Text to print
     * @return User's response
     */
    private static String prompt(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    /**
     * Prints the given String and returns user's response as an integer
     *
     * @param prompt Text to print
     * @return User's integer response
     */
    private static int promptInt(String prompt) {
        boolean badInput;
        int result = 0;
        do {
            badInput = false;
            System.out.print(prompt);
            try {
                result = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Please only enter a number.");
                badInput = true;
            }

        } while (badInput);
        return result;
    }

    /**
     * Prompts the user to enter as many custom fields as desired
     *
     * @return HashMap containing entered key and values
     */
    private static HashMap<String, String> promptFields() {
        HashMap<String, String> fields = new HashMap<>();
        boolean done = false;
        String key, value;
        // Prompt to enter any fields
        if (!prompt("Enter a custom field? (Y/N): ").equalsIgnoreCase("y")) {
            return fields;
        }
        while (!done) {
            key = prompt("Enter custom field name: ");
            value = prompt("Enter custom field value: ");
            fields.put(key, value);
            // Prompt to add more fields
            if (!prompt("Add a field? (Y/N): ").equalsIgnoreCase("y")) {
                done = true;
            }
        }
        return fields;
    }

    /**
     * Prompts user if they want to update
     *
     * @param name Name of thing to update
     * @return True if yes, false if no
     */
    private static boolean updatePrompt(String name) {
        return prompt(String.format("Update %s? (Y/N): ", name)).equalsIgnoreCase("y");
    }

    /**
     * Prompts user to log in to existing account or to create a new account
     *
     * @return Username of logged-in user
     * @preconditions Users HashMap must be initialized
     * @postconditions User will be "logged in" and their items read from a file
     */
    public static String[] userLoginPrompt() {
        String username;
        String password;
        int minPassLen = 3;

        // Prompt user until correct credentials are entered
        while (true) {
            System.out.println("""
                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    					          ___   ____
                            /' --;^/ ,-_\\     \\ | /
                           / / --o\\ o-\\ \\\\   --(_)--
                          /-/-/|o|-|\\-\\\\|\\\\   / | \\              PALM
                           '`  ` |-|   `` '                      Password and Login Manager
                                 |-|
                                 |-|O
                                 |-(\\,__
                              ...|-|\\--,\\_....
                          ,;;;;;;;;;;;;;;;;;;;;;;;;,.
                    ~~,;ASCII art from www.asciiart.eu ;;;;;;,~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    ~;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;,  ______   ---------   _____     ------
                    """);
            username = prompt("Please enter your username: ");
            // Nothing entered
            if (username.length() == 0) {
                continue;
            }
            // Username not found, prompt to create new account
            PALMDB pdb = new PALMDB();
            if (!pdb.containsUser(username)) {
                if (prompt(String.format("Username \"%s\" not found! Create new account? (Y/N): ", username))
                        .equalsIgnoreCase("y")) {
                    // Create new account
                    password = prompt("Please enter your new master password: ");
                    if (password.length() < minPassLen) {
                        System.out.printf("Password must be at least %d characters.\n", minPassLen);
                        continue;
                    }
                    if (pdb.writeUser(username, AES.getPasswordHash(username, password, null))) {
                        System.out.printf("Welcome %s!\n", username);
                    } else {
                        System.err.println("Error creating user...");
                        continue;
                    }
                    break;
                }
            } else { // Username exists, prompt for password
                password = prompt("Please enter your password: ");
                if (pdb.checkUser(username, password)) {
                    System.out.printf("Welcome back, %s!\n", username);
                    break;
                } else {
                    System.out.println("Incorrect password. Try again.");
                }
            }
        }
        return new String[]{username, password};
    }

    /**
     * Prints all items the user
     *
     * @param items All items
     * @preconditions Items ArrayList must contain items
     * @postconditions Matching items are printed to console
     */
    private static void viewAllItems(ItemManager<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found!");
            return;
        }
        // Loop over Items
        for (Item item : items) {
            System.out.println(item);
        }
    }

    /**
     * Main loop of the text based interface
     *
     * @param items List of current user's items
     * @preconditions User must be logged-in and items loaded
     * @postconditions Application exits
     */
    public static void mainLoop(ItemManager<Item> items) {
        // Main loop - exit application when user quits
        while (true) {
            System.out.println("\n/----Select an option----\\");
            System.out.println("| 1 - View All Items     |");
            System.out.println("| 2 - View by Type       |");
            System.out.println("| 3 - Search Items       |");
            System.out.println("| 4 - Create an Item     |");
            System.out.println("| 5 - Update an Item     |");
            System.out.println("| 6 - Delete an Item     |");
            System.out.println("| 7 - Export Items       |");
            System.out.println("| 8 - Generate Password  |");
            System.out.println("| 9 - Quit               |");
            System.out.println("\\------------------------/");

            HashMap<String, String> fields;
            String name, username, password;
            switch (promptInt("")) {
                case 1 -> // View all items
                        viewAllItems(items);
                case 2 -> { // View by type
                    System.out.print("Enter item type to view: ");
                    System.out.println("\n/----Select an option----\\");
                    System.out.println("| 1 - Application Login  |");
                    System.out.println("| 2 - Device Login       |");
                    System.out.println("| 3 - Web Site Login     |");
                    System.out.println("| 4 - Card               |");
                    System.out.println("| 5 - Identity           |");
                    System.out.println("| 6 - Note               |");
                    System.out.println("| 7 - Custom             |");
                    System.out.println("\\------------------------/");
                    switch (in.nextLine()) {
                        case "1" -> PALM.viewItemType(LoginApplication.class).forEach(System.out::println);
                        case "2" -> PALM.viewItemType(LoginDevice.class).forEach(System.out::println);
                        case "3" -> PALM.viewItemType(LoginWebSite.class).forEach(System.out::println);
                        case "4" -> PALM.viewItemType(CredentialCard.class).forEach(System.out::println);
                        case "5" -> PALM.viewItemType(CredentialIdentity.class).forEach(System.out::println);
                        case "6" -> PALM.viewItemType(Note.class).forEach(System.out::println);
                        case "7" -> PALM.viewItemType(CredentialCustom.class).forEach(System.out::println);
                        default -> System.err.println("Please select a shown option.");
                    }
                }
                case 3 -> { // Search Items
                    System.out.print("Enter way to search: ");
                    System.out.println("\n/----Select an option----\\");
                    System.out.println("| 1 - Name               |");
                    System.out.println("| 2 - Username           |");
                    System.out.println("| 3 - Contains in Name   |");
                    System.out.println("| 4 - Contains Anywhere  |");
                    System.out.println("| 5 - Exact              |");
                    System.out.println("\\------------------------/");
                    int searchChoice = promptInt("");
                    if (searchChoice < 1 || searchChoice > 5) {
                        System.err.println("Please select a shown option.");
                        break;
                    }
                    PALM.searchItem(prompt("Enter item name to search for: "), searchChoice).forEach(System.out::println);
                }
                case 4 -> { // Create
                    System.out.print("Enter item type to create: ");
                    System.out.println("\n/----Select an option----\\");
                    System.out.println("| 1 - Application Login  |");
                    System.out.println("| 2 - Device Login       |");
                    System.out.println("| 3 - Web Site Login     |");
                    System.out.println("| 4 - Card               |");
                    System.out.println("| 5 - Identity           |");
                    System.out.println("| 6 - Note               |");
                    System.out.println("| 7 - Custom             |");
                    System.out.println("\\------------------------/");
                    switch (in.nextLine()) {
                        case "1" -> { // Application Login
                            String appName;
                            name = prompt("Enter item name: ");
                            username = prompt("Enter username: ");
                            password = prompt("Enter password: ");
                            appName = prompt("Enter application name: ");
                            if (items.add(new LoginApplication(name, username, password, appName))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        case "2" -> { // Device Login
                            String device;
                            name = prompt("Enter item name: ");
                            username = prompt("Enter username: ");
                            password = prompt("Enter password: ");
                            device = prompt("Enter device name: ");
                            if (items.add(new LoginDevice(name, username, password, device))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        case "3" -> { // Web Site Login
                            String url;
                            name = prompt("Enter item name: ");
                            username = prompt("Enter username: ");
                            password = prompt("Enter password: ");
                            url = prompt("Enter site URL: ");
                            if (items.add(new LoginWebSite(name, username, password, url))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        case "4" -> { // Card Credential
                            String holderName, expiration;
                            int number, cvv;
                            name = prompt("Enter item name: ");
                            holderName = prompt("Enter card holder's name: ");
                            number = promptInt("Please enter card number: ");
                            cvv = promptInt("Please enter CVV:");
                            expiration = prompt("Please enter expiration date:");
                            fields = promptFields();
                            if (items.add(new CredentialCard(name, holderName, number, cvv, expiration, fields))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        case "5" -> { // Identity Credential
                            String type;
                            name = prompt("Enter item name: ");
                            type = prompt("Enter identity type: ");
                            fields = promptFields();
                            if (items.add(new CredentialIdentity(name, type, fields))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        case "6" -> { // Note
                            String notes, line;
                            name = prompt("Enter item name: ");
                            StringBuilder sb = new StringBuilder();
                            System.out.println("Enter notes and type \"DONE\" when finished.");
                            do {
                                line = in.nextLine();
                                sb.append(line).append("\n");
                            } while (!line.equals("DONE"));
                            notes = sb.substring(0, sb.length() - 6);
                            if (items.add(new Note(name, notes))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        case "7" -> { // Custom Credential
                            name = prompt("Enter item name: ");
                            fields = promptFields();
                            if (items.add(new CredentialCustom(name, fields))) {
                                System.out.println("Item created!");
                            } else {
                                System.err.println("Item already exists with that name!");
                            }
                        }
                        default -> System.err.println("Please select a shown option.");
                    }
                }
                case 5 -> { // Update
                    Item search = PALM.searchItem(prompt("Enter item name to update: "), 5).findFirst().orElse(null);
                    if (search == null) {
                        System.out.println("Item not found.");
                        break;
                    }
                    items.remove(search.getName());
                    switch (search.getClass().getSimpleName()) {
                        case "LoginApplication" -> { // Application Login
                            LoginApplication la = (LoginApplication) search;
                            String appName;
                            name = updatePrompt("name") ? prompt("Enter new name: ") : la.getName();
                            username = updatePrompt("username") ? prompt("Enter new username: ") : la.getUsername();
                            password = updatePrompt("password") ? prompt("Enter new password: ") : la.getPassword();
                            appName = updatePrompt("app name") ? prompt("Enter new app name: ") : la.getAppName();
                            if (items.add(new LoginApplication(name, username, password, appName))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        case "LoginDevice" -> { // Device Login
                            LoginDevice ld = (LoginDevice) search;
                            String device;
                            name = updatePrompt("name") ? prompt("Enter new name: ") : ld.getName();
                            username = updatePrompt("username") ? prompt("Enter new username: ") : ld.getUsername();
                            password = updatePrompt("password") ? prompt("Enter new password: ") : ld.getPassword();
                            device = updatePrompt("device") ? prompt("Enter new device: ") : ld.getDevice();
                            if (items.add(new LoginDevice(name, username, password, device))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        case "LoginWebSite" -> { // Web Site Login
                            LoginWebSite lws = (LoginWebSite) search;
                            String url;
                            name = updatePrompt("name") ? prompt("Enter new name:") : lws.getName();
                            username = updatePrompt("username") ? prompt("Enter new username: ") : lws.getUsername();
                            password = updatePrompt("password") ? prompt("Enter new password: ") : lws.getPassword();
                            url = updatePrompt("URL") ? prompt("Enter new URL: ") : lws.getUrl();
                            if (items.add(new LoginWebSite(name, username, password, url))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        case "CredentialCard" -> { // Card Credential
                            CredentialCard cc = (CredentialCard) search;
                            String holderName, expiration;
                            int number, cvv;
                            name = updatePrompt("name") ? prompt("Enter new name: ") : cc.getHolderName();
                            holderName = updatePrompt("holder name") ? prompt("Enter new holder name: ") : cc.getHolderName();
                            expiration = updatePrompt("expiration") ? prompt("Enter new expiration: ") : cc.getExpiration();
                            number = updatePrompt("card number") ? promptInt("Enter new card number: ") : cc.getNumber();
                            cvv = updatePrompt("CVV") ? promptInt("Enter new CVV: ") : cc.getCvv();
                            fields = updatePrompt("custom fields") ? promptFields() : cc.getFields();
                            if (items.add(new CredentialCard(name, holderName, number, cvv, expiration, fields))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        case "CredentialIdentity" -> { // Identity Credential
                            CredentialIdentity ci = (CredentialIdentity) search;
                            String type;
                            name = updatePrompt("name") ? prompt("Enter new name: ") : ci.getName();
                            type = updatePrompt("type") ? prompt("Enter new type: ") : ci.getType();
                            fields = updatePrompt("custom fields") ? promptFields() : ci.getFields();
                            if (items.add(new CredentialIdentity(name, type, fields))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        case "Note" -> { // Note
                            Note n = (Note) search;
                            String notes, line;
                            name = updatePrompt("name") ? prompt("Enter new name: ") : n.getName();
                            if (updatePrompt("notes")) {
                                StringBuilder sb = new StringBuilder();
                                System.out.println("Enter notes and type \"DONE\" when finished.");
                                do {
                                    line = in.nextLine();
                                    sb.append(line).append("\n");
                                } while (!line.equals("DONE"));
                                notes = sb.substring(0, sb.length() - 6);
                            } else {
                                notes = n.getNotes();
                            }
                            if (items.add(new Note(name, notes))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        case "CredentialCustom" -> { // Custom Credential
                            CredentialCustom ccu = (CredentialCustom) search;
                            name = updatePrompt("name") ? prompt("Enter new name: ") : ccu.getName();
                            fields = updatePrompt("custom fields") ? promptFields() : ccu.getFields();
                            if (items.add(new CredentialCustom(name, fields))) {
                                System.out.println("Item updated!");
                            } else {
                                System.err.println("Item already exists with that name!");
                                items.add(search);
                            }
                        }
                        default -> System.err.println("Please select a shown option.");
                    }
                }
                case 6 -> { // Delete
                    if (PALM.deleteItem(prompt("Enter item name to delete: "))) {
                        System.out.println("Item deleted!");
                    } else {
                        System.out.print("Item does not exist!");
                    }
                }
                case 7 -> // Export
                        PALM.writePlainTextItemsToFile(prompt("Enter file name: "), items.stream());
                case 8 -> { // Generate password
                    int length = promptInt("Enter password length: ");
                    boolean nums = prompt("Include numbers? (Y/N): ").equalsIgnoreCase("y");
                    boolean caps = prompt("Include capital letters? (Y/N): ").equalsIgnoreCase("y");
                    boolean symbols = prompt("Include symbols? (Y/N): ").equalsIgnoreCase("y");
                    System.out.println(PALM.generatePassword(length, nums, caps, symbols));
                }
                case 9 -> { // Quit
                    System.out.println("Quitting...");
                    in.close();
                    return;
                }
                default -> // Number entered, but not an option
                        System.err.println("Please select a shown option.");
            }
        }
    }
}
