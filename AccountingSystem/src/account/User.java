package account;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

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
	
	public User(int kind) {
		this.kind = kind;
	}
	
	public void sellGoods(int id, int amount) throws SQLException{
		if(kind != 1 && kind != 0){
			throw new RuntimeException("permission denied");
		}
		String amountStr = Program.sql.select(Table.Item, Column.Amount, id);
		int preAmount = Integer.parseInt(amountStr);
		if(preAmount <= amount){
			throw new RuntimeException("insufficient goods");
		}
		int newAmount = preAmount - amount;
		Program.sql.update(Table.Item, Column.Amount, "'" + newAmount + "'", id);
	}
	
	public void buyGoods(int id, int amount) throws SQLException{
		if(kind != 2 && kind != 0){
			throw new RuntimeException("permission denied");
		}
		String amountStr = Program.sql.select(Table.Item, Column.Amount, id);
		int preAmount = Integer.parseInt(amountStr);
		int newAmount = preAmount + amount;
		Program.sql.update(Table.Item, Column.Amount, "'" + newAmount + "'", id);
	}
	
	public void getUserTable(){
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
	}
	
	public void getItemTable(int id){
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
	}
	
	public void getTransTable(int id){
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
	}
	
	public void getBalanceTable(int id){
		if(kind != 0 && kind != 3){
			throw new RuntimeException("permission denied");
		}
	}
	
	public void addUser(String name, String pwd, int kind){
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		//TODO:
	}
	
	public void deleteUser(int id){
		if(kind != 0){
			throw new RuntimeException("permission denied");
		}
		Program.sql.delete(Table.User, id);
	}
}
