package StatementExamples;
import java.sql.Connection ;
import java.sql.Statement ;
import java.sql.SQLException ;
import java.sql.ResultSet ;
import java.sql.DriverManager ;
public class DeleteData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;
    private static final String USERNAME = "root" ;
    private static final String PASSWORD = "SUBH@123";

    public static void main(String[] args) {
        //Load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Classes Loaded Successfully!");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Connect with DB and perform delete operation
        try {
            //Connect to the DB
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Creating a Statement object (vehicle to transfer the query to the database)
            Statement statement = connection.createStatement() ;

            //Create a query
            String deleteQuery = "DELETE FROM students WHERE id = 2" ;

            //Execute the query
            int rowEffected = statement.executeUpdate(deleteQuery) ;

            if(rowEffected > 0) {
                System.out.println("Data deleted successfully!");
            }else {
                System.out.println("Data not deleted!");
            }

            //Printing all the data of the table
            String readQuery = "SELECT * FROM students" ;

            //Executing the query
            ResultSet resultSet = statement.executeQuery(readQuery) ;

            //Printing the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //close all the connection with db
            connection.close() ;
            statement.close() ;
            resultSet.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
