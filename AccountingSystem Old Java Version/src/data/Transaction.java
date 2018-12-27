package data;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public JSONObject toJSONObject() {
		JSONObject jsObject = new JSONObject();
		try {
			jsObject.put("id", this.id);
			jsObject.put("kind", this.kind);
			jsObject.put("itemID", this.itemID);
			jsObject.put("amount", this.amount);
			jsObject.put("totalPrice", this.totalPrice);
			jsObject.put("userID", this.userID);
			jsObject.put("time", this.time);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsObject;
	}
}
