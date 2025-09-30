package TransactionHandlingExample;

import java.sql.Connection ;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.Scanner ;

public class BankTransaction {

    private static final String URL = "jdbc:mysql://localhost:3306/Bank" ;
    private static final String USERNAME = "root" ;
    private static final String PASSWORD = "SUBH@123" ;


    public static void main(String[] args) {
        //Load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("All classes loaded successfully!");
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now connect to the database
        try {
            //Connect to the dataBase
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Off the auto commit
            connection.setAutoCommit(false) ;

            //Create two general query (first for credit and second for debit) for the transaction
            String creditQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?" ;
            String debitQuery = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?" ;

            //Taking input from the user
            Scanner scanner = new Scanner(System.in) ;
            System.out.print("From : ") ;
            int fromAccountNumber = scanner.nextInt() ;
            System.out.print("To : ");
            int toAccountNumber = scanner.nextInt() ;
            System.out.print("Enter the amount: ") ;
            double amount = scanner.nextDouble() ;


            //now,create a preparedStatement for both the queries
            PreparedStatement creditStatement = connection.prepareStatement(creditQuery) ;
            PreparedStatement debitStatement = connection.prepareStatement(debitQuery) ;
            
            //Set the values of placeholders 
            creditStatement.setDouble(1 , amount) ;
            creditStatement.setInt(2, toAccountNumber) ;

            debitStatement.setDouble(1 , amount) ;
            debitStatement.setInt(2, fromAccountNumber) ;

            //Execute both query 
            creditStatement.executeUpdate() ;
            debitStatement.executeUpdate() ;

            //If there is no problem to make transaction then commit other wise rollback
            if(isSafeToPerformOperation(connection , amount , fromAccountNumber)) {
                connection.commit();
                System.out.println("Transaction successful!");
            }else{
                connection.rollback();
                System.out.println("Transaction Failed!");
            }

            //Close all the connections with data base
            connection.close() ;
            debitStatement.close() ;
            creditStatement.close();
            scanner.close() ;

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isSafeToPerformOperation(Connection connection , double debitAmount , int fromAccountNumber) {
        //if the amount is negative then we can't make the transaction
        if(debitAmount < 0) {
            return false ;
        }

        try {
            //Create a general query to find the balance of the account 
            String query = "SELECT balance FROM Accounts WHERE account_number = ? " ;

            //create preparedStatement 
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;

            //set the values of placeholder
            preparedStatement.setInt(1 , fromAccountNumber);

            //result set to store the result of executeQuery() method
            ResultSet resultSet = preparedStatement.executeQuery() ;

            //since it return only one record if the account is present
            if(resultSet.next()) {
                double currentBalance = resultSet.getDouble("balance") ;
                return currentBalance >= debitAmount ; // compare with balance with debit amount
            }

            //Close statement and resultSet
            resultSet.close() ;
            preparedStatement.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return false ; // we can't make transaction
    }
}
