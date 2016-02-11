package finalproject.data;

import finalproject.models.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class userDB {
	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	public static User getUser(String name)
	{
		User u = new User();
		u.setName("Anonymous");
		return u;
	}
}
