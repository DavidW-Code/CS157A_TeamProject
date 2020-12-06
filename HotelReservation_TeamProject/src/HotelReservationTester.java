import java.sql.SQLException;
import java.util.Scanner;

public class HotelReservationTester {
HotelReservation hotel;
String selection;

	public HotelReservationTester() throws SQLException {
		hotel = new HotelReservation();
		Scanner input = new Scanner (System.in);
		
		printMenu();
		
		System.out.print("Enter Selection: " );
		selection = input.nextLine();
		while (!selection.equals("Quit")) {
			
			if (selection.equals("Archive")) {
				System.out.println("Enter cut off date");
				String cutDate = input.nextLine();
				hotel.archive(cutDate);
			}
			
			//Create a Reservation
			else if (selection.equals("Reserve")) {
				System.out.println("Enter Starting Date of Stay (i.e yyyy-mm-dd)");
				String startDate = input.nextLine();
				
				System.out.println("Enter Ending Date of Stay (i.e yyyy-mm-dd)");
				String endDate = input.nextLine();
				
				System.out.println("Enter Guest Name");
				String guestName = input.nextLine();
				
				System.out.println("Enter Size of Party");
				int partySize = input.nextInt();
				
				System.out.println("Select a room number");
				hotel.getAvailableRooms();
				int roomID = input.nextInt();
				
				System.out.println("Enter paymentID information");
				int paymentID = input.nextInt();
				input.nextLine();
				
				hotel.reserveRoom(guestName, partySize, roomID, startDate, endDate, paymentID);
				
			}
			
			//Get Reservation List
			else if (selection.equals("Reservation List")) {
				hotel.getReservationList();
			}
			
			//Get Guest Information
			else if (selection.equals("Guest Info")) {
				System.out.println("Enter guestID");
				int guestID = input.nextInt();
				input.nextLine();
				hotel.getGuestInfo(guestID);
			}
			
			//Find Available Rooms
			else if (selection.equals("Find")) {
				hotel.getAvailableRooms();
			}
			
			else if (selection.equals("Cost")){
				hotel.getRoomCost();
			}
			
			else if (selection.equals("Extend")) {
				System.out.println("Enter new check out date");
				String checkoutDate = input.nextLine();
				System.out.println("Enter reservationID");
				int reservationID = input.nextInt();
				input.nextLine();
				hotel.extendReservation(checkoutDate, reservationID);
			}
			
			else if (selection.equals("Room Info")) {
				System.out.println("Enter roomID");
				int roomID = input.nextInt();
				input.nextLine();
				hotel.getRoomInfo(roomID);
			}
			
			else if (selection.equals("Member Discount")) {
				System.out.println("Enter guestID");
				int guestID = input.nextInt();
				input.nextLine();
				hotel.applyDiscount(guestID);
			}
			
			else if (selection.equals("Update")) {
				System.out.println("Enter new name");
				String name = input.nextLine();
				System.out.println("Enter new party size");
				int partySize = input.nextInt();
				System.out.println("Enter guestID");
				int guestID = input.nextInt();
				input.nextLine();
				hotel.updateGuest(guestID, name, partySize);
			}
			
			else if (selection.equals("Amenaties")) {
				System.out.println("Enter guestID");
				int guestID = input.nextInt();
				input.nextLine();
				hotel.addAmenaties(guestID);
			}
			
			else if (selection.equals("Party Size")) {
				System.out.println("Enter party size");
				int partySize = input.nextInt();
				input.nextLine();
				hotel.getPartySize(partySize);
			}
			
			else if (selection.equals("Expensive Rooms")) {
				hotel.getExpensive();
			}
			
			else if (selection.equals("Least Expensive Rooms")) {
				hotel.leastExpensive();
			}
			
			else if (selection.equals("Wifi and Breakfast")) {
				hotel.wifiAndBreakfast();
			}
			
			else {
				System.out.println("Incorrect input, Check type case and spacing!");
			}
			
			System.out.println("");
			printMenu();
			System.out.println("Enter Selection: ");
			selection = input.nextLine();
		}
		
		hotel.stmt.close();
		hotel.conn.close();
		System.out.println("Finished");
	}
	
	//Selection Menu
	public void printMenu() {
		System.out.println("---------MENU---------");
		System.out.println("Guest Requests");
		System.out.println("[Reserve] Create a reservation");
		System.out.println("[Cost] Gives costs of all room types");
		System.out.println("[Extend] Extends current reservation");
		System.out.println("[Room Info] Gives information about particular room");
		System.out.println("[Update] Updates guest information");
		System.out.println("[Amenaties] Adds amentaties to guest room");
		System.out.println("[Expensive Rooms] Gets the most expensive rooms of each type");
		System.out.println("[Least Expensive Rooms] Gets the least expensive rooms of each type");
		
		System.out.println("");
		
		System.out.println("Admin Requests");
		System.out.println("[Archive] Archives payment information");
		System.out.println("[Reservation List] Gets a list of all reservations");
		System.out.println("[Guest Info] Gets information about particular guest");
		System.out.println("[Find] Gets avilable rooms");
		System.out.println("[Member Discount] Applies 15% discount to total cost");
		System.out.println("[Party Size] Gets the guests with specific party size");
		System.out.println("[Wifi and Breakfast] Gets the rooms that offer both wifi and breakfast");
		
		System.out.println("");
		
		System.out.println("Exit Menu");
		System.out.println("[Quit]");
		
		System.out.println("----------------------");
	}
	
	public static void main(String[] args) throws SQLException {
		HotelReservationTester test = new HotelReservationTester();
	}
}
