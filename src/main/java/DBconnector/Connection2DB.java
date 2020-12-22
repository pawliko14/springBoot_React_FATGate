package DBconnector;

import java.sql.Connection;
import java.sql.DriverManager;


public class Connection2DB {
	Connection conn=null;
	public static Connection dbConnector()
	{
		try {	
			Class.forName("org.mariadb.jdbc.Driver");
		//	Connection conn=DriverManager.getConnection("jdbc:mariadb://192.168.90.123/fatdb","listy","listy1234");
			Connection conn=DriverManager.getConnection("jdbc:mariadb://localhost/fat","root","");

			return conn;
		}catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}
