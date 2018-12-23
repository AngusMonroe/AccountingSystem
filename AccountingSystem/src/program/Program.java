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
		ArrayList<Integer> nameOK =  sql.where(Table.User, Column.Name, "'" + name + "'");
		ArrayList<Integer> pwdOK = sql.where(Table.User, Column.Password, "'" + pwd + "'");
		ArrayList<Integer> findAns = intersect(nameOK, pwdOK);
		ArrayList<String> kind = sql.select(Table.User, Column.Kind, findAns);
		if(kind.size() == 0){
			throw new RuntimeException("用户名或密码错误");
		}else{
			int k = Integer.parseInt(kind.get(0));
			currUser = new User(k);
		} 
	}
	
	public static void Logout(){
		currUser = null;
	}
	
	public static ArrayList<Integer> intersect(ArrayList<Integer> nameOK, ArrayList<Integer> pwdOK){
		ArrayList<Integer> list = new ArrayList<Integer>();
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
