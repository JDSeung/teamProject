package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.db.DBConnection;

public class QnaDBBean {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = null;
	private int result;
	
	private static QnaDBBean instance = new QnaDBBean();
	
	//.jsp페이지에서 DB연동빈인 BoardDBBean 클래스의 메소드에서 접근 시 필요
	public static QnaDBBean getInstance(){
		return instance;
	}
	private QnaDBBean() {}
	
	//qna 테이블에 글을 추가 - 사용자가 작성하는 글
	public int insertArticle(QnaDataBean article){
		result =0;
		int group_id =1;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT MAX(QNA_ID) FROM QNA ");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
			
			if(result > 0){
				group_id = rs.getInt(1) +1;
			}
			
			//쿼리를 작성 : board 테이블에 새로운 레코드 추가
			sql = new StringBuffer();
			sql.append("INSERT INTO QNA(QNA_ID, BOOK_ID, BOOK_TITLE, QNA_WRITER, QNA_CONTENT, ");
			sql.append("			    GROUP_ID, QORA, REPLY, REG_DATE) ");
			sql.append("VALUES(QNA_SEQ.NEXTVAL,?,?,?,?,?,?,?,?) ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, article.getBook_id());
			pstmt.setString(2, article.getBook_title());
			pstmt.setString(3, article.getQna_writer());
			pstmt.setString(4, article.getQna_content());
			pstmt.setInt(5, group_id);
			pstmt.setInt(6, article.getQora());
			pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getReg_date());
			pstmt.executeUpdate();
			result = 1;
		}catch (Exception e) {
			System.out.print("QnaDBBean insertArticle 사용자 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	//qna 테이블에 글을 추가 - 관리자가 작성한 답변
	public int insertArticle(QnaDataBean article, int qna_id){
		result =0;
		try{
			conn = DBConnection.getConnection();
			//쿼리를 작성 : board 테이블에 새로운 레코드 추가
			sql = new StringBuffer();
			sql.append("INSERT INTO QNA(QNA_ID, BOOK_ID, BOOK_TITLE, QNA_WRITER, QNA_CONTENT, ");
			sql.append("			    GROUP_ID, QORA, REPLY, REG_DATE) ");
			sql.append("VALUES(QNA_SEQ.NEXTVAL,?,?,?,?,?,?,?,?) ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, article.getBook_id());
			pstmt.setString(2, article.getBook_title());
			pstmt.setString(3, article.getQna_writer());
			pstmt.setString(4, article.getQna_content());
			pstmt.setInt(5, article.getGroup_id());
			pstmt.setInt(6, article.getQora());
			pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getReg_date());
			pstmt.executeUpdate();
			
			sql = new StringBuffer();
			sql.append("UPDATE QNA SET REPLY = ? WHERE QNA_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, 1);
			pstmt.setInt(2, qna_id);
			pstmt.executeUpdate();
			
			result = 1;
		}catch (Exception e) {
			System.out.print("QnaDBBean insertArticle 관리자 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	//qna 테이블에 저장된 전체 글의 수를 얻어냄
	public int getArticleCount(){
		result = 0;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM QNA");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.print("QnaDBBean getArticleCount 전체 QNA 수량 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	
	//특정 책에 대해 작성한 qna 글의 수를 얻어냄
	public int getArticleCount(int book_id){
		result = 0;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM QNA WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, book_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.print("QnaDBBean getArticleCount 특정 QNA 수량 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn); 
		}
		return result;
	}
	
	//지정한 수의 해당하는 qna 글의 수를 얻어냄
	public List<QnaDataBean> getArticles(int count){
		List<QnaDataBean> articleList = null;//글 목록을 저장하는 객체
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT * FROM QNA ORDER BY GROUP_ID DESC, QORA ASC");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){//ResultSet이 레코드를 가짐
				articleList = new ArrayList<QnaDataBean>(count);
				do{
					QnaDataBean article = new QnaDataBean();
					article.setQna_id(rs.getInt("qna_id"));
					article.setBook_id(rs.getInt("book_id"));
					article.setBook_title(rs.getString("book_title"));
					article.setQna_writer(rs.getString("qna_writer"));
					article.setQna_content(rs.getString("qna_content"));
					article.setGroup_id(rs.getInt("group_id"));
					article.setQora(rs.getByte("qora"));
					article.setReply(rs.getByte("reply"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					
					//List 객체에 데이터 저장빈인 BoardDataBean 객체를 저장
					articleList.add(article);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.print("QnaDBBean getArticleCount 지정한 수의  QNA 글의 수 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return articleList;
	}
	//특정 책에 대한 작성한 qna 글을 지정한 수만큼 얻어 냄
	public List<QnaDataBean> getArticles(int count, int book_id){
		List<QnaDataBean> articleList = null;//글 목록을 저장하는 객체
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT * FROM QNA "); 
			sql.append("WHERE BOOK_ID = ? "); 
			sql.append("ORDER BY GROUP_ID DESC, QORA ASC ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, book_id);
			rs = pstmt.executeQuery();
			if(rs.next()){//ResultSet이 레코드를 가짐
				articleList = new ArrayList<QnaDataBean>(count);
				do{
					QnaDataBean article = new QnaDataBean();
					article.setQna_id(rs.getInt("qna_id"));
					article.setBook_id(rs.getInt("book_id"));
					article.setBook_title(rs.getString("book_title"));
					article.setQna_writer(rs.getString("qna_writer"));
					article.setQna_content(rs.getString("qna_content"));
					article.setGroup_id(rs.getInt("group_id"));
					article.setQora(rs.getByte("qora"));
					article.setReply(rs.getByte("reply"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					
					//List 객체에 데이터 저장빈인 BoardDataBean 객체를 저장
					articleList.add(article);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.print("QnaDBBean getArticleCount 특정 QNA 지정한 글의 수 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return articleList;
	}
	
	//QNA 글 수정 폼에서 사용한 글의 내용
	public QnaDataBean updateGetArticle(int qna_id){
		QnaDataBean article = null;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT * FROM QNA WHERE QNA_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, qna_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				article = new QnaDataBean();
				article.setQna_id(rs.getInt("qna_id"));
				article.setBook_id(rs.getInt("book_id"));
				article.setBook_title(rs.getString("book_title"));
				article.setQna_writer(rs.getString("qna_writer"));
				article.setQna_content(rs.getString("qna_content"));
				article.setGroup_id(rs.getInt("group_id"));
				article.setQora(rs.getByte("qora"));
				article.setReply(rs.getByte("reply"));
				article.setReg_date(rs.getTimestamp("reg_date"));
			}
		}catch (Exception e) {
			System.out.print("QnaDBBean updateGetArticle 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return article;
	}
	
	//QnA 글수정 처리에서 사용
	public int updateArticle(QnaDataBean article){
		result = -1;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("UPDATE QNA SET QNA_CONTENT = ? WHERE QNA_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, article.getQna_content());
			pstmt.setInt(2, article.getQna_id());
			pstmt.executeUpdate();
			result = 1;
			
		}catch (Exception e) {
			System.out.print("QnaDBBean updateArticle 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	
	//QnA 글 수정 삭제 처리 시 사용
	public int deleteArticle(int qna_id){
		result = -1;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("DELETE FROM QNA WHERE QNA_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, qna_id);
			pstmt.executeUpdate();
			result = 1;//글삭제 성공
		}catch (Exception e) {
			System.out.print("QnaDBBean deleteArticle 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
}
