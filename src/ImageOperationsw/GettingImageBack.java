package ImageOperationsw;

import java.io.*;
import java.sql.*;

public class GettingImageBack {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/image_operations";
        String username = "postgres";
        String password = "Pravin@123";
        String folder = "/Users/webshar/Desktop/";
        String query = "SELECT image_data FROM image_table WHERE image_id = ?";

        // Loading the driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }

        // Connection
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            System.out.println("Connection Established Successfully!!");

            preparedStatement.setInt(1, 2);  // fetch image_id = 1
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image_data");
                String imagePath = folder + "extractedImage1.jpg";

                try (OutputStream outputStream = new FileOutputStream(imagePath)) {
                    outputStream.write(imageData);
                    System.out.println("Image extracted successfully at: " + imagePath);
                }
            } else {
                System.out.println("Image not found!!");
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }
}
