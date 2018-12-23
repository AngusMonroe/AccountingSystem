package program;

import java.sql.SQLException;
import java.util.ArrayList;

import account.User;
import sql_connection.SqlConnection;
import sql_connection.SqlConnection.*;

public class Program
{
	private static User currUser;
	public static SqlConnection sql;
	
	public static void main(String[] args)
	{
		try 
		{
			String server = "localhost";
			String port = "3306";
			String database = "css";
			String username = "ruangong";
			String password = "ruangong";
			
			sql = new SqlConnection
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
	
	public static void Login(String name, String pwd) throws SQLException,RuntimeException {
		ArrayList<String> nameOK =  sql.where(Table.User, Column.Name, name);
		ArrayList<String> pwdOK = sql.where(Table.User, Column.Password, pwd);
		ArrayList<String> findID = intersect(nameOK, pwdOK);
		if(findID.size() != 0){
			throw new RuntimeException("用户名或密码错误");
		}
		String kind = sql.select(Table.User, Column.Kind, findID.get(0));
		currUser = new User(Integer.parseInt(kind), findID.get(0));
	}
	
	public static void Logout(){
		currUser = null;
	}
	
	public static ArrayList<String> intersect(ArrayList<String> nameOK, ArrayList<String> pwdOK){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i<nameOK.size();i++){
			for(int j = 0; j<pwdOK.size();j++){
				if(nameOK.get(i) == pwdOK.get(i)){
					list.add(nameOK.get(i));
				}
			}
		}
		return list;
	}
	
}
