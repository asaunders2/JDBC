import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CreatedDB cc = new CreatedDB();
        Scanner key = new Scanner(System.in);
        Scanner goAgain = new Scanner(System.in);
        int input;
        int itemNumber;
        boolean endProgram = false;
        String breakPoint = "=====================================";

        System.out.println(breakPoint);

        while (!endProgram) {

            System.out.println("\n1) Add Items \n2) View Cart \n3) Exit");

            try {
                input = key.nextInt();
                if (input == 1) {

                    //if the user wants to add an item to the cart

                    System.out.println(breakPoint);
                    outputDB("Products");
                    System.out.println(breakPoint);
                    System.out.println("\nEnter an item number from the list of products.");

                    try {
                        itemNumber = key.nextInt();
                        if (itemNumber == 1 || itemNumber == 2 || itemNumber == 3 || itemNumber == 4) {

                            //if the user enters a valid item number

                            addItem(itemNumber);
                            System.out.println("added");
                        }
                    } catch (Exception e) {
                        System.out.println("\nInvalid Input. Enter a valid item number.");
                        key.next();
                    }

                    System.out.println("\nContinue using (y/n)?");
                    String again = goAgain.next();

                    if(again.toLowerCase().equals("y")){

                        // if the user wants to add another item

                        endProgram = false;
                    }else if(again.toLowerCase().equals("n")){

                        // if the user exit
                        // this needs something so the user can go back to the main menu and view the cart or end the program

                        endProgram = true;
                    }else{
                        System.out.println("\nInvalid Input. Enter y or n");
                    }

                } else if (input == 2) {

                    // if the user wants to view the cart

                    outputDB("Cart");
                }else if(input == 3){

                    // if the user wants to do nothing

                    endProgram = true;
                }
            } catch (Exception e) {
                System.out.println("\nInvalid Input. Enter a number between 1 and 3.");
                key.next();
            }
        }
    }

    private static void addItem(int itemNumber) {

        final String DB_URL = "jdbc:derby:CreatedDB";
        Statement stmt = null;
        Connection conn = null;
        try{
            System.out.println("\nConnecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql;
            int numItems = 1;

            if(itemNumber == 1){
                sql = "INSERT INTO Cart " +
                        "VALUES ('1', 'Fortnite', 0.99)";
                stmt.executeUpdate(sql);
            }else if(itemNumber == 2){
                sql = "INSERT INTO Cart " +
                        "VALUES ('2', 'Pool', 1.95)";
                stmt.executeUpdate(sql);
            }else if(itemNumber == 3){
                sql = "INSERT INTO Cart " +
                        "VALUES ('3', 'Zumba', 3.95)";
                stmt.executeUpdate(sql);
            }else if(itemNumber == 4){
                sql = "INSERT INTO Cart " +
                        "VALUES ('4', 'Mario Cart', 59.99)";
                stmt.executeUpdate(sql);
            }

            System.out.println("\nInserted records into the table...");
            //Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

    }

    private static void outputDB(String table) {
        final String DB_URL = "jdbc:derby:CreatedDB";
        Statement stmt = null;
        Connection conn = null;
        try{
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT ProdNum, Description, Price FROM " + table;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                String id  = rs.getString("ProdNum");
                double price = rs.getDouble("Price");
                String description = rs.getString("Description");

                //Display values
                System.out.print("\nID: " + id.trim());
                System.out.print("\nPrice: " + price);
                System.out.println("\nDescription: " + description);
            }

            if (table.equals("Cart")) {
                ResultSet sum = stmt.executeQuery("SELECT SUM(Price) FROM Cart");

                try {
                    if (sum.next()) {
                        double total = sum.getDouble(1);
                        System.out.println("Total: " + total);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}