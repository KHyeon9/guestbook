package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestbookDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?useSSL=false";
	private static String dbuser = "connectuser";
	private static String dbpassword = "connect123!@#";
	private static String driver = "com.mysql.jdbc.Driver";
	
    public List<Guestbook> getGuestbooks(){
        List<Guestbook> list = new ArrayList<>();

        try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        String sql = "SELECT id, name, content, regdate FROM guestbook";
        
        try (Connection conn = DriverManager.getConnection(dburl, dbuser, dbpassword);
        		PreparedStatement ps = conn.prepareStatement(sql)){
			
        	try (ResultSet rs = ps.executeQuery()){
        		
        		while (rs.next()) {
					Long id = rs.getLong(1);
					String name = rs.getString(2);
					String content = rs.getString(3);
					Date regDate = rs.getDate(4);
					Guestbook guestbook = new Guestbook(id, name, content, regDate);
					list.add(guestbook);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}

        return list;
    }

    public void addGuestbook(Guestbook guestbook){
        Connection conn = null;
        PreparedStatement ps = null;
        
        
        try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(dburl, dbuser, dbpassword);
			
			String sql = "INSERT INTO guestbook (id, name, content, regDate) VALUES (id, ?, ?, now())";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, guestbook.getName());
			ps.setString(2, guestbook.getContent());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
    }
}
