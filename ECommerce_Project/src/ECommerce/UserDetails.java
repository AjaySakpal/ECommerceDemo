package ECommerce;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Properties;
import java.util.Scanner;

public class UserDetails {

	//Method to return Connection Object
		Connection getConnectionObj() {
			
			 Connection con=null;
			 FileInputStream fis=null;
			try {
				fis=new FileInputStream("E:\\Java_2\\ECommerce_Project\\src\\ECommerce\\databaseID.properties");
				Properties p=new Properties();
				p.load(fis);
				String databaseaddress=p.getProperty("databaseaddress");
				String databasename=p.getProperty("databasename");
				String databasepassword=p.getProperty("databasepassword");
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(databaseaddress,databasename,databasepassword);
			}
			catch(Exception e) {
				System.out.println(e);
			}
			return con;
		}
	//Method to take user details to create new login
	void createuser() {
		String Email_ID=null;
		String User_Name=null;
		String User_Password=null;
		
		Scanner scanner =new Scanner(System.in);
		
		System.out.println("Enter Email_ID to to Sign-in");
		Email_ID=scanner.nextLine();
		
		System.out.println("Enter the name");
		User_Name=scanner.nextLine();
		
		System.out.println("Enter the Password");
		User_Password=scanner.nextLine();
		
		LoginDetails login=new LoginDetails();
		
		login.setEmail_ID(Email_ID);
		login.setUser_Name(User_Name);
		login.setUser_Password(User_Password);
		
		UserDetails user=new UserDetails();
		
		Connection con=user.getConnectionObj();
		
		user.adduser(con, login);
	scanner.close();	
	}
	
	//Method to add collected details into the user database
	void adduser(Connection con,LoginDetails login) {
		try {
		PreparedStatement ps=con.prepareStatement("insert into UserDetails(Email_ID, User_Name, User_Password, UserRole)"
				+ "Values (?,?,?,?)");
		ps.setString(1, login.getEmail_ID());
		ps.setString(2, login.getUser_Name());
		ps.setString(3, login.getUser_Password());
		ps.setString(4, login.getUserRole());
		ps.execute();
		System.out.println("\nUser Added to the database\n");
		System.out.println("\nWelcome "+login.getUser_Name());
		}
		//In case wrong credentials are used 
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("\nAccount with mentioned Credentials already exists\nChoose unique Contact Detials or login to available account\n");
			MainClass mainclass=new MainClass();
			mainclass.main(null);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		StoreFunctionality store=new StoreFunctionality();
		//store.storeinventory(con);
		store.store_User(con);
		store.purchase(login, con);
	}
	
	

	//Method to access Existing user
	void getUser(LoginDetails login) {
		//LoginDetails login=new LoginDetails();
		UserDetails user=new UserDetails();
		Connection con=user.getConnectionObj();
		String sql=null;
		try{
			sql="Select *from UserDetails where Email_ID='"+login.getEmail_ID()+"' and User_Password='"+login.getUser_Password()+"'";	
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet res=ps.executeQuery();
			int rescount=0;
			while(res.next()) {
			if(res.getString(5).equals("Admin")) {
				System.out.println("\nWelcome Admin");
				user.adminactivities();
				}
			else {
				System.out.println("\nWelcome to Online Stores "+res.getString(3)+"\n");
				login.setUser_Name(res.getString(3));
		
				StoreFunctionality store=new StoreFunctionality();
				//store.storeinventory(con);
				store.store_User(con);
				store.purchase(login, con);
				}
				rescount++;
			}
			if(rescount==0) {
				System.out.println("Login Credentials are wrong");
				MainClass mainclass=new MainClass();
				mainclass.main(null);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	void adminactivities() {
		StoreFunctionality store=new StoreFunctionality();
		UserDetails user=new UserDetails();
		Connection con=user.getConnectionObj();
		Scanner scanner=new Scanner(System.in);
		
		System.out.println("Choose the Activity\n1. Check Product List\n2. Check user details\n3. Check Order History\n4. Log-out");
		int choice=scanner.nextInt();
		switch (choice) {
		case 1:
			store.storeinventory(con);
			System.out.println("Choose the activity to perform\n1. Add Qty\n2. Add Product\n3. Delete Product\n4. Exit");
			choice=scanner.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter the Product ID for which qty is to be added");
				int Product_ID=scanner.nextInt();
				store.updateinventory(Product_ID, con);
				user.adminactivities();
				break;
			case 2:
				store.addproduct(con);
				user.adminactivities();
				break;
			case 3:
				store.deleteproduct(con);
				user.adminactivities();
				break;
			case 4:
				user.adminactivities();
				break;
			default:
				System.out.println("Wrong Option");
				user.adminactivities();
			}
			user.adminactivities();
			break;
		case 2:
			store.userlist(con);
			user.adminactivities();
			break;
		case 3:
			
			store.orderhistory(con);
			System.out.println("Order history done");
			user.adminactivities();
			break;
		case 4:
			System.out.println("Thank You and Visit Again :)");
			System.exit(1);
		default:
			System.out.println("Wrong Option");
			user.adminactivities();
		}
	
	}
}
