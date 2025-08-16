package ReadData;

import java.sql.*;

public class RetrieveData {
    private static final String url = "jdbc:postgresql://localhost:5432/mydb";
    private static final String username = "postgres";
    private static final String password = "Pravin@123";

    public static void main(String[] args) {
        String query = "SELECT * FROM student";

        // Load Driver
        try {
            Class.forName("org.postgresql.Driver");

        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        // Connection

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establish Successfully!");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()){
                String first = resultset.getString("first_name");
                String last = resultset.getString("last_name");
                int age = resultset.getInt("age");
                System.out.println("-------------------------------------------------");

                System.out.println("First name : "+first);
                System.out.println("Last name : "+last);
                System.out.println("Age : "+age);
            }
            // Close all resources which we have used to be responsibility
            resultset.close();
            statement.close();
            connection.close();

            System.out.println("Connection Closed Successfully !");

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
