package PreparedStatementExamples;

import java.sql.DriverManager ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.sql.PreparedStatement ;
import java.sql.SQLException ;

public class ReadData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;

    private static final String USERNAME = "root" ;

    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        //Load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("Loaded All the classes successfully!");
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now connect to the databases
        try {
            //Connect to the database
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Create a general query
            String query = "SELECT * FROM students " ;

            //Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;

            //Now execute the query
            ResultSet resultSet = preparedStatement.executeQuery() ;

            //Now print the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //Close the connection
            connection.close() ;
            preparedStatement.close() ;
            resultSet.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
