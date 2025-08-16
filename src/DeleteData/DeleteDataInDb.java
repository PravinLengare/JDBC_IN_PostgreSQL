package DeleteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDataInDb {
    private static final String url = "jdbc:postgresql://localhost:5432/mydb";
    private static final String username = "postgres";
    private static final String password = "Pravin@123";
    public static void main(String[] args) {
        String query = " DELETE FROM student WHERE student_id = ?";

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
            preparedStatement.setInt(1,2);
            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0){
                System.out.println("Deleted Successfully !");
            }
            else {
                System.out.println("Delete Failed !");
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
