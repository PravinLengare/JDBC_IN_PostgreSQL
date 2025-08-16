import java.sql.*;
import java.util.Scanner;

public class TranHandling {
    private static final String url = "jdbc:postgresql://localhost:5432/lenden_1";
    private static final String username = "postgres";
    private static final String password = "Pravin@123";

    static boolean isSufficient(Connection connection, int account_no, double amount) {
        try {
            String query = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account_no);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double current_balance = resultSet.getDouble("balance");
                return amount <= current_balance;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false); // Start transaction

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter sender account no : ");
            int account_no = sc.nextInt();
            System.out.println("Enter amount : ");
            double amount = sc.nextDouble();

            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

            try (PreparedStatement debit_preparedStatement = connection.prepareStatement(debit_query);
                 PreparedStatement credit_preparedStatement = connection.prepareStatement(credit_query)) {

                debit_preparedStatement.setDouble(1, amount);
                debit_preparedStatement.setInt(2, account_no);

                credit_preparedStatement.setDouble(1, amount);
                credit_preparedStatement.setInt(2, 102); // Fixed receiver account

                if (isSufficient(connection, account_no, amount)) {
                    int affectedRow1 = debit_preparedStatement.executeUpdate();
                    int affectedRow2 = credit_preparedStatement.executeUpdate();

                    if (affectedRow1 > 0 && affectedRow2 > 0) {
                        connection.commit();
                        System.out.println("Transaction successful!!");
                    } else {
                        connection.rollback();
                        System.out.println("Transaction failed: Invalid account(s).");
                    }
                } else {
                    connection.rollback();
                    System.out.println("Transaction Failed: Insufficient balance.");
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback on any error
                System.out.println("Error during transaction: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
