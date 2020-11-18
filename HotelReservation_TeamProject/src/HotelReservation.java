import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelReservation {
static final String DB_URL = "jdbc:mysql://localhost/HotelReservation?serverTimezone=UTC";
static final String USER = "root";
static final String PASS = "ENTER PASSWORD HERE";

Connection conn = null;
Statement stmt = null;
ResultSet rs = null;

	public HotelReservation() throws SQLException {
		//Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		if (conn != null) {
			System.out.println("Connection success");
		}
		System.out.println("");
	}

	public void reserveRoom(String guestName, int partySize, String startDate, String endDate) throws SQLException {
		//Execute a query
		stmt = conn.createStatement();
		String insertStatement = "INSERT INTO Guest VALUES(1," + ",'" + guestName + "'," + partySize + ",'" + startDate
								+ "','" + endDate + "');";
		stmt.executeUpdate(insertStatement);
		
		//Process the results
		
		
	}
	
	public void getReservationList() throws SQLException {
		//Execute a query
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from Reservations");
		
		//Process the results
		while(rs.next()) {
			System.out.println("ReservationID: " + rs.getInt("reservationID") + ", GuestID: " + rs.getInt("guestID") 
								+ ", RoomID: " + rs.getInt("roomID") + ", Check In Date: " + rs.getDate("checkin_date")
								+ ", Check Out Date: " + rs.getDate("checkout_date"));
		}
	}
	
	public void getGuestInfo(String guestID) throws SQLException {
		//Execute a query
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from Guest where guestID = " + guestID);
			      
		//Process the results
		while(rs.next()){
			System.out.println("GuestID: " + rs.getInt("guestID") + ", Guest Name: " + rs.getString("name")
								+ ", Party Size: " + rs.getInt("party_size") + ", ReservationID: " + rs.getInt("reservationID")
								+ ", RoomID: " + rs.getInt("roomID"));
		}
	}
	
	public void getAvailableRooms(String roomType, boolean all) throws SQLException {
		//Execute a query
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from Rooms where room_status = 'Available';");
		
		while(rs.next()) {
			if (all) {
				System.out.println("RoomID: " + rs.getInt("roomID") + ", Floor: " + rs.getInt("floor")
					+ ", Room Type: " + rs.getString("room_type") + ", Room Cost: " + rs.getInt("cost"));
			}
			else {
				if (rs.getString("room_type").equals(roomType)) {
					System.out.println("RoomID: " + rs.getInt("roomID") + ", Floor: " + rs.getInt("floor")
						+ ", Room Type: " + rs.getString("room_type") + ", Room Cost: " + rs.getInt("cost"));
				}
			}
		}
		
	}
	
}
