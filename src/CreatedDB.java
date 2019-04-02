import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatedDB {

    public CreatedDB()
    {

        try
        {
            // Create a named constant for the URL.
            // NOTE: This value is specific for Java DB.
            final String DB_URL = "jdbc:derby:CreatedDB;create=true"; // changed the part AFTER jdbc:derby: AND BEFORE ;create=true

            // Create a connection to the database.
            Connection conn =
                    DriverManager.getConnection(DB_URL);

            // If the DB already exists, drop the tables.
            dropTables(conn);

            // Build the table.
            buildProductTable(conn);

            // Build the Customer table.
            buildCartTable(conn);

            // Close the connection.
            conn.close();
        } catch (Exception e)
        {
            System.out.println("Error Creating the Table");
            System.out.println(e.getMessage());
        }

    }

    /**
     * The dropTables method drops any existing
     * in case the database already exists.
     */
    public static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables.");

        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            try
            {
                // Drop the Customer table.
                stmt.execute("DROP TABLE Products");
                System.out.println("Products table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }

            try
            {
                // Drop the Coffee table.
                stmt.execute("DROP TABLE Cart");
                System.out.println("Cart table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The buildCoffeeTable method creates the
     * Coffee table and adds some rows to it.
     */
    public static void buildProductTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Products (" +
                    "Description VARCHAR(250), " +
                    "ProdNum CHAR(10) NOT NULL PRIMARY KEY, " +
                    "Price DOUBLE " +
                    ")");

            // Insert row #1.
            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Fortnite', "+
                    "'1', " +
                    "0.99 )");

            // Insert row #2.
            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Pool', " +
                    "'2', " +
                    "1.95 )");

            // Insert row #3.
            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Zumba', " +
                    "'3', " +
                    "3.95 )");

            // Insert row #4.
            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Mario Cart', " +
                    "'4', " +
                    "59.99 )");

            System.out.println("Products table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildCartTable method creates the
     * Cart table and adds some rows to it.
     */
    public static void buildCartTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE Cart" +
                    "(ProdNum CHAR(10) NOT NULL PRIMARY KEY, " +
                    "Description CHAR(25), " +
                    "Price DOUBLE" +
                    ")");

            System.out.println("Cart table created.");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
