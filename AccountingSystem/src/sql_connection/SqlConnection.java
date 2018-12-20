package sql_connection;

import java.sql.*;
import java.util.ArrayList;

public class SqlConnection
{
	private Connection connection;

	public enum Table { User, Item, Transaction, Balance }
	public enum Column { ID, Name, Password, Kind, Price, Amount, ItemID, TotalPrice, UserID, Time, Profit, Date }
	
	public SqlConnection(String url, String user, String password) throws Exception
	{
		connection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * @param Table ����
	 * @param values Ԫ��
	 * @throws Exception
	 */
	public void insert(Table Table, String[] values) throws Exception
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
	 * @param Table ����
	 * @param id ID����
	 * @throws Exception
	 */
	public void delete(Table Table, int[] id) throws Exception
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
	 * @param Table ����
	 * @param Column ����
	 * @param value ֵ
	 * @param id ID����
	 * @throws Exception
	 */
	public void update(Table Table, Column Column, String value, int[] id) throws Exception
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
	 * @param Table ����
	 * @param Column ����
	 * @param id ID����
	 * @return
	 * @throws Exception
	 */
	public String[] select(Table Table, Column Column, int[] id) throws Exception
	{
		String[] ret;
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
		ret = new String[results.size()];
		for (int i = 0; i < results.size(); i++)
			ret[i] = results.get(i);
		
		return ret;
	}
	
	/**
	 * @param Table ����
	 * @param Column ����
	 * @param value ֵ
	 * @return (����=ֵ)��ID����
	 * @throws Exception
	 */
	public int[] where(Table Table, Column Column, String value) throws Exception
	{
		int[] ret;
		ArrayList<Integer> results = new ArrayList<Integer>();
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		
		stmt = "SELECT ID FROM " + Table + " WHERE " + Column + " = " + value;
		pstmt = connection.prepareStatement(stmt);
		rs = pstmt.executeQuery();
		while(rs.next())
			results.add(rs.getInt(1));
		ret = new int[results.size()];
		for (int i = 0; i < results.size(); i++)
			ret[i] = results.get(i);
		
		return ret;
	}
}
