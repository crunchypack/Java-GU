package BetacriticEasyDatabase;

import java.util.ArrayList;

//calle
public class User {
	private String username;
	private String password;
	private String email;
	private String userID;
	
	private boolean loggedIn;
	
	public User(String userID, DatabaseManager manager)
	{
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT * FROM user WHERE UserID='"+userID+"'"));
		ArrayList<String> list = arrList.get(0);
		this.username = list.get(manager.getIndexOfCollumn("user", "Username"));
		this.password = list.get(manager.getIndexOfCollumn("user", "Password"));
		this.email = list.get(manager.getIndexOfCollumn("user", "Email"));
		this.userID = userID;
	}
	
	public boolean login(String username, String password)
	{
		if (username.equals(this.username)  && password.equals(this.password)) {
			loggedIn = true;
			return true;
		}
		else {
			return false;
		}

		
	}
	public void logout()
	{
		loggedIn = false;
	}
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public String getEmail(){
		return email;
	}
	public String getUsername(){
		return username;
	}
	
	public int getUserID()
	{
		return Integer.parseInt(userID);
	}
	
	public static User getUserByUserName(String username, DatabaseManager manager)
	{
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT * FROM user WHERE Username='"+username+"'"));
		if (arrList.size() != 0) {
			ArrayList<String> list = arrList.get(0);
			return new User(list.get(manager.getIndexOfCollumn("user", "UserID")), manager);
		}
		
		return null;
		
		
	}
	
	
}
