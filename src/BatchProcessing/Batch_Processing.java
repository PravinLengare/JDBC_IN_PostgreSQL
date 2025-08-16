package BatchProcessing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Batch_Processing {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/employee1";
        String username = "postgres";
        String password = "Pravin@123";


        // Loading the driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }

        // Connection
         try{
             Connection connection = DriverManager.getConnection(url,username,password);
             System.out.println("Connection Established Successfully!!");
             connection.setAutoCommit(false);  // by default it was true means it will work auto commit .
             String query = "INSERT INTO employee_details(name ,job_name,salary) VALUES (?,?,?)";
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             Scanner scanner = new Scanner(System.in);
             while (true){
                 System.out.println("Enter the name of employee : ");
                 String name = scanner.nextLine();
                 System.out.println("Enter the job of employee : ");
                 String job = scanner.nextLine();
                 System.out.println("Enter the salary of employee : ");
                 int salary = scanner.nextInt();
                 preparedStatement.setString(1,name);
                 preparedStatement.setString(2,job);
                 preparedStatement.setInt(3,salary);
                 preparedStatement.addBatch(); // add all operations in the batch
                 System.out.println("Add more Y/N : ");
                 String choice = scanner.next();
                 if (choice.toUpperCase().equals("N")){
                     break;
                 }

             }

             int [] batchResult =  preparedStatement.executeBatch(); // to execute the whole batch
             connection.commit();
             System.out.println("Batch Executed Successfully !!");


        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
