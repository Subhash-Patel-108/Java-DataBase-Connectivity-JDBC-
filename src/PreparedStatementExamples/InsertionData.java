package PreparedStatementExamples;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

public class InsertionData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;

    private static final String USERNAME = "root" ;

    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        //First, we load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now, we connect to the database
        try {
            //Connect to the data base
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;

            //Create a general query
            String query = "INSERT INTO students (name , age , marks) VALUES (? , ? , ?)" ; // The ? are the placeholders

            //Create a prepared statement (it only complies the query only ones time and after that the query is run multiple times)
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;

            //Now we set the values
            //........Syntax..........//
            //prepared_object_name.set<DataType>(PlaceHolderIndex , Value) ;
            preparedStatement.setString(1 , "Guru Ji") ;
            preparedStatement.setInt(2 , 45) ;
            preparedStatement.setDouble(3 , 99.69) ;

            //Now we execute the query
            int rowEffected = preparedStatement.executeUpdate() ;//no need to pass the query as it is already complied

            if (rowEffected > 0 ) {
                System.out.println("Data Inserted Successfully!");
            }else{
                System.out.println("Data Not Inserted!");
            }

            //Now print the data
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM students") ;

            while(resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //Now close all the connections with the database
            connection.close() ;
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
