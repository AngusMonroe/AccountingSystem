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
		String amountStr = Program.sql.select(Table.Item, Column.Amount, id);
		int preAmount = Integer.parseInt(amountStr);
		if(preAmount <= amount){
			throw new RuntimeException("»õÎï²»×ã");
		}
		int newAmount = preAmount - amount;
		Program.sql.update(Table.Item, Column.Amount, "'" + newAmount + "'", id);
	}
	
	public void buyGoods(int id, int amount) throws SQLException{
		sellGoods(id, -amount);
	}
	
	public void getUserTable(){
		
	}
	
	public void getItemTable(){
		
	}
	
	public void getTransTable(){
		
	}
	
	public void getBalanceTable(){
		
	}
}
