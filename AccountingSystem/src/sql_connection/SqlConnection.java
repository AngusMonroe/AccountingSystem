package sql_connection;

import java.sql.*;
import java.util.*;

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
	public void insert(Table Table, String[] values) throws SQLException
	{
		int valueNum = 0;
		String stmt;
		PreparedStatement pstmt;
		
		stmt = "INSERT INTO " + Table + " VALUES(";
		
		switch (Table)
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
				stmt += ", ";
		}
		stmt += ")";
		pstmt = connection.prepareStatement(stmt);
		pstmt.execute();
	}
	
	/**
	 * @param Table 表名
	 * @param id ID数组
	 * @throws Exception
	 */
	public void delete(Table Table, ArrayList<Integer> id) throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		
		for (int x : id)
		{
			stmt = "DELETE FROM " + Table + " WHERE ID = " + x;
			pstmt = connection.prepareStatement(stmt);
			pstmt.execute();
		}
	}
	
	/**
	 * @param Table 表名
	 * @param Column 列名
	 * @param value 值
	 * @param id ID数组
	 * @throws Exception
	 */
	public void update(Table Table, Column Column, String value, ArrayList<Integer> id) throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		
		for (int x : id)
		{
			stmt = "UPDATE " + Table + " SET " + Column + " = " + value + " WHERE ID = " + x;
			pstmt = connection.prepareStatement(stmt);
			pstmt.execute();
		}
	}
	
	/**
	 * @param Table 表名
	 * @param Column 列名
	 * @param id ID数组
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> select(Table Table, Column Column, ArrayList<Integer> id) throws SQLException
	{
		ArrayList<String> results = new ArrayList<String>();
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		
		for (int x : id)
		{
			stmt = "SELECT " + Column + " FROM " + Table + " WHERE ID = " + x;
			pstmt = connection.prepareStatement(stmt);
			rs = pstmt.executeQuery();
			results.add(rs.getString(1));
		}
		
		return results;
	}
	
	/**
	 * @param Table 表名
	 * @param Column 列名
	 * @param value 值
	 * @return (列名=值)的ID数组
	 * @throws Exception
	 */
	public ArrayList<Integer> where(Table Table, Column Column, String value) throws SQLException
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		
		stmt = "SELECT ID FROM " + Table + " WHERE " + Column + " = " + value;
		pstmt = connection.prepareStatement(stmt);
		rs = pstmt.executeQuery();
		while(rs.next())
			results.add(rs.getInt(1));
		
		return results;
	}
}
