package data;

public class Transaction
{
	public String id;
	public String kind;
	public String itemID;
	public String amount;
	public String totalPrice;
	public String userID;
	public String time;
	
	public Transaction(String id, String kind, String itemID, String amount, String totalPrice, String userID, String time)
	{
		this.id = id;
		this.kind = kind;
		this.itemID = itemID;
		this.amount = amount;
		this.totalPrice = totalPrice;
		this.userID = userID;
		this.time = time;
	}
}
