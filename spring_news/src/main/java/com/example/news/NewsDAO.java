package com.example.news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class NewsDAO {
	//DB 연결 선언
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/volpedb";

	//DB 연결 가져오는 메서드
	public Connection open()	{
		Connection conn = null;

		try	{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL,"volperyu","1234");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}

	//뉴스 기사 목록 전체 가져오는 메서드
	public List<News> getAll() throws Exception	{
		Connection conn = open();
		List<News>	newsList = new ArrayList<>();

		String sql = "select aid, title, SUBSTRING(date from 1 for 19) as cdate from news";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		// try-with-resource
		try(conn; pstmt; rs)	{
			while(rs.next())	{
				News n = new News();
				n.setAid(rs.getInt("aid"));
				n.setTitle(rs.getString("title"));
				n.setDate(rs.getString("cdate"));

				newsList.add(n);
			}
			return newsList;
		}
	}
	//뉴스 선택시 세부내용 보여주기 위한 메서드
	public News getNews(int aid) throws SQLException {
		Connection conn = open();

		News n = new News();
		String sql = "select aid, title, img, SUBSTRING(date from 1 for 19) as cdate, content from news where aid = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		ResultSet rs = pstmt.executeQuery();

		rs.next();

		try(conn; pstmt; rs)	{
			n.setAid(rs.getInt("aid"));
			n.setTitle(rs.getString("title"));
			n.setImg(rs.getString("img"));
			n.setDate(rs.getString("cdate"));
			n.setContent(rs.getString("content"));
			pstmt.executeQuery();
			return n;
		}
	}
	
	//뉴스 추가 메서드
		public void addNews(News n) throws Exception	{
			Connection conn = open();

			String sql = "insert into news(title, img, date, content) values(?,?,CURRENT_TIMESTAMP(),?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);

			try(conn; pstmt)	{
				pstmt.setString(1, n.getTitle());
				pstmt.setString(2, n.getImg());
				pstmt.setString(3, n.getContent());
				pstmt.executeUpdate();

			}
		}
		
	//뉴스 삭제 메서드
	public void delNews(int aid) throws SQLException	{
		Connection conn = open();

		String sql = "delete from news where aid = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try(conn; pstmt)	{
			pstmt.setInt(1, aid);
			if(pstmt.executeUpdate()==0)	{
				throw new SQLException("DB에러");
			}
		}
	}
}
