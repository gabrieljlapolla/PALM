package palm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.HashMap;

public class PALMDB {
    private static final String URL = "jdbc:sqlite:palmdb.sqlite";
    private static final String READING_ERR = "Error reading from database";

    public PALMDB() {
        createTables();
    }

    /**
     * Converts the given String to a HashMap object String must be formatted
     * {key1:value1, key2:value2, etc...}
     *
     * @param mapString String to be converted
     * @return HashMap object containing data given in String
     */
    private static HashMap<String, String> stringToHashMap(String mapString) {
        HashMap<String, String> map = new HashMap<>();
        // Empty HashMap
        if (mapString.equals("{}")) {
            return map;
        }
        // Split into individual pairs
        String[] pairs = mapString.substring(1, mapString.length() - 1).split(",");

        // Iterate over pairs, split by "=" and add to HashMap
        for (String pair : pairs) {
            String[] kv = pair.trim().split("=");
            map.put(kv[0], kv[1]);
        }

        return map;
    }

    /**
     * Connects to database and executes given query through a prepared statement
     *
     * @param query Query to execute
     */
    private void executePreparedStatement(String query) {
        Connection connection = connect();
        try {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.execute();
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to database");
        }
    }

    /**
     * Creates tables to contain user login information and items
     *
     * @preconditions Database must be able to be written to
     * @postconditions User table and items table are created
     */
    public void createTables() {
        String queryItems = """
                CREATE TABLE IF NOT EXISTS itemsTable (
                username text PRIMARY KEY,
                items text
                );""";

        executePreparedStatement(queryItems);

        String queryUsers = """
                CREATE TABLE IF NOT EXISTS usersTable (
                             username text PRIMARY KEY,
                             uuid text,
                             salthash blob NOT NULL,
                             totpsid text,
                             FOREIGN KEY (username)
                                 REFERENCES itemsTable (username)
                                 ON DELETE CASCADE
                                 ON UPDATE NO ACTION
                         );""";

        executePreparedStatement(queryUsers);
    }

