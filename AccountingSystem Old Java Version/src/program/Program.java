package program;

import sql_connection.SqlConnection;
import sql_connection.SqlConnection.Table;

public class Program
{
	public static void main(String[] args)
	{
		try 
		{
			String server = "localhost";
			String port = "3306";
			String database = "AccountingSystem";
			String username = "root";
			String password = "root";
			
			AccountManager.sql = new SqlConnection
					(
						"jdbc:mysql://" + 
						server +  ":" +
						port + "/" + 
						database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8", 
						username, 
						password
					);
//			String[] s1 = {"111","Apple","8.8","10"};
//			String[] s2 = {"222","Banana","9.9","5"};
//			String[] s3 = {"333","Grape","1.1","7"};
//			AccountManager.sql.insert(Table.Item, s1);
//			AccountManager.sql.insert(Table.Item, s2);
//			AccountManager.sql.insert(Table.Item, s3);
//			AccountManager.Login("Zhang", "456");
//			AccountManager.currUser.buyGoods("111", "7");
//			AccountManager.currUser.sellGoods("222", "1");
//			System.out.println(AccountManager.currUser.getItemTable());
//			System.out.println(AccountManager.currUser.getBalanceTable());
//			System.out.println(AccountManager.currUser.getTransTable("1"));
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
