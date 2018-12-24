package data;

import org.json.JSONException;
import org.json.JSONObject;

public class Balance
{
	public String id;
	public String profit;
	public String date;
	
	public Balance(String id, String profit, String date)
	{
		this.id = id;
		this.profit = profit;
		this.date = date;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsObject = new JSONObject();
		try {
			jsObject.put("id", this.id);
			jsObject.put("profit", this.profit);
			jsObject.put("date", this.date);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsObject;
	}
	
}
