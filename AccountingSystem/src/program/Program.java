package program;

import sql_connection.SqlConnection;
import sql_connection.SqlConnection.*;

public class Program
{
	public static void main(String[] args)
	{
		try 
		{
			String server = "localhost";
			String port = "3306";
			String database = "css";
			String username = "ruangong";
			String password = "ruangong";
			
			SqlConnection sql = new SqlConnection
					(
						"jdbc:mysql://" + 
						server +  ":" +
						port + "/" + 
						database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8", 
						username, 
						password
					);
			
			sql.update(Table.User, Column.Password, "'123456'", sql.where(Table.User, Column.Name, "'Wang'"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
