package PreparedStatementExamples;

import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;

    private static final String USERNAME = "root" ;

    private static final String PASSWORD = "SUBH@123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in) ;
        //load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("All classes loaded successfully!");
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now, connect to the database
        try {
            //connect to the database
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Write a general query
            String query = "INSERT INTO students (name , age , marks) VALUES (? , ? , ?)" ;

            //Create a Statement
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;

            //Now, we will use a while loop to add the queries to the batch
            while(true) {
                System.out.print("Enter Name: ");
                String studentName = scanner.nextLine().trim();

                System.out.print("Enter Age: ");
                String ageLine = scanner.nextLine().trim();
                int studentAge;
                try {
                    studentAge = Integer.parseInt(ageLine);
                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid age. Please enter a valid integer.");
                    continue;
                }

                System.out.print("Enter Marks: ");
                String marksLine = scanner.nextLine().trim();
                double studentMarks;
                try {
                    studentMarks = Double.parseDouble(marksLine);
                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid marks. Please enter a valid number.");
                    continue;
                }

                //Set all the value of placeholder
                preparedStatement.setString(1 , studentName) ;
                preparedStatement.setInt(2 , studentAge) ;
                preparedStatement.setDouble(3 , studentMarks) ;

                //Add the query to the batch
                preparedStatement.addBatch();

                System.out.print("Do you want to add more data? (y/n) : ");
                String choice = scanner.next() ;

                if(choice.equalsIgnoreCase("N")) {
                    break ;
                }
            }

            //If we execute the batch query, it returns the array of int (where value 0 represent the ith query is not executed successfully)
            int[] resultArray = preparedStatement.executeBatch() ;

            for(int i = 0 ; i < resultArray.length ; i++)  {
                if (resultArray[i] == 0 ) {
                    System.out.println("Query " + (i + 1) + " not executed! ");
                }
            }

            //now we print the data
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM students ") ;

            while(resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %.2f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //close all the connection with db
            connection.close() ;
            preparedStatement.close();
            resultSet.close();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
