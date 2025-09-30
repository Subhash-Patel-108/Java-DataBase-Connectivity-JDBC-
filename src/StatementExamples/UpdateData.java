package StatementExamples;

import java.sql.DriverManager;
import java.sql.Connection ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;

public class UpdateData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;
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

        //Now, connect to the database and perform update operation
        try {
            //Now, connect to the database
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Create a statement
            Statement statement = connection.createStatement() ;

            //Create a Query
            String query = String.format("UPDATE students SET marks = %f WHERE id = %d",98.69 , 1) ;
            
            //Run the query
            int rowEffected = statement.executeUpdate(query) ;

            if(rowEffected > 0) {
                System.out.println("The data is Updated successfully!");
            }else{
                System.out.println("The data is not Updated!");
            }

            //Now retrieve all the data from the table
            String selectQuery = "SELECT * FROM students" ;
            ResultSet resultSet = statement.executeQuery(selectQuery) ;

            //Print all the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //Now, finally close the database and statement
            connection.close() ;
            statement.close() ;
            resultSet.close() ;

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
