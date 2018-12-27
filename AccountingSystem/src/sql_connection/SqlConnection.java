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
		String stmt;
		PreparedStatement pstmt;
		int valueNum = tableValueNum(table);
		
		stmt = "INSERT INTO " + table + " VALUES('";
		
		for (int i = 0; i < valueNum; i++)
		{
			stmt += values[i];
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
		System.out.println(stmt);
		rs = pstmt.executeQuery();
		
		if(rs.next())
			return rs.getString(1);
		else
			throw new RuntimeException("miss");
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
			System.out.println(stmt);
			rs = pstmt.executeQuery();
			results.add(rs.getString(1));
		}
		return results;
	}
	
	public ArrayList<User> selectUser() throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<User> ret = new ArrayList<>();
		
		stmt = "SELECT * FROM User";
		pstmt = connection.prepareStatement(stmt);
		System.out.println(stmt);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			ret.add(user);
		}
		return ret;
	}
	
	public ArrayList<Item> selectItem() throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<Item> ret = new ArrayList<>();
		
		stmt = "SELECT * FROM Item";
		pstmt = connection.prepareStatement(stmt);
		System.out.println(stmt);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Item item = new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			ret.add(item);
		}
		return ret;
	}
	
	public ArrayList<Transaction> selectTransaction() throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<Transaction> ret = new ArrayList<>();
		
		stmt = "SELECT * FROM Transaction";
		pstmt = connection.prepareStatement(stmt);
		System.out.println(stmt);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Transaction transaction = new Transaction(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
			ret.add(transaction);
		}
		return ret;
	}
	
	public ArrayList<Balance> selectBalance() throws SQLException
	{
		String stmt;
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<Balance> ret = new ArrayList<>();
		
		stmt = "SELECT * FROM Balance";
		pstmt = connection.prepareStatement(stmt);
		System.out.println(stmt);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Balance balance = new Balance(rs.getString(1), rs.getString(2), rs.getString(3));
			ret.add(balance);
		}
		return ret;
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
		System.out.println(stmt);
		rs = pstmt.executeQuery();
		while(rs.next())
			results.add(rs.getString(1));
		
		return results;
	}
	
	private int tableValueNum(Table table)
	{
		switch (table)
		{
		case User: return 4;
		case Item: return 4;
		case Transaction: return 7;
		case Balance: return 3;
		}
		return 0;
	}
	
	/**
	 * 数据库建表
	 */
	private void tableCreate() throws SQLException
	{
		PreparedStatement pstmt;
		String stmt =
				"-- MySQL Script generated by MySQL Workbench\r\n" + 
				"-- Sun Dec 23 22:00:43 2018\r\n" + 
				"-- Model: New Model    Version: 1.0\r\n" + 
				"-- MySQL Workbench Forward Engineering\r\n" + 
				"\r\n" + 
				"SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;\r\n" + 
				"SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;\r\n" + 
				"SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';\r\n" + 
				"\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"-- Schema AccountingSystem\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"-- Schema AccountingSystem\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"CREATE SCHEMA IF NOT EXISTS `AccountingSystem` DEFAULT CHARACTER SET utf8 ;\r\n" + 
				"USE `AccountingSystem` ;\r\n" + 
				"\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"-- Table `AccountingSystem`.`User`\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"CREATE TABLE IF NOT EXISTS `AccountingSystem`.`User` (\r\n" + 
				"  `ID` INT NOT NULL,\r\n" + 
				"  `Name` VARCHAR(255) NOT NULL,\r\n" + 
				"  `Password` VARCHAR(255) NOT NULL,\r\n" + 
				"  `Kind` VARCHAR(255) NOT NULL,\r\n" + 
				"  PRIMARY KEY (`ID`))\r\n" + 
				"ENGINE = InnoDB;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"-- Table `AccountingSystem`.`Item`\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"CREATE TABLE IF NOT EXISTS `AccountingSystem`.`Item` (\r\n" + 
				"  `ID` INT NOT NULL,\r\n" + 
				"  `Name` VARCHAR(255) NOT NULL,\r\n" + 
				"  `Price` DOUBLE NOT NULL,\r\n" + 
				"  `Amount` INT NOT NULL,\r\n" + 
				"  PRIMARY KEY (`ID`))\r\n" + 
				"ENGINE = InnoDB;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"-- Table `AccountingSystem`.`Transaction`\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"CREATE TABLE IF NOT EXISTS `AccountingSystem`.`Transaction` (\r\n" + 
				"  `ID` INT NOT NULL,\r\n" + 
				"  `Kind` VARCHAR(255) NOT NULL,\r\n" + 
				"  `ItemID` INT NOT NULL,\r\n" + 
				"  `Amount` INT NOT NULL,\r\n" + 
				"  `TotalPrice` DOUBLE NOT NULL,\r\n" + 
				"  `UserID` INT NOT NULL,\r\n" + 
				"  `Time` DATETIME NOT NULL,\r\n" + 
				"  PRIMARY KEY (`ID`))\r\n" + 
				"ENGINE = InnoDB;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"-- Table `AccountingSystem`.`Balance`\r\n" + 
				"-- -----------------------------------------------------\r\n" + 
				"CREATE TABLE IF NOT EXISTS `AccountingSystem`.`Balance` (\r\n" + 
				"  `ID` INT NOT NULL,\r\n" + 
				"  `Profit` DOUBLE NOT NULL,\r\n" + 
				"  `Date` DATE NOT NULL,\r\n" + 
				"  PRIMARY KEY (`ID`))\r\n" + 
				"ENGINE = InnoDB;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"SET SQL_MODE=@OLD_SQL_MODE;\r\n" + 
				"SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;\r\n" + 
				"SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;\r\n";

		pstmt = connection.prepareStatement(stmt);
		pstmt.execute();
	}
}
