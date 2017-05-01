package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.crypt.BCrypt;
import com.crypt.SHA256;
import com.db.DBConnection;

public class MngrDBBean {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = null;
	private int result;
	
	//MngrDBBean 전역 객체 생성 <- 한개의 객체만 생성해서 공유
	private static MngrDBBean instance = new MngrDBBean();
	//MngrDBBean 객체를 리턴하는 메소드
	public static MngrDBBean getInstance(){
		return instance;
	}
	
	private MngrDBBean(){}
	
	//관리자 인증 메소드
	public int userCheck(String id, String passwd){
		result = -1;
		SHA256 sha = SHA256.getInsatnce();
		try{
			conn = DBConnection.getConnection();
			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());
			sql = new StringBuffer();
			sql.append("SELECT MANAGERPASSWD FROM MANAGER WHERE MANAGERID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				//해당 아이디가 있으면 수행
				String dbpasswd = rs.getString("managerPasswd");
				if(BCrypt.checkpw(shaPass, dbpasswd)){
					result = 1;//인증성공
				}else{//해당 아이디 없으면 수행
					result = 0;//비밀번호 틀림
				}
			}else{//해당 아이디가 없으면 수행
				result = -1; //아이디 없음
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean userCheck 에러 : ");
			e.printStackTrace();
		}finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		System.out.println(result);
		return result;
	}
	//책 등록 메소드
	public void insertBook(MngrDataBean book){
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("INSERT INTO BOOK(BOOK_ID, BOOK_KIND, BOOK_TITLE, BOOK_PRICE, ");
			sql.append("BOOK_COUNT, AUTHOR, PUBLISHING_COM, PUBLISHING_DATE, BOOK_IMAGE, ");
			sql.append("BOOK_CONTENT, DISCOUNT_RATE, REG_DATE) ");
			sql.append("VALUES(BOOK_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setInt(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setByte(10, book.getDiscount_rate());
			pstmt.setTimestamp(11, book.getReg_date());
			pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.print("MngrDBBean userCheck 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
	}
	//이미 등록된 책을 검증
	public int registedBookconFirm(String kind, String bookTitle, String author){
		result = -1;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT BOOK_TITLE ");
			sql.append("FROM BOOK ");
			sql.append("WHERE BOOK_KIND = ? AND BOOK_TITLE = ? AND AUTHOR = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, kind);
			pstmt.setString(2, bookTitle);
			pstmt.setString(3, author);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = 1; //해당 책이 이미 등록되어 있음
			}else{
				result = -1; //해당 책이 등록되어 있지 않음
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean registedBookconFirm 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	
	//전체 등록 된 책의 수를 얻어내는 메소드
	public int getBookCount(){
		result = 0;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM BOOK");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean getBookCount 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		
		return result;
	}
	
	//해당 분류의 책의 수를 얻어내는 메소드
	public int getBookCount(String book_kind){
		result = 0;
		int kind = Integer.parseInt(book_kind);
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM BOOK WHERE BOOK_KIND= ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, kind);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean getBookCount 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	
	//책의 제목을 얻어냄
	public String getBookTitle(int book_id){
		String result = "";
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT BOOK_TITLE FROM BOOK WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, book_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean getBookTitle 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return result;
	}
	
	//분류별 또는 전체 등록된 책의 정보를 얻어내는 메소드
	public List<MngrDataBean> getbooks(String book_kind){
		List<MngrDataBean> bookList = null;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT * FROM BOOK ");
			if(book_kind != null){
				if(!(book_kind.equals("all") || book_kind.equals(""))){
					sql.append("WHERE BOOK_KIND = ? ORDER BY REG_DATE DESC ");
					pstmt.setString(1, book_kind);
				}
			}
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				bookList = new ArrayList<MngrDataBean>();
				do{
					MngrDataBean book = new MngrDataBean();
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));
					
					bookList.add(book);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean getbooks 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return bookList;
	}
	
	//쇼핑몰 메인에 표시하기 위해서 사용하는 분류별 신간 목록을 얻어내는 메소드
	public MngrDataBean[] getBooks(String book_kind, int count){
		MngrDataBean[] bookList = null;
		int i =0;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT * ");
			sql.append("FROM ( SELECT BOOK.*, ROWNUM RNUM ");
			sql.append("	   FROM (SELECT * ");
			sql.append("			 FROM BOOK ");
			sql.append("			 WHERE BOOK_KIND = ? ");
			sql.append("			 ORDER BY REG_DATE)BOOK ");
			sql.append("	 ) ");
			sql.append("WHERE RNUM BETWEEN ? AND ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, book_kind);
			pstmt.setInt(2, 0);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			if(rs.next()){
				bookList = new MngrDataBean[count];
				do {
					MngrDataBean book = new MngrDataBean();
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setBook_image(rs.getString("book_image"));
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));
					
					bookList[i] = book;
					i++;
				} while (rs.next());
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean getBooks 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return bookList;
	}
	
	//bookId에 해당하는 책의 정보를 얻어내는 메소드로
	//등록된 책을 수정하기 위해 수정 폼으로 읽어들이기 위한 메소드
	public MngrDataBean getBook(int bookId){
		MngrDataBean book = null;
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("SELECT * FROM BOOK WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();
			if(rs.next()){
				book = new MngrDataBean();
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_kind(rs.getString("book_kind"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_content(rs.getString("book_content"));
				book.setBook_count(rs.getShort("book_count"));
				book.setAuthor(rs.getString("author"));
				book.setPublishing_com(rs.getString("publishing_com"));
				book.setPublishing_date(rs.getString("publishing_date"));
				book.setBook_image(rs.getString("book_image"));
				book.setDiscount_rate(rs.getByte("discount_rate"));
				book.setReg_date(rs.getTimestamp("reg_date"));
			}
		}catch (Exception e) {
			System.out.print("MngrDBBean getBook 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return book;
	}
	
	//등록된 책의 정보를 수정 시 사용하는 메소드
	public void updateBook(MngrDataBean book, int bookId){
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("UPDATE BOOK SET BOOK_KIND = ?, BOOK_TITLE=?, BOOK_PRICE=?, ");
			sql.append("BOOK_COUNT = ?, AUTHOR = ?, PUBLISHING_COM = ?, PUBLISHING_DATE =?, ");
			sql.append("BOOK_IMAGE = ?, BOOK_CONTENT = ?, DISCOUNT_RATE = ?");
			sql.append("WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setInt(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setByte(10, book.getDiscount_rate());
			pstmt.setInt(11, bookId);
			pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.print("MngrDBBean updateBook 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
	}
	
	//bookId에 해당하는 책의 정보를 삭제 시 사용하는 메소드
	public void deleteBook(int bookId){
		try{
			conn = DBConnection.getConnection();
			sql = new StringBuffer();
			sql.append("DELETE FROM BOOK WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.print("MngrDBBean deleteBook 에러 : ");
			e.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
	}
}

      
      
      
