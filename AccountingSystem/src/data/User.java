package data;

import org.json.JSONException;
import org.json.JSONObject;

public class User
{
	public String id;
	public String name;
	public String password;
	public String kind;
	
	public User(String id, String name, String password, String kind) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.kind = kind;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsObject = new JSONObject();
		try {
			jsObject.put("id", this.id);
			jsObject.put("name", this.name);
			jsObject.put("password", this.password);
			jsObject.put("kind", this.kind);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsObject;
	}
	
}
