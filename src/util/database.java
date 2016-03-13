package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class database {
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/test?user=root&password=suenwuko&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			System.out.println("连接数据库时出现错误");
		}
		return conn;
	}
	
	public static void insertImg(String imgName,String ClothesId,String imgPath) {
		    PreparedStatement pstmt = null;
			Connection sqlConnection = database.getConnection();
			try {	
				String sql = "insert into student (imgName,ClothesId,imgPath) values (?,?,?)";
				pstmt = sqlConnection.prepareStatement(sql);
				pstmt.setString(1, imgName);
				pstmt.setString(2, ClothesId);
			    pstmt.setString(3, imgPath);
			    pstmt.executeUpdate();
			}catch(SQLException e) {
	            e.printStackTrace();
	        }finally{
	  		    	try {
	  					pstmt.close();
	  	  				sqlConnection.close();
	  				} catch (Exception e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	  		    }
	}
	
	public static void insertFile(String ClothesId,String shop,String filePath) {
	    PreparedStatement pstmt = null;
		Connection sqlConnection = database.getConnection();
		try {	
			String sql = "insert into student2 (ClothesId,shop,filePath) values (?,?,?)";
			pstmt = sqlConnection.prepareStatement(sql);
			pstmt.setString(1, ClothesId);
			pstmt.setString(2, shop);
		    pstmt.setString(3, filePath);
		    pstmt.executeUpdate();
		}catch(SQLException e) {
            e.printStackTrace();
        }finally{
  		    	try {
  					pstmt.close();
  	  				sqlConnection.close();
  				} catch (Exception e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  		    }
		
	}

}
