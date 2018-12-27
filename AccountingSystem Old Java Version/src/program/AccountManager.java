package program;

import java.sql.SQLException;
import java.util.ArrayList;

import account.Account;
import sql_connection.SqlConnection;
import sql_connection.SqlConnection.*;

public class AccountManager
{
	public static Account currUser;
	public static SqlConnection sql;
	
	public static void Login(String name, String pwd) throws SQLException,RuntimeException {
		ArrayList<String> nameOK =  sql.where(Table.User, Column.Name, name);
		ArrayList<String> pwdOK = sql.where(Table.User, Column.Password, pwd);
//		for(String s : nameOK)System.out.println(s);
//		for(String s : pwdOK)System.out.println(s);
		ArrayList<String> findID = intersect(nameOK, pwdOK);
//		for(String s : findID)System.out.println(s);
		if(findID.size() != 1){
			throw new RuntimeException("用户名或密码错误");
		}
		String kind = sql.select(Table.User, Column.Kind, findID.get(0));
		currUser = new Account(Integer.parseInt(kind), findID.get(0));
	}
	
	public static void Logout(){
		currUser = null;
	}
	
	public static ArrayList<String> intersect(ArrayList<String> nameOK, ArrayList<String> pwdOK){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i<nameOK.size();i++){
			for(int j = 0; j<pwdOK.size();j++){
				if(nameOK.get(i).equals(pwdOK.get(i))){
					list.add(nameOK.get(i));
				}
			}
		}
		return list;
	}
	
}
