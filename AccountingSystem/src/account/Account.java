package account;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.Balance;
import data.Item;
import data.Transaction;
import data.User;
import program.AccountManager;
import sql_connection.SqlConnection.Column;
import sql_connection.SqlConnection.Table;

public class Account {
	
	/** 0: Administrator 
	 *  1: Seller
	 *  2: Buyer
	 *  3: Accountant  
	 */
	private int kind;
	private String userID;
	
	public Account(int kind, String userID) {
		this.kind = kind;
		this.userID = userID;
	}
	
	public String getItem(String id) throws SQLException{
		String name = AccountManager.sql.select(Table.Item, Column.Name, id);
		String price = AccountManager.sql.select(Table.Item, Column.Price, id);
		String amount = AccountManager.sql.select(Table.Item, Column.Amount, id);
		Item item = new Item(id, name, price, amount);
		return item.toJSONObject().toString();
	}
	
	public void sellGoods(String id, String amount) throws SQLException{
		if(kind != 1 && kind != 0){
			throw new RuntimeException("permission denied");
		}
		String preAmount = AccountManager.sql.select(Table.Item, Column.Amount, id);
		int _preAmount = Integer.parseInt(preAmount);
		int _amount = Integer.parseInt(amount);
		if(_preAmount <= _amount){
			throw new RuntimeException("insufficient goods");
		}
		int _newAmount = _preAmount - _amount;
		
		// 更新Item
		AccountManager.sql.update(Table.Item, Column.Amount, ""+_newAmount, id);
		// 更新Trans
		String price = AccountManager.sql.select(Table.Item, Column.Price, id);
		Double totalPrice = _amount * Double.parseDouble(price);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		String date = sdf.format(new Date());
		String[] trans = {nextTranID(), "0", id, amount, ""+totalPrice, userID, date};
		AccountManager.sql.insert(Table.Transaction, trans);
	}
	
	public void buyGoods(String id, String amount) throws SQLException{
		if(kind != 2 && kind != 0){
			throw new RuntimeException("permission denied");
		}
		String preAmount = AccountManager.sql.select(Table.Item, Column.Amount, id);
		int _preAmount = Integer.parseInt(preAmount);
		int _amount = Integer.parseInt(amount);
		int _newAmount = _preAmount + _amount;
		
		// 更新Item
		AccountManager.sql.update(Table.Item, Column.Amount, ""+_newAmount, id);
		// 更新Trans
		String price = AccountManager.sql.select(Table.Item, Column.Price, id);
		Double totalPrice = _amount * Double.parseDouble(price);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		String date = sdf.format(new Date());
		String[] trans = {nextTranID(), "1", id, amount, ""+totalPrice, userID, date};
		AccountManager.sql.insert(Table.Transaction, trans);
	}
	
	public void addItem(String name, String price) throws RuntimeException{
		if(kind != 0 && kind != 2){
			throw new RuntimeException("permission denied");
		}
		try {
			String id = nextItemID(); 
			String amount = "0";
			String[] values = {id, name, price, amount};
			AccountManager.sql.insert(Table.Item, values);
		} catch (Exception e) {
			throw new RuntimeException("insert item fault");
		}
	}
	
	public String getItemList() throws SQLException{		
		ArrayList<Item> items = AccountManager.sql.selectItem();
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
	
	public String  getItemInfo(String id){
		//TODO:
//		String item = getItem(id);
//		AccountManager.sql.select(Table.Transaction, column, 
//				AccountManager.sql.where(table, column, value))
	}
	
	public String getBalance() throws SQLException{
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
		Double total = 0.0;
		ArrayList<Transaction> trans = AccountManager.sql.selectTransaction();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		String date = sdf.format(new Date());
		for(Transaction tran : trans) {
			if(tran.time.equals(date)){
				if (tran.kind.equals("0")) {
					total -= Double.parseDouble(tran.totalPrice);
				}else {
					total += Double.parseDouble(tran.totalPrice);
				}
			}
		}
		Balance bal = new Balance(nextBalID(), ""+total, date);
		return bal.toJSONObject().toString();
	}
	
	public String getUserList() throws SQLException{
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		
		ArrayList<User> users = AccountManager.sql.selectUser();
		int index = 0;
		JSONArray jsArray = new JSONArray();
		for(User user : users){
			JSONObject jsObject = user.toJSONObject();
            try {
                jsArray.put(index++, jsObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
		}
		return jsArray.toString();
	}
	
	public void removeUser(String id) throws SQLException{
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		ArrayList<String> ids = new ArrayList<String>(){{
			add(id);
		}};
		AccountManager.sql.delete(Table.User, ids);
	}
	
	public void addUser(String name, String pwd, String userKind) throws SQLException{
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		ArrayList<User> users = AccountManager.sql.selectUser();
		String[] values = new String[4];
		values[0] = nextUserID();
		values[1] = name;
		values[2] = pwd;
		values[3] = userKind;
		AccountManager.sql.insert(Table.User, values);
	}
	
	private String nextUserID() throws SQLException{
		int max = 1;
		ArrayList<User> users = AccountManager.sql.selectUser();
		for(User user : users) {
			int id = Integer.parseInt(user.id);
			if(id > max) {
				max = id;
			}
		}
		max++;
		return ""+max;
	}
	
	private String nextBalID() throws SQLException{
		int max = 1;
		ArrayList<Balance> bals = AccountManager.sql.selectBalance();
		for(Balance bal : bals) {
			int id = Integer.parseInt(bal.id);
			if(id > max) {
				max = id;
			}
		}
		max++;
		return ""+max;
	}
	
	private String nextItemID() throws SQLException{
		int max = 1;
		ArrayList<Item> items = AccountManager.sql.selectItem();
		for(Item item : items) {
			int id = Integer.parseInt(item.id);
			if(id > max) {
				max = id;
			}
		}
		max++;
		return ""+max;
	}
	
	private String nextTranID() throws SQLException{
		int max = 1;
		ArrayList<Transaction> trans = AccountManager.sql.selectTransaction();
		for(Transaction tran : trans) {
			int id = Integer.parseInt(tran.id);
			if(id > max) {
				max = id;
			}
		}
		max++;
		return ""+max;
	}
}