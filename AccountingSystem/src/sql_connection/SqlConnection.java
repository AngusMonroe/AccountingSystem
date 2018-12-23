package sql_connection;

import java.sql.*;
import java.util.*;

import javax.management.RuntimeErrorException;

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
	 * @param Table ����
	 * @param values Ԫ��
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
	 * @param Table ����
	 * @param id ID����
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
	 * @param Table ����
	 * @param Column ����
	 * @param value ֵ
	 * @param id ID
	 * @throws Exception
	 */
	public void update(Table Table, Column Column, String value, int id) throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		
		stmt = "UPDATE " + Table + " SET " + Column + " = " + value + " WHERE ID = " + id;
		pstmt = connection.prepareStatement(stmt);
		pstmt.execute();
	}
	
	public String select(Table Table, Column Column, int id) throws SQLException
	{
		ArrayList<Integer> ids = new ArrayList<Integer>(){{
			add(id);
		}};
		ArrayList<String> ans = select(Table, Column, ids);
		if(ans.size() != 1){
			throw new SQLException("�鲻��");
		}else{
			return ans.get(0);
		}
	}
	
	/**
	 * @param Table ����
	 * @param Column ����
	 * @param id ID����
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
	 * @param Table ����
	 * @param Column ����
	 * @param value ֵ
	 * @return (����=ֵ)��ID����
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
