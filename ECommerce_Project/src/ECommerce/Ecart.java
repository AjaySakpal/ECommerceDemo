package ECommerce;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ecart {
	DateTimeFormatter dt=DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
	LocalDateTime now=LocalDateTime.now();
	String Date_Time=dt.format(now);
	String Username;
	int Product_ID;
	String Product_Name;
	

	int QtyOrdered;
	
	double TotalPrice;
	
	public String getDate_Time() {
		return Date_Time;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public int getProduct_ID() {
		return Product_ID;
	}
	public void setProduct_ID(int product_ID) {
		Product_ID = product_ID;
	}
	public int getQtyOrdered() {
		return QtyOrdered;
	}
	public void setQtyOrdered(int qtyOrdered) {
		QtyOrdered = qtyOrdered;
	}
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getProduct_Name() {
		return Product_Name;
	}
	public void setProduct_Name(String product_Name) {
		Product_Name = product_Name;
	}
	
	@Override
	public String toString() {
		return "Ecart [Date_Time=" + Date_Time + ", Username=" + Username + ", Product_ID=" + Product_ID
				+ ", QtyOrdered=" + QtyOrdered + ", TotalPrice=" + TotalPrice + "]";
	}
	
	
	}


