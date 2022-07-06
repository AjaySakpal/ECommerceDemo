package ECommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class StoreFunctionality {
	
     	static void storeinventory(Connection con) {

		//Code to get the list of products from database

		System.out.format("%55s","***Store Inventory***");
		
		String sql="select *from Inventory";
		try {
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet res= ps.executeQuery();
		System.out.println("\n----------------------------------------------------------------------------------------------------");
		System.out.format("%5s %25s %10s %30s %15s","Product ID","Product name","Price","Product desc","Qty");
		
		System.out.println("\n----------------------------------------------------------------------------------------------------");
		while(res.next()) {
			System.out.format("%5s %30s %10s %30s %15s",res.getInt(1),res.getString(4),res.getString(3),res.getString(2),res.getString(5));
			System.out.println();
		}
		System.out.println("----------------------------------------------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	static void store_User(Connection con) {

		//Code to get the list of products from database

		System.out.format("%55s","***Store Inventory***");
		
		String sql="select *from Inventory";
		try {
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet res= ps.executeQuery();
		System.out.println("\n-------------------------------------------------------------------------------------------");
		System.out.format("%5s %25s %10s %30s","Product ID","Product name","Price","Product desc");
		
		System.out.println("\n-------------------------------------------------------------------------------------------");
		while(res.next()) {
			System.out.format("%5s %30s %10s %30s",res.getInt(1),res.getString(4),res.getString(3),res.getString(2));
			System.out.println();
		}
		System.out.println("-------------------------------------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	void purchase(LoginDetails login, Connection con) {
		
		//Code to add material in cart and purchase
		String sql="Select *from inventory where Product_ID=";
		Scanner scanner=new Scanner(System.in);
		ArrayList<Ecart> cartarray=new ArrayList<Ecart>();
		String Username=login.getUser_Name();
		int Product_ID;
		int QtyOrdered;
		double TotalPrice;
		boolean b=true;
		int product_count=0;
		
		while(b) {
			Ecart cart=new Ecart();
			cart.setUsername(Username);
			System.out.print("\nChoose product ID to order: ");
			Product_ID=scanner.nextInt();
			cart.setProduct_ID(Product_ID);
			int count=0;
			try {
			PreparedStatement stmt=con.prepareStatement(sql+Product_ID);
			ResultSet res= stmt.executeQuery();
			
			while(res.next()) {
				System.out.print("Enter Qty to order: ");
				QtyOrdered=scanner.nextInt();
				if(QtyOrdered<res.getInt(5)&&res.getInt(5)>0) {
				cart.setQtyOrdered(QtyOrdered);
				cart.setProduct_Name(res.getString(4));

				TotalPrice=QtyOrdered*res.getInt(3);
				cart.setTotalPrice(TotalPrice);
				System.out.format("%55s","***Selected Product added to cart***");
				System.out.println("\n-------------------------------------------------------------------------------------------");
				System.out.format("%12s %15s %8s %12s","Product_ID","Product_Name","Qty","Price");
				System.out.println("\n-------------------------------------------------------------------------------------------");
				System.out.format("%7s %15s %12s %14s",cart.getProduct_ID(),cart.getProduct_Name(),cart.getQtyOrdered(),cart.getTotalPrice());
				System.out.println();
				System.out.println("\n-------------------------------------------------------------------------------------------");
				cartarray.add(cart);
				product_count++;
				count++;
				}
				else {
					System.out.println("Stock not available");
					break;
				}
			
			}
			if(count==0) {
			System.out.print("Selected Product ID doesent Exists");	
			System.out.println("\nEnter 'y' to add another product  \nEnter any key to Exit cart and logout");
			System.out.print("Choice: ");  
			char choice=scanner.next().charAt(0);
			
			if(choice=='y'||choice=='Y') {
				continue;
			}
			else  {
				break;
			}	
			}
			else {
				System.out.print("\nEnter 'y' Add more products\nEnter any key to Exit cart\n");
				System.out.print("Choice: ");
				char choice=scanner.next().charAt(0);
				
				if(choice=='y'||choice=='Y') {
					continue;
				}
				else  {
					break;
				}	
			}
			}
			catch(Exception e) {
				System.out.println(e);
			}
			
			
			
			scanner.close();
			
		}
		
		double billamount=0;
		Iterator<Ecart> itr1=cartarray.iterator();
		System.out.println("\n*****Receipt******");
		System.out.println("\n-------------------------------------------------------------------------------------------");
		System.out.format("%15s %12s %15s %8s %12s","Customer_Name","Product_ID","Product_Name","Qty","Price");
		System.out.println("\n-------------------------------------------------------------------------------------------");
		while(itr1.hasNext()) {
			Ecart ecart=itr1.next();
			System.out.format("%10s %12s %15s %12s %12s",ecart.getUsername(),ecart.getProduct_ID(),ecart.getProduct_Name(),ecart.getQtyOrdered(),ecart.getTotalPrice());
			System.out.println();
			billamount=billamount+ecart.getTotalPrice();
			
		}
		System.out.println("\n-------------------------------------------------------------------------------------------");
		System.out.print("*************Total Price*************");
		System.out.format("%27s","INR "+billamount);
		System.out.println("\n-------------------------------------------------------------------------------------------");
		
		if(product_count!=0) {
		System.out.print("\nPress y to complete payment of >>"+billamount+"\nPress any key to abort transaction and logout\nChoice: ");
		char choice=scanner.next().charAt(0);
		if(choice=='y'||choice=='Y') {
			System.out.println("\nPayment Successful.\nProduct Will be delivered soon\n\nThank You and Visit Again :)\n******Logging out*******");
			StoreFunctionality store=new StoreFunctionality();
			Iterator<Ecart> itr=cartarray.iterator();
			while(itr.hasNext()) {
				store.updateinventory(itr.next());
				
			}
			//System.out.println("Updated order history");
		}
		else {
			System.out.println("Transaction Failed. Please try again later.\n******Logging out*******");
		}
		
	//storeinventory(con);
	
		}
		else {
			System.out.println("Logging out");
		}
		
		MainClass main=new MainClass();
		main.main(null);
	}
	
	void updateinventory(Ecart cart) {
		UserDetails user=new UserDetails();
		Connection con=user.getConnectionObj();
		String sql="select Product_Qty from Inventory where (Product_ID="+cart.getProduct_ID()+")";
		String sql1 ="Update Inventory set Product_Qty=? where (Product_ID=?)";
		try {
			Statement stmt=con.createStatement();
		ResultSet res=	stmt.executeQuery(sql);
		int Qty=0;
		while(res.next()) {
			Qty=res.getInt(1)-cart.getQtyOrdered();
		}
		PreparedStatement ps=con.prepareStatement(sql1);
		ps.setInt(1, Qty);
		ps.setInt(2, cart.getProduct_ID());
		ps.execute();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		updateorderhistory(cart);
		
		
	}
	
	void updateinventory(int Product_ID, Connection con) {
		String sql="select Product_Qty from Inventory where (Product_ID="+Product_ID+")";
		String sql1 ="Update Inventory set Product_Qty=? where (Product_ID=?)";
		System.out.println("Enter Qty to be added");
		Scanner scanner=new Scanner(System.in);
		int add=scanner.nextInt();
		try {
			Statement stmt=con.createStatement();
		ResultSet res=	stmt.executeQuery(sql);
		int Qty=0;
		while(res.next()) {
			Qty=res.getInt(1)+add;
		}
		PreparedStatement ps=con.prepareStatement(sql1);
		ps.setInt(1, Qty);
		ps.setInt(2, Product_ID);
		ps.execute();
		System.out.println("Product Qty added");
	//	storeinventory(con);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		scanner.close();
	}
	
	static void updateorderhistory(Ecart cart) {
	//	System.out.println(cart.toString());
		UserDetails user=new UserDetails();
		Connection con=user.getConnectionObj();
		String search="select Product_name from Inventory where(Product_ID="+cart.getProduct_ID() +")";
		String sql="insert into OrderHistory(Date_Time, Username, Product_ID, Product_Name, Ordered_Qty, Total_Price)Values (?,?,?,?,?,?)";
		try {
			PreparedStatement ps_1=con.prepareStatement(search);
			ResultSet res= ps_1.executeQuery();
			while(res.next()) {
				cart.setProduct_Name(res.getString(1));
			}
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, cart.getDate_Time());
		ps.setString(2, cart.getUsername());
		ps.setInt(3, cart.getProduct_ID());
		ps.setString(4, cart.getProduct_Name());
		ps.setInt(5, cart.getQtyOrdered());
		ps.setDouble(6, cart.getTotalPrice());
		ps.execute();
		
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	void addproduct(Connection con) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the Product Description to be added");
		String Product_Description=scanner.nextLine();
		System.out.println("Enter the Product Name");
		String Product_name=scanner.nextLine();
		System.out.println("Enter the Product Price");
		double  Product_Price=scanner.nextDouble();
		System.out.println("Enter the Product Qty");
		int Product_Qty=scanner.nextInt();
		String sql="insert into Inventory(Product_Description,Product_Price,Product_name,Product_Qty )values(?,?,?,?)";
			try {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, Product_Description);
				ps.setDouble(2, Product_Price);
				ps.setString(3, Product_name);
				ps.setInt(4, Product_Qty);
				ps.execute();
				System.out.println("Data Updated");
				//storeinventory(con);
				
			}
			catch(Exception e){
				
			}
		scanner.close();
	}

	static void userlist(Connection con) {
		//Code to get the list of Users from database
		System.out.format("%55s","***User-List***");
		
		String sql="select User_ID,Email_ID,User_Name,UserRole from userdetails where UserRole!='Admin'";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet res= ps.executeQuery();
			System.out.println("\n-------------------------------------------------------------------------------------------");
			System.out.format("%5s %15s %15s %15s","User_ID","Email_ID","User_Name","UserRole");
			
			System.out.println("\n-------------------------------------------------------------------------------------------");
			while(res.next()) {
				System.out.format("%5s %15s %15s %15s",res.getInt(1),res.getString(2),res.getString(3),res.getString(4));
				System.out.println();
			}
			System.out.println("-------------------------------------------------------------------------------------------");
			}
			catch(Exception e) {
				System.out.println(e);
			}
	}

	static void orderhistory(Connection con) {
		//Code to get the order history from database
		Scanner scanner=new Scanner(System.in);
		System.out.print("\nEnter the option as required\n1. Display complete order history till date\n2. Display the order history of specified user\n\nChoice: ");
		int choice=scanner.nextInt();
		String sql = "select *from OrderHistory";
		String name = null;
		if(choice==1) {
			sql="select *from OrderHistory";	
		}
		else if(choice==2) {
			userlist(con);
			System.out.println("Choose User ID from the list");
			int user=scanner.nextInt();
			
			
			try {
				String s="select *from UserDetails where (User_ID="+user+")";
				Statement stmt=con.createStatement();
			ResultSet res=	stmt.executeQuery(s);
			while(res.next()) {
				name=res.getString(3);
			}
			sql="select *from OrderHistory where (Username='"+name+"')";
			}
			catch(Exception e) {
				System.out.println(e);
			}
			
			
		}
		else {
			System.out.println("Wrong Choice\nChoose Again");
			orderhistory(con);
		}

		System.out.format("%55s","***Store Order History***");
		
		
		try {
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet res= ps.executeQuery();
		System.out.println("\n-------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %20s %20s %20s %15s %15s","Date_Time","Username","Product_ID","Product_Name","Ordered_Qty","Total_Price");
		
		System.out.println("\n-------------------------------------------------------------------------------------------");
		while(res.next()) {
			System.out.format("%5s %15s %15s %25s %15s %15s",res.getString(1),res.getString(2),res.getInt(3),res.getString(4),res.getInt(5),res.getInt(6));
			System.out.println();
		}
		System.out.println("\n-------------------------------------------------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	
	}
	
	static void orderhistory(int User_ID, Connection con) {
		//Code to get the order history from database
		String s="Select User_Name from UserDetails where (User_ID="+User_ID+")";
		
System.out.format("%55s","***Store Order History***");
		
		
		try {
		Statement st=con.createStatement();
		ResultSet r=st.executeQuery(s);
		String name="";
		while(r.next()) {
			name=r.getString(1);
		}
		String sql="select *from OrderHistory where (Username="+name+ ")";
		PreparedStatement ps=con.prepareStatement(sql);
		
		ResultSet res= ps.executeQuery();
		System.out.println("\n-------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %20s %20s %20s %15s %15s","Date_Time","Username","Product_ID","Product_Name","Ordered_Qty","Total_Price");
		
		System.out.println("\n-------------------------------------------------------------------------------------------");
		while(res.next()) {
			System.out.format("%5s %15s %15s %25s %15s %15s",res.getString(1),res.getString(2),res.getInt(3),res.getString(4),res.getInt(5),res.getInt(6));
			System.out.println();
		}
		System.out.println("\n-------------------------------------------------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println(e);
		}
			}
	void deleteproduct(Connection con) {
		String sql="delete from inventory where Product_ID=?";
		Scanner scanner=new Scanner(System.in);
		try {
		while(true) {
		System.out.println("Enter the Product ID to delete product");
		int Product_ID=scanner.nextInt();
		
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, Product_ID);
			ps.execute();
			System.out.println("Data deleted");
			System.out.println("Enter 1 to delete another product\nEnter any key to abort process");
			int choice=scanner.nextInt();
			if(choice==1) {
				continue;
			}
			else {
				System.out.println("Database Updated");
				break;
			}
			
		}
		
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	scanner.close();	
	}
}

