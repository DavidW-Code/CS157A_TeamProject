import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
//Database name, has to be created in SQL file, must be existing DB
static final String DATABASE_NAME = "HotelReservation";

//Database credentials
static final String USER = "root";
//Put your mysql password to access DB
static final String PASS = "password";
	public static void main(String[] argv) {
		
        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?serverTimezone=UTC","root", PASS);
        	

        } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return;
        }

        if (connection != null) {
                System.out.println("You made it, take control your database now!");
        } else {
                System.out.println("Failed to make connection!");
        }
  }
}
