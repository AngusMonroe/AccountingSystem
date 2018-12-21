package program;

import javax.lang.model.type.IntersectionType;

import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import account.User;
import sql_connection.SqlConnection;
import sql_connection.SqlConnection.*;

public class Program
{
	private static User currUser;
	private static SqlConnection sql;
	
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
	
	public static void Login(String name, String pwd) throws Exception {
		int[] nameOK =  sql.where(Table.User, Column.Name, "'" + name + "'");
		int[] pwdOK = sql.where(Table.User, Column.Password, "'" + pwd + "'");
		int[] findAns = intersect(nameOK, pwdOK);
		String[] kind = sql.select(Table.User, Column.Kind, findAns);
		if(kind.length == 0){
			throw new RuntimeException("用户名或密码错误");
		}else{
			int k = Integer.parseInt(kind[0]);
			currUser = new User(k);
		} 
	}
	
	public static void Logout(){
		currUser = null;
	}
	
	public static int[] intersect(int[] a, int[] b){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i<a.length;i++){
			for(int j = 0; j<b.length;j++){
				if(a[i] == b[j]){
					list.add(a[i]);
				}
			}
		}
		
		int[] ans = new int[list.size()];
		for(int i =0;i<ans.length;i++){
			ans[i] = list.get(i);
		}
		return ans;
	}
	
}
