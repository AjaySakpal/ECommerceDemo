package ECommerce;

public class LoginDetails {
	
	//A separate class with user details is made to ensure set of user details by encapsulation
	private long Contact_number;
	private String Email_ID;
	private String User_Name;
	private String User_Password;
	private final String UserRole="User";
	static String defaultadminID="store@admin";
	static String adminpassword="password";
	
	//Getter setter methods are used to set and access the data
	public long getContact_number() {
		return Contact_number;
	}
	public void setContact_number(long contact_number) {
		Contact_number = contact_number;
	}
	public String getEmail_ID() {
		return Email_ID;
	}
	public void setEmail_ID(String email_ID) {
		Email_ID = email_ID;
	}
	public String getUser_Name() {
		return User_Name;
	}
	public void setUser_Name(String user_Name) {
		User_Name = user_Name;
	}
	public String getUser_Password() {
		return User_Password;
	}
	public void setUser_Password(String user_Password) {
		User_Password = user_Password;
	}
	public String getUserRole() {
		return UserRole;
	}
	@Override
	public String toString() {
		return "LoginDetails [Contact_number=" + Contact_number + ", Email_ID=" + Email_ID + ", User_Name=" + User_Name
				+ ", User_Password=" + User_Password + ", UserRole=" + UserRole + "]";
	}
	
}
