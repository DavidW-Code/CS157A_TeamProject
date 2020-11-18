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
			
			//Create a Reservation
			if (selection.equals("Reserve")) {
				System.out.println("Enter Guest Name");
				String guestName = input.nextLine();
				System.out.println("Enter Size of Party");
				int partySize = input.nextInt();
				System.out.println("Enter Starting Date of Stay (i.e yyyy-mm-dd)");
				String startDate = input.nextLine();
				System.out.println("Enter Ending Date of Stay (i.e yyyy-mm-dd)");
				String endDate = input.nextLine();
				
			}
			
			//Get Reservation List
			else if (selection.equals("Reservation List")) {
				hotel.getReservationList();
			}
			
			//Get Guest Information
			else if (selection.equals("Guest Info")) {
				System.out.println("Enter Guest ID");
				String guestID = input.nextLine();
				
				hotel.getGuestInfo(guestID);
			}
			
			//Find Available Rooms
			else if (selection.equals("Find")) {
				System.out.println("[All] Rooms or Specific Room [Type]?");
				selection = input.nextLine();
				
				String roomType = "";
				boolean all = false;
				if (selection.equals("All")) {
					all = true;
				}
				else {
					all = false;
					System.out.println("Enter Room Type ('King' / 'Queen' / 'Suite' )");
					roomType = input.nextLine();
				}
				
				hotel.getAvailableRooms(roomType,all);
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
		System.out.println("[Reserve] a room");
		
		System.out.println("");
		
		System.out.println("Admin Requests");
		System.out.println("[Reservation List] list of all current reservations");
		System.out.println("[Guest Info] guest information");
		System.out.println("[Find] find number of empty rooms");
		
		System.out.println("");
		
		System.out.println("Exit Menu");
		System.out.println("[Quit]");
		
		System.out.println("----------------------");
	}
	
	public static void main(String[] args) throws SQLException {
		HotelReservationTester test = new HotelReservationTester();
	}
}
