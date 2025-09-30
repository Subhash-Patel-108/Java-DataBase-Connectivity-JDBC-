import java.sql.* ; // to connect to the database

public class FirstJDBCConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb" ;

    private static final String USERNAME = "root" ;

    private static final String PASSWORD = "SUBH@123" ;

    public static void main(String[] args) {
        // load the driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e ) {
            System.out.println(e.getMessage());
        }

        // connect to the database

        try {
            Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD) ;
            Statement statement = connection.createStatement() ;

            String query = "SELECT * FROM students" ;
            ResultSet resultSet = statement.executeQuery(query) ;

            while(resultSet.next()) {
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name") ;
                int age = resultSet.getInt("age") ;
                double marks = resultSet.getDouble("marks") ;

                System.out.println("Id : " + id + " | Name : " + name + " | Age : " + age + " | Marks : " + marks);
            }

            connection.close();
            statement.close();
            resultSet.close();
        }catch(SQLException e ) {
            System.out.println(e.getMessage());
        }

        return ;
    }
}
