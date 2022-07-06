package ECommerce;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClass {
/*the purpose of this class is to start the program execution.
 * This is the class from where complete code is controlled.
 */
	public static void main(String[] args) {
		
		/*At first the user will be given options to start with existing login or create new login.
		 */
//		UserDetails u=new UserDetails();
//		Connection con=u.getConnectionObj();
//StoreFunctionality s=new StoreFunctionality();
//s.orderhistory(con);

	try {
		System.out.println("Choose your option\n1. New Sign-up\n2. User Login\n3. Exit");
		Scanner scanner=new Scanner(System.in);
		int choice=scanner.nextInt();
		UserDetails user=new UserDetails();
		//based on the choice selected we can execute the code. for this the switch case is used
		switch (choice) {
		case 1:
			//For new login another class from user is called. i.e. create user
			user.createuser();
			break;
		case 2:
			//For existing login the login credentials are asked. i.e. email, name and password
			String Email_ID=null;
			scanner.nextLine();
			System.out.println("Enter Email ID");
			Email_ID=scanner.nextLine();	
			System.out.println("Enter Password");
			String User_Password=scanner.nextLine();
			//Once login credentials are taken those are passed as arguments into method getUser from User class
			//to get the user name and login to stores. 
			LoginDetails login=new LoginDetails();
			//login.setContact_number(Contact_number);
			login.setEmail_ID(Email_ID);
			login.setUser_Password(User_Password);
			user.getUser(login);
			break;
		case 3:
			System.out.println("Thank You and Visit Again :) Good-Bye");
			System.exit(0);
			break;
		default:
			
			System.out.println("\nWrong choice\nRestarting the store\n");
			main(null);;
		}
		scanner.close();
	}
	catch(InputMismatchException e) {
		System.out.println("Incorrect Input type");
		System.out.println("Exiting from the store");
		System.exit(1);
	}
	}
}
		
	
		
		
		
	


