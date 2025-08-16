package InsertData;

import java.sql.*;

public class InsertInDb {
    private static final String url = "jdbc:postgresql://localhost:5432/mydb";
    private static final String username = "postgres";
    private static final String password = "Pravin@123";

    public static void main(String[] args) {
        String query = "INSERT INTO student (first_name,last_name,age) VALUES (?,?,?)";

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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,"Amol");
            preparedStatement.setString(2,"Lengare");
            preparedStatement.setInt(3,20);
            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0){
                System.out.println("Inserted Successfully !");
            }
            else {
                System.out.println("Insertion Failed !");
            }


            // Close all resources which we have used to be responsibility

            preparedStatement.close();
            connection.close();

            System.out.println("Connection Closed Successfully !");

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
