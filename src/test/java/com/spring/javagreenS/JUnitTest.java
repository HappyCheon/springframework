package com.spring.javagreenS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.spring.javagreenS.vo.BoardVO;
import com.spring.javagreenS.vo.MemberVO;

public class JUnitTest {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/javagreen";
		return DriverManager.getConnection(url, "root", "1234");
	}
	
	//@Test
	public void getBoard() {
		BoardVO vo = null;
		try {
			conn = getConnection();
			String sql = "select * from board order by idx desc limit 2";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new BoardVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setNickName(rs.getString("nickName"));
				vo.setTitle(rs.getString("title"));
				vo.setEmail(rs.getString("email"));
				vo.setHomePage(rs.getString("homePage"));
				vo.setContent(rs.getString("content"));
				System.out.println("vo : " + vo);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void idCheck() {
		String mid = "admin";
		MemberVO vo = getIdCheck(mid);
		System.out.println("vo : " + vo);
	}

	private MemberVO getIdCheck(String mid) {
		MemberVO vo = new MemberVO();
		try {
			conn = getConnection();
			String sql = "select * from member where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setName(rs.getString("name"));
				vo.setNickName(rs.getString("nickName"));
				vo.setGender(rs.getString("gender"));
				vo.setBirthday(rs.getString("birthday"));
				vo.setTel(rs.getString("tel"));
				vo.setAddress(rs.getString("address"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
	
}
