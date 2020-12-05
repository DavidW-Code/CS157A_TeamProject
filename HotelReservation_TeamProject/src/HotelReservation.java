import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class HotelReservation {
static final String DB_URL = "jdbc:mysql://localhost/HotelReservation?serverTimezone=UTC";
static final String USER = "root";
static final String PASS = "ENTER PASSWORD HERE";

Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
CallableStatement call = null;

String query;

	public HotelReservation() throws SQLException {
		//Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		if (conn != null) {
			System.out.println("Connection success");
		}
		System.out.println("");
	}
	
	public int generateReservationNum() {
		Random r = new Random();
		return r.nextInt(999999-100000) + 100000;
	}
	
	public void archive(String cutoffDate) throws SQLException {
		call = conn.prepareCall("{call archivePayment(?)}");
		call.setString(1, cutoffDate);
		call.execute();
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from PaymentArchive");
		
		//Process the results
		while(rs.next()) {
			System.out.println("Archived Payment ID Data: " + rs.getInt("paymentID"));
		}
		
	}
	
	public void getAvailableRooms() throws SQLException {
		call = conn.prepareCall("{call getAvailableRooms()}");
		call.execute();
		ResultSet rs = call.getResultSet();
		
		while(rs.next()) {
			System.out.println("RoomID: " + rs.getInt("roomID") + " Room Type: " + rs.getString("room_type")
							+ " Cost: " + rs.getInt("cost"));
		}
		
	}
	
	
	public void reserveRoom(String guestName, int partySize, int roomID, String startDate, String endDate, int paymentID) throws SQLException {
		int reservationNum = generateReservationNum();
		
		call = conn.prepareCall("{call createReservation(?,?,?,?,?,?,?)}");
		call.setString(1, guestName);
		call.setInt(2, partySize);
		call.setInt(3, roomID);
		call.setInt(4, reservationNum);
		call.setInt(5, paymentID);
		call.setString(6, startDate);
		call.setString(7, endDate);
		call.execute();
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from Reservations where reservationID = " + reservationNum);
		
		//Process the results
		while(rs.next()) {
			System.out.println("Reservation Made: " + rs.getInt("reservationID"));
		}
		
	}
	
	public void getReservationList() throws SQLException {
		call = conn.prepareCall("{call getReservationList()}");
		call.execute();
		ResultSet rs = call.getResultSet();

		while(rs.next()) {
			System.out.println("ReservationID: " + rs.getInt("reservationID") + ", GuestID: " + rs.getInt("guestID") 
								+ ", RoomID: " + rs.getInt("roomID") + ", Check In Date: " + rs.getDate("checkin_date")
								+ ", Check Out Date: " + rs.getDate("checkout_date"));
		}
	}
	
	public void getGuestInfo(int guestID) throws SQLException {
		call = conn.prepareCall("{call getGuestInfo(?)}");
		call.setInt(1, guestID);
		call.execute();
		ResultSet rs = call.getResultSet();

		while(rs.next()){
			System.out.println("GuestID: " + rs.getInt("guestID") + ", Guest Name: " + rs.getString("name")
								+ ", Party Size: " + rs.getInt("party_size") + ", ReservationID: " + rs.getInt("reservationID")
								+ ", RoomID: " + rs.getInt("roomID"));
		}
	}
	
	public void getRoomCost() throws SQLException {
		call = conn.prepareCall("{call getRoomCosts()}");
		call.execute();
		ResultSet rs = call.getResultSet();

		while(rs.next()){
			System.out.println("Room Type: " + rs.getString("room_type") + " Cost: " + rs.getInt("cost"));
		}
	}

	
	public void extendReservation(String checkoutDate, int reservationID) throws SQLException {
		call = conn.prepareCall("{call extendReservation(?,?)}");
		call.setString(1, checkoutDate);
		call.setInt(2, reservationID);
		call.execute();
		ResultSet rs = call.getResultSet();
		
		while(rs.next()){
			System.out.println("Reservation ID: " + rs.getString("reservationID") 
							+ " New CheckOutDate " + rs.getString("checkout_date"));
		}
		
	}
	
	public void getRoomInfo(int roomID) throws SQLException {
		call = conn.prepareCall("{call getRoomInfo(?)}");
		call.setInt(1, roomID);
		call.execute();
		ResultSet rs = call.getResultSet();
		
		while(rs.next()){
			System.out.println("Room ID: " + rs.getInt("roomID") + " Room Type " + rs.getString("room_type") 
							+ " WIFI: " + roomCheck(rs.getInt("wifi")) + " View: " + roomCheck(rs.getInt("view"))
							+ " Breakfast: " + roomCheck(rs.getInt("breakfast")));
		}
		
	}
	
	public String roomCheck(int num) {
		if (num == 1) {
			return "YES";
		}
		else {
			return "NO";
		}
	}
	
	public void applyDiscount(int guestID) throws SQLException {
		call = conn.prepareCall("{call memberDiscount(?)}");
		call.setInt(1, guestID);
		call.execute();
		ResultSet rs = call.getResultSet();
		
		while(rs.next()){
			System.out.println("GuestID: " + rs.getInt("guestID") + " roomID: " + rs.getInt("roomID")
							+ " New amount due: " + rs.getInt("amountDue"));
		}
	}
	
	public void updateGuest(int guestID, String name, int partySize) throws SQLException {
		call = conn.prepareCall("{call updateGuest(?,?,?)}");
		call.setInt(1, guestID);
		call.setString(2, name);
		call.setInt(3, partySize);
		call.execute();
		ResultSet rs = call.getResultSet();
		
		while(rs.next()){
			System.out.println("GuestID: " + rs.getInt("guestID") + " Guest Name: " + rs.getString("name")
							+ " Party Size: " + rs.getInt("party_size"));
		}
		
	}
	
	public void addAmenaties(int guestID) throws SQLException {
		call = conn.prepareCall("{call addAmenaties(?)}");
		call.setInt(1, guestID);
		call.execute();
		ResultSet rs = call.getResultSet();
		
		while(rs.next()){
			System.out.println("WIFI and Breakfast added to room cost, new total cost: " + rs.getInt("amountDue"));
		}
	}
	
	
}
