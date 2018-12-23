package sql_connection;

import java.sql.*;
import java.util.*;

import data.*;

public class SqlConnection
{
	private Connection connection;

	public enum Table { User, Item, Transaction, Balance }
	public enum Column { ID, Name, Password, Kind, Price, Amount, ItemID, TotalPrice, UserID, Time, Profit, Date }
	
	public SqlConnection(String url, String user, String password) throws SQLException
	{
		connection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * @param Table 表名
	 * @param values 元素
	 * @throws Exception
	 */
	public void insert(Table table, String[] values) throws SQLException
	{
		int valueNum = 0;
		String stmt;
		PreparedStatement pstmt;
		
		stmt = "INSERT INTO " + table + " VALUES('";
		
		switch (table)
		{
		case User: valueNum = 4; break;
		case Item: valueNum = 4; break;
		case Transaction: valueNum = 7; break;
		case Balance: valueNum = 3; break;
		}
		
		for (int i = 0; i < valueNum; i++)
		{
			stmt += values;
			if(i < values.length - 1)
				stmt += "', '";
		}
		stmt += "')";
		pstmt = connection.prepareStatement(stmt);
		pstmt.execute();
	}
	
	/**
	 * @param Table 表名
	 * @param id ID数组
	 * @throws Exception
	 */
	public void delete(Table table, ArrayList<String> id) throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		
		for (String x : id)
		{
			stmt = "DELETE FROM " + table + " WHERE ID = '" + x + "'";
			pstmt = connection.prepareStatement(stmt);
			pstmt.execute();
		}
	}
	
	/**
	 * @param Table 表名
	 * @param column 列名
	 * @param value 值
	 * @param id ID
	 * @throws Exception
	 */
	public void update(Table table, Column column, String value, String id) throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		
		stmt = "UPDATE " + table + " SET " + column + " = " + value + " WHERE ID = '" + id + "'";
		pstmt = connection.prepareStatement(stmt);
		pstmt.execute();
	}
	
	/**
	 * @param Table 表名
	 * @param column 列名
	 * @param id ID数组
	 * @return
	 * @throws Exception
	 */
	public String select(Table table, Column column, String id) throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		
		stmt = "SELECT " + column + " FROM " + table + " WHERE ID = '" + id + "'";
		pstmt = connection.prepareStatement(stmt);
		rs = pstmt.executeQuery();
	
		return rs.getString(1);
	}
	
	/**
	 * @param Table 表名
	 * @param column 列名
	 * @param id ID数组
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> select(Table table, Column column, ArrayList<String> id) throws SQLException
	{
		ArrayList<String> results = new ArrayList<String>();
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		
		for (String x : id)
		{
			stmt = "SELECT " + column + " FROM " + table + " WHERE ID = '" + x + "'";
			pstmt = connection.prepareStatement(stmt);
			rs = pstmt.executeQuery();
			results.add(rs.getString(1));
		}
		return results;
	}
	
	public ArrayList<User> selectUser()
	{
		return null;
	}
	
	public ArrayList<Item> selectItem()
	{
		return null;
	}
	
	public ArrayList<Transaction> selectTransaction()
	{
		return null;
	}
	
	public ArrayList<Balance> selectBalance()
	{
		return null;
	}
	
	/**
	 * @param table 表名
	 * @param column 列名
	 * @param value 值
	 * @return (列名=值)的ID数组
	 * @throws Exception
	 */
	public ArrayList<String> where(Table table, Column column, String value) throws SQLException
	{
		ArrayList<String> results = new ArrayList<String>();
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		
		stmt = "SELECT ID FROM " + table + " WHERE " + column + " = '" + value + "'";
		pstmt = connection.prepareStatement(stmt);
		rs = pstmt.executeQuery();
		while(rs.next())
			results.add(rs.getString(1));
		
		return results;
	}
}
