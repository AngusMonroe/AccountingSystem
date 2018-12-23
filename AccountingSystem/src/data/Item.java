package data;

import org.json.JSONException;
import org.json.JSONObject;

public class Item
{
	public String id;
	public String name;
	public String price;
	public String amount;
	
	public Item(String id, String name, String price, String amount)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsObject = new JSONObject();
		try {
			jsObject.put("id", this.id);
			jsObject.put("name", this.name);
			jsObject.put("price", this.price);
			jsObject.put("amount", this.amount);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsObject;
	}
}
