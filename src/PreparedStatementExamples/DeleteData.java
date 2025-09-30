package PreparedStatementExamples;
import java.sql.SQLException ;
import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

public class DeleteData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;

    private static final String USERNAME = "root" ;

    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        //First,load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("All classes loaded successfully!");
        }catch(ClassNotFoundException e ) {
            System.out.println(e.getMessage());
        }

        //Now, connect with the database and perform delete operation
        try {
            //connecting to the database
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD);

            //creating a general query
            String query = "DELETE FROM students WHERE id = ? " ;

            //prepare a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;

            //Set the values of placeholders
            preparedStatement.setInt(1 , 3);

            //execute the query
            int rowEffected = preparedStatement.executeUpdate() ;

            if (rowEffected > 0) {
                System.out.println("Data Deleted Successfully!");
            }else{
                System.out.println("Data Not Deleted!");
            }

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM students") ;

            while (resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //Now close all the connections
            connection.close() ;
            preparedStatement.close() ;
            resultSet.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
