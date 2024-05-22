package guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import guestbook.vo.GuestBookVo;

public class GuestBookDao {
	private Connection getConnection() throws SQLException {
		Connection conn=null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url="jdbc:mariadb://192.168.0.207:3306/webdb?charset=utf-8"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;
	}
	
	public int insert(GuestBookVo vo) {
	      int result = 0;
	      
	      try (
	    	Connection conn = getConnection();
	        PreparedStatement pstmt1 = conn.prepareStatement("insert into guestbook (name, password, contents, reg_date) values(?, ?, ?, now())");
	        PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
	      ){
	    	 //바인딩
	         pstmt1.setString(1, vo.getName());
	         pstmt1.setString(2, vo.getPassword());
	         pstmt1.setString(3, vo.getContents());
	         result = pstmt1.executeUpdate();
	         
	         ResultSet rs = pstmt2.executeQuery();
	         vo.setNo(rs.next() ? rs.getLong(1) : null);
	         rs.close();
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	      
	      return result;
	   }

	public List<GuestBookVo> findAll() {
		List<GuestBookVo> result = new ArrayList<>();
		
		try (
			Connection conn= getConnection();
			PreparedStatement pstmt= conn.prepareStatement("select no, name, contents, date_format(reg_date, '%Y-%m-%d') from guestbook");
			ResultSet rs= pstmt.executeQuery();
		) { 
		
			while(rs.next()) {
				Long no= rs.getLong(1);
				String name= rs.getString(2);
				String contents= rs.getString(3);
				String regDate=rs.getString(4);
				
				GuestBookVo vo= new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		return result;
	}

	public void deleteByNo(GuestBookVo vo) {

		try (
			Connection conn= getConnection();
			PreparedStatement pstmt= conn.prepareStatement("delete from guestbook where no = ? and password= ?");
		) { 
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			pstmt.executeUpdate();			
		} catch (SQLException e) {
				System.out.println("error:" + e);
		}
	}
}
