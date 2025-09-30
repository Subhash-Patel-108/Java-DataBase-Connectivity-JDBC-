package StatementExamples;
import java.sql.* ;

public class ReadData {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        //First load all the classes
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("Driver loaded successfully") ;
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Now, Connect to the database and perform read operation
        try {
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;
            Statement statement = connection.createStatement() ;

            String query = "SELECT * FROM students" ;
            ResultSet resultSet = statement.executeQuery(query) ; //executeQuery() return ResultSet in terms of answer

            //Now, we will print the data
            while(resultSet.next()) {
                //here the get<DataType>(fieldName) is used to get the data from the database
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                //Statement to print the data
                String result = String.format("Id : %d | Name : '%s' | Age : %d | Marks : %f " , id , name , age , marks) ;
                System.out.println(result);
            }

            //close the Database and statement
            connection.close();
            statement.close() ;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
