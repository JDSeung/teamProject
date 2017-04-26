package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.db.DBConnection;

public class CartDBBean {
	private static CartDBBean instance = new CartDBBean();
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sql = null;
	private ResultSet rs = null;
	public static CartDBBean getInstance(){
		return instance;
	}
	
	private CartDBBean(){
		
	}
	// [장바구니에 담기]를 클릭하면 수행되는 것으로 cart 테이블에 새로운 레코드를 추가
	public void insertCart(CartDataBean cart) throws Exception{
		sql = new StringBuffer();
		
		try{
			conn = DBConnection.getConnection();
			sql.append("insert into cart(book_id, buyer, book_title, buy_price, buy_count, book_image "+
			"values(?, ?, ?, ?, ?, ?)");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, cart.getBook_id());
			pstmt.setString(2, cart.getBuyer());
			pstmt.setString(3, cart.getBook_title());
			pstmt.setInt(4, cart.getBuy_price());
			pstmt.setByte(5, cart.getBuy_count());
			pstmt.setString(6, cart.getBook_image());
			
			pstmt.executeUpdate();
		}catch(Exception ex){
			System.out.print("CartDBBean insertCart 에러 : ");
			ex.printStackTrace();
		}finally{
			DBConnection.disConnect(null, pstmt, conn);
		}
	}
	
	// id에 해당하는 레코드의 수를 얻어내는 메소드
	public int getListCount(String id) throws Exception{
		int x = 0;
		
		try{
			conn = DBConnection.getConnection();
			
			pstmt = conn.prepareStatement("select count(*) from cart where buyer=? ");
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				x = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.print("CartDBBean getListCount 에러 : ");
			ex.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}
	
	// id에 해당하는 레코드의 목록을 얻어내는 메소드
	public List<CartDataBean> getCart(String id, int count) throws Exception{
		CartDataBean cart = null;
		List<CartDataBean> lists = null;
		
		try{
			conn = DBConnection.getConnection();
			
			sql.append("select * from cart where buyer=? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			lists = new ArrayList<CartDataBean>(count);
			
			while(rs.next()){
				cart = new CartDataBean();
				
				cart.setCart_id(rs.getInt("cart_id"));
				cart.setBook_id(rs.getInt("book_id"));
				cart.setBook_title(rs.getString("book_title"));
				cart.setBuy_price(rs.getInt("buy_price"));
				cart.setBuy_count(rs.getByte("buy_count"));
				cart.setBook_image(rs.getString("book_image"));
				
				lists.add(cart);
			}
		}catch(Exception ex){
			System.out.print("CartDBBean getCart 에러 : ");
			ex.printStackTrace();
		}finally{
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return lists;
	}
	
	// 장바구니에서 수량 수정 시 실행되는 메소드
	public void updateCount(int cart_id, byte count) throws Exception{
		try{
			conn = DBConnection.getConnection();
			
			pstmt = conn.prepareStatement("update cart set buy_count=? where cart_id=? ");
			pstmt.setByte(1, count);
			pstmt.setInt(2, cart_id);
			
			pstmt.executeUpdate();
		}catch(Exception ex){
			System.out.print("CartDBBean updateCount 에러 : ");
			ex.printStackTrace();
		}finally{
			DBConnection.disConnect(null, pstmt, conn);
		}
	}
	
	// 장바구니에서 cart_id에 대한 레코드를 삭제하는 메소드
	public void deleteList(int cart_id) throws Exception{
		try{
			conn = DBConnection.getConnection();
			
			pstmt = conn.prepareStatement("delete from cart where cart_id=? ");
			pstmt.setInt(1, cart_id);
			
			pstmt.executeUpdate();
		}catch(Exception ex){
			System.out.print("CartDBBean deleteList 에러 : ");
			ex.printStackTrace();
		}finally{
			DBConnection.disConnect(null, pstmt, conn);
		}
	}
	
	// id에 해당하는 모든 레코드를 삭제하는 메소드로 [장바구니 비우기] 버튼을 클릭 시 실행된다.
	public void deleteAll(String id) throws Exception{
		try{
			conn = DBConnection.getConnection();
			
			pstmt = conn.prepareStatement("delete from cart where buyer=? ");
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
		}catch(Exception ex){
			System.out.print("CartDBBean deleteAll 에러 : ");
			ex.printStackTrace();
		}finally{
			DBConnection.disConnect(null, pstmt, conn);
		}
	}
}