    /**
     * Connects to database or creates one if one doesn't yet exist
     *
     * @return Connection to database or null if failed
     * @preconditions None
     * @postconditions Database connection is created
     */
    private Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Could not connect to database");
            return null;
        }
    }

    /**
     * Write the given user and their SaltHash to the database
     *
     * @param username User to be written
     * @param saltHash User login information to be written
     * @return True if success
     * @preconditions User database table must exist
     * @postconditions User is written to database
     */
    public boolean writeUser(String username, SaltHash saltHash, String uuid, String totpsid) {
        String query = "INSERT INTO usersTable (username, salthash, uuid, totpsid) VALUES(?,?,?,?)";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, username); // Store username

                    // To store hashed login info, convert SaltHash object to blob and save to database
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(saltHash);
                    ps.setBytes(2, baos.toByteArray());

                    ps.setString(3, uuid);
                    ps.setString(4, totpsid);

                    ps.execute();

            }
        } catch (Exception e) {
            System.err.println("Error writing to database");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given username and password are valid login credentials
     *
     * @param username Username to check
     * @param password Password to check
     * @return True if credentials are valid
     * @preconditions User database table must exist
     * @postconditions None
     */
    public boolean checkUser(String username, String password) {
        // Check if user even exists
        if (!containsUser(username)) {
            return false;
        }

        String query = "SELECT salthash FROM usersTable WHERE username = ?";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                // Get blob bytes and convert back to SaltHash
                ByteArrayInputStream baip = new ByteArrayInputStream(rs.getBytes("salthash"));
                ObjectInputStream ois = new ObjectInputStream(baip);
                SaltHash salthash = (SaltHash) ois.readObject();
                return Encrypt.checkPassword(password, salthash);

            }
        } catch (Exception e) {
            System.err.println(READING_ERR);
            return false;
        }
    }

    /**
     * Reads the given user's UUID from database
     *
     * @param username Username to check
     * @return User's UUID if it exists or null
     * @preconditions User database table must exist
     * @postconditions None
     */
    public String readUserUuid(String username) {
        // Check if user even exists
        if (!containsUser(username)) {
            System.err.println("User not found");
            return null;
        }

        String query = "SELECT uuid FROM usersTable WHERE username = ?";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                return rs.getString(1);

            }
        } catch (Exception e) {
            System.err.println(READING_ERR);
            return null;
        }
    }

    /**
     * Reads the given user's TOTP SID from database
     *
     * @param username Username to check
     * @return User's TOTP SID if it exists or null
     * @preconditions User database table must exist
     * @postconditions None
     */
    public String readUserTotpSid(String username) {
        // Check if user even exists
        if (!containsUser(username)) {
            System.err.println("User not found");
            return null;
        }

        String query = "SELECT totpsid FROM usersTable WHERE username = ?";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                return rs.getString(1);

            }
        } catch (SQLException e) {
            System.err.println(READING_ERR);
            return null;
        }
    }

    /**
     * Checks if the database contains the given user
     *
     * @param username User to search for
     * @return True if user exists
     * @preconditions User database table must exist
     * @postconditions None
     */
    public boolean containsUser(String username) {
        String query = "SELECT username FROM usersTable WHERE username = ?";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                // Check if results contains any data
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(READING_ERR);
            return false;
        }
        return false;
    }

    /**
     * Reads given user's items from database and saves to items list
     *
     * @param username User to get items for
     * @param password User's password to decrypt items
     * @param items    Item list to add to
     * @preconditions Username and password must be validated and items database must exist
     * @postconditions All user items are stored in items list
     */
    public void readItems(String username, String password, ItemManager<Item> items) {
        String query = "SELECT items " +
                       "FROM itemsTable WHERE username = ?";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    for (String line : rs.getString("items").split("%n")) {
                        String[] params = Encrypt.decrypt(line, password).split("\\+");
                        if (params.length == 0) {
                            continue;
                        }
                        // Create correct object based on first String in array
                        switch (params[0]) {
                            case "LoginApplication" ->
                                    items.add(new LoginApplication(params[1], params[2], params[3], params[4]));
                            case "LoginWebSite" ->
                                    items.add(new LoginWebSite(params[1], params[2], params[3], params[4]));
                            case "LoginDevice" ->
                                    items.add(new LoginDevice(params[1], params[2], params[3], params[4]));
                            case "Note" -> items.add(new Note(params[1], params[2]));
                            case "CredentialCustom" ->
                                    items.add(new CredentialCustom(params[1], stringToHashMap(params[2])));
                            case "CredentialCard" ->
                                    items.add(new CredentialCard(params[1], params[2], Integer.parseInt(params[3]),
                                            Integer.parseInt(params[4]), params[5], stringToHashMap(params[6])));
                            case "CredentialIdentity" ->
                                    items.add(new CredentialIdentity(params[1], params[2], stringToHashMap(params[3])));
                        }
                    }
                }

            }
        } catch (SQLException e) {
            System.err.println(READING_ERR);
        }
    }

    /**
     * Writes all user items to database
     *
     * @param username User whose items will be written
     * @param password Password used to encrypt items
     * @param items    Items to be written
     * @return True if success
     * @preconditions User must be logged-in with items loaded
     * @postconditions Items are saved to database
     */
    public boolean writeItems(String username, String password, ItemManager<Item> items) {
        // Convert items to encrypted text
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.encrypt(password));
            sb.append("%n");
        }

        // Write item text to database
        String query = "INSERT OR REPLACE INTO itemsTable (username, items) VALUES(?,?)";

        try (Connection connection = connect()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username); // Store username
                ps.setString(2, sb.toString());
                ps.execute();
            }
        } catch (SQLException e) {
            System.err.println("Error writing to database");
            return false;
        }
        return true;
    }
}
