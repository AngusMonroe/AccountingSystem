package account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.crypto.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.Balance;
import data.Item;
import data.Transaction;
import program.Program;
import sql_connection.SqlConnection.Column;
import sql_connection.SqlConnection.Table;

public class User {
	
	/** 0: Administrator 
	 *  1: Seller
	 *  2: Buyer
	 *  3: Accountant  
	 */
	private int kind;
	private String userID;
	
	public User(int kind, String userID) {
		this.kind = kind;
		this.userID = userID;
	}
	
	public void sellGoods(String id, String amount) throws SQLException{
		if(kind != 1 && kind != 0){
			throw new RuntimeException("permission denied");
		}
		String preAmount = Program.sql.select(Table.Item, Column.Amount, id);
		int _preAmount = Integer.parseInt(preAmount);
		int _amount = Integer.parseInt(amount);
		if(_preAmount <= _amount){
			throw new RuntimeException("insufficient goods");
		}
		int _newAmount = _preAmount - _amount;
		
		// 更新Item
		Program.sql.update(Table.Item, Column.Amount, ""+_newAmount, id);
		// 更新Trans
		String price = Program.sql.select(Table.Item, Column.Price, id);
		Double totalPrice = _amount * Double.parseDouble(price);
		String[] trans = {nextTranID(), "0", id, amount, ""+totalPrice, userID, new Date().toString()};
		Program.sql.insert(Table.Transaction, trans);
	}
	
	public void buyGoods(String id, String amount) throws SQLException{
		if(kind != 2 && kind != 0){
			throw new RuntimeException("permission denied");
		}
		String preAmount = Program.sql.select(Table.Item, Column.Amount, id);
		int _preAmount = Integer.parseInt(preAmount);
		int _amount = Integer.parseInt(amount);
		int _newAmount = _preAmount + _amount;
		
		// 更新Item
		Program.sql.update(Table.Item, Column.Amount, ""+_newAmount, id);
		// 更新Trans
		String price = Program.sql.select(Table.Item, Column.Price, id);
		Double totalPrice = _amount * Double.parseDouble(price);
		String[] trans = {nextTranID(), "1", id, amount, ""+totalPrice, userID, new Date().toString()};
		Program.sql.insert(Table.Transaction, trans);
	}
	
	public String getUserTable() throws SQLException{
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		
		ArrayList<data.User> users = Program.sql.selectUser();
		int index = 0;
		JSONArray jsArray = new JSONArray();
		for(data.User user : users){
			JSONObject jsObject = user.toJSONObject();
            try {
                jsArray.put(index++, jsObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
		}
		return jsArray.toString();
	}
	
	public String getItemTable(int id) throws SQLException{
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
		
		ArrayList<Item> items = Program.sql.selectItem();
		int index = 0;
		JSONArray jsArray = new JSONArray();
		for(data.Item item : items){
			JSONObject jsObject = item.toJSONObject();
            try {
                jsArray.put(index++, jsObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
		}
		return jsArray.toString();
	}
	
	public String getTransTable(int id) throws SQLException{
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
		ArrayList<Transaction> trans = Program.sql.selectTransaction();
		Transaction ans = null;
		for(Transaction tran : trans) {
			if(tran.id == ""+id){
				ans = tran;
				break;
			}
		}
		if(ans == null) {
			throw new RuntimeException("error id");
		}else{
			return ans.toJSONObject().toString();
		}
	}
	
	public String getBalanceTable() throws SQLException{
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
		Double total = 0.0;
		ArrayList<Transaction> trans = Program.sql.selectTransaction();
		for(Transaction tran : trans) {
			if(tran.time == new Date().toString()){
				if (tran.kind == "0") {
					total -= Double.parseDouble(tran.totalPrice);
				}else {
					total += Double.parseDouble(tran.totalPrice);
				}
			}
		}
		Balance bal = new Balance(nextBalID(), ""+total, new Date().toString());
		return bal.toJSONObject().toString();
	}
	
	public void addUser(String name, String pwd, String userKind) throws SQLException{
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		ArrayList<data.User> users = Program.sql.selectUser();
		String[] values = new String[4];
		values[0] = nextUserID();
		values[1] = name;
		values[2] = pwd;
		values[3] = userKind;
		Program.sql.insert(Table.User, values);
	}
	
	public void deleteUser(String id) throws SQLException{
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		ArrayList<String> ids = new ArrayList<String>(){{
			add(id);
		}};
		Program.sql.delete(Table.User, ids);
	}
	
	private String nextUserID() throws SQLException{
		int max = 1;
		ArrayList<data.User> users = Program.sql.selectUser();
		for(data.User user : users) {
			int id = Integer.parseInt(user.id);
			if(id > max) {
				max = id;
			}
		}
		return ""+max;
	}
	
	private String nextBalID() throws SQLException{
		int max = 1;
		ArrayList<Balance> bals = Program.sql.selectBalance();
		for(Balance bal : bals) {
			int id = Integer.parseInt(bal.id);
			if(id > max) {
				max = id;
			}
		}
		return ""+max;
	}
	
	private String nextItemID() throws SQLException{
		int max = 1;
		ArrayList<Item> items = Program.sql.selectItem();
		for(Item item : items) {
			int id = Integer.parseInt(item.id);
			if(id > max) {
				max = id;
			}
		}
		return ""+max;
	}
	
	private String nextTranID() throws SQLException{
		int max = 1;
		ArrayList<Transaction> trans = Program.sql.selectTransaction();
		for(Transaction tran : trans) {
			int id = Integer.parseInt(tran.id);
			if(id > max) {
				max = id;
			}
		}
		return ""+max;
	}
}