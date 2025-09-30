package PreparedStatementExamples;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.SQLException ;
import java.sql.ResultSet ;
import java.sql.DriverManager ;

public class UpdateData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;

    private static final String USERNAME = "root" ;

    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        //Load all the classes first
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("All classes loaded successfully!");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now, connect with the database and update the data

        try {
            //Connect with data base
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Create a general query
            String query = "UPDATE students SET marks = ? WHERE id = ? ";

            //Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;

            //Set the values
            preparedStatement.setDouble(1 , 45.69) ;
            preparedStatement.setInt(2 , 1) ;

            //Execute the query
            int rowEffected = preparedStatement.executeUpdate() ;

            if (rowEffected > 0 ) {
                System.out.println("Data Updated Successfully!");
            }else{
                System.out.println("Data Not Updated!");
            }

            //Now check that the data is updated or not
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM students") ;

            while (resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //now close all the connection with the database
            connection.close() ;
            preparedStatement.close();
            resultSet.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
