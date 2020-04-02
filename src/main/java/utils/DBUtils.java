package utils;

import java.sql.*;

public class DBUtils {
	static String username = null;
	static String password = null;
	static String dbUrl = null;

	public DBUtils(String user, String pwd, String db){
		username = user;
		password = pwd;
		dbUrl = db;
	}

	public Connection DBConnection() throws SQLException, ClassNotFoundException {
		Connection con = DriverManager.getConnection(dbUrl, username,password);
		Class.forName("org.postgresql.Driver");
		return con;
	}

	public ResultSet select(String selectQuery) throws SQLException, ClassNotFoundException {
		//String query = "SELECT * FROM public.segment_details where segment_id_zat=50301;";
		Connection con = DBConnection();
		PreparedStatement ps = con.prepareStatement(selectQuery);
		ResultSet rs = ps.executeQuery();
		con.close();
		return rs;
	}
}
