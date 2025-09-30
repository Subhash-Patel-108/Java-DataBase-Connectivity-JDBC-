package StatementExamples;
import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;

public class InsertionData {
    private static final String URL = "jdbc:mysql://localhost:3306/javadb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        //Load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("All Classes Loaded Successfully! ");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now connect to the database and perform insertion operation
        try {
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD);
            Statement statement = connection.createStatement() ;

            String query = String.format("INSERT INTO students (name , age , marks ) VALUES ('%s' , %d , %f )" , "SUBH PATEL " , 21 , 69.69) ;

            int rowEffected = statement.executeUpdate(query);

            if(rowEffected > 0) {
                System.out.println("The data is inserted successfully!");
            }else{
                System.out.println("The data is not inserted!");
            }

            String readQuery = "SELECT * FROM students" ;
            ResultSet resultSet = statement.executeQuery(readQuery) ;

            //Printing the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double marks = resultSet.getDouble("marks");

                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }
            //close the data
            connection.close() ;
            statement.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
