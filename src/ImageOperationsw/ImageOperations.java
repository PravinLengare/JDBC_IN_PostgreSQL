package ImageOperationsw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageOperations {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/image_operations";
        String username = "postgres";
        String password = "Pravin@123";
        String image_path = "/Users/webshar/Desktop/pravin.jpg";
        String query = "INSERT INTO image_table(image_data) VALUES (?)";

        // Loading the drivers

        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        // Connection

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully!!");
            FileInputStream fileInputStream = new FileInputStream(image_path); // this class will convert any file into the binary format.
            byte [] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1,imageData);
            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow > 0){
                System.out.println("Image inserted successfully!!");
            }
            else {
                System.out.println("Not inserted !!");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
