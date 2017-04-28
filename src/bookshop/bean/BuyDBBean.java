package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.db.DBConnection;
import com.oracle.webservices.internal.api.databinding.Databinding.Builder;

import javafx.scene.control.ButtonBar.ButtonData;

public class BuyDBBean {
	private static BuyDBBean instance = new BuyDBBean();
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = null;

	public static BuyDBBean getInstance() {
		return instance;
	}

	private BuyDBBean() {
	}

	// bank 테이블에 있는 전체 레코드를 얻어내는 메소드
	public List<String> getAccount() {
		List<String> accountList = null;

		try {
			conn = DBConnection.getConnection();

			sql.append("select * from bank");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			accountList = new ArrayList<String>();

			while (rs.next()) {
				String account = new String(
						rs.getString("account") + " " + rs.getString("bank") + " " + rs.getString("name"));
				accountList.add(account);
			}
		} catch (Exception e) {
			System.out.print("BuyDBBean getAccount 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return accountList;
	}

	// 구매 테이블인 buy에 구매 목록 등록
	@SuppressWarnings("resource")
	public void insertBuy(List<CartDataBean> lists, String id, String account, String deliveryName, String deliveryTel,
			String deliveryAddress) throws Exception {
		Timestamp reg_date = null;
		String maxDate = "";
		String number = "";
		String todayDate = "";
		String compareDate = "";
		long buyId = 0;
		short nowCount;
		try {
			conn = DBConnection.getConnection();
			reg_date = new Timestamp(System.currentTimeMillis());
			todayDate = reg_date.toString();
			compareDate = todayDate.substring(0, 4) + todayDate.substring(5, 7) + todayDate.substring(8, 10);

			sql.append("select max(buy_id) from buy");

			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			rs.next();

			if (rs.getLong(1) > 0) {
				Long val = new Long(rs.getLong(1));
				maxDate = val.toString().substring(0, 8);
				number = val.toString().substring(8);
				if (compareDate.equals(maxDate)) {
					if ((Integer.parseInt(number) + 1) < 10000) {
						buyId = Long.parseLong(maxDate + (Integer.parseInt(number) + 1 + 10000));
					} else {
						buyId = Long.parseLong(maxDate + (Integer.parseInt(number) + 1 + 10000));
					}
				} else {
					compareDate += "00001";
					buyId = Long.parseLong(compareDate);
				}
			} else {
				compareDate += "00001";
				buyId = Long.parseLong(compareDate);
			}
			// 96~147 라인까지 하나의 트랜잭션으로 처리
			conn.setAutoCommit(false);
			for (int i = 0; i < lists.size(); i++) {
				// 해당 아이디에 대한 cart 테이블의 레코드를 가저온후 buy 테이블에 추가
				CartDataBean cart = lists.get(i);

				sql.append("insert into buy(buy_id,buyer,book_id,book_title,buy_price,buy_count,");
				sql.append("book_image,buy_date,account, deliveryName, deliveryTel, deliveryAddress)");
				sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setLong(1, buyId);
				pstmt.setString(2, id);
				pstmt.setInt(3, cart.getBook_id());
				pstmt.setString(4, cart.getBook_title());
				pstmt.setInt(5, cart.getBuy_price());
				pstmt.setInt(6, cart.getBuy_count());
				pstmt.setString(7, cart.getBook_image());
				pstmt.setTimestamp(8, reg_date);
				pstmt.setString(8, account);
				pstmt.setString(10, deliveryName);
				pstmt.setString(11, deliveryTel);
				pstmt.setString(12, deliveryAddress);
				pstmt.executeUpdate();

				// 상품이 구매되었으므로 book 테이블의 상품 수량을 재조정함
				sql.append("select book_count from book where book_id=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, cart.getBook_id());
				rs = pstmt.executeQuery();
				rs.next();

				nowCount = (short) (rs.getShort(1) - 1);// 실무에서는 구매 수량을 뺄 것

				sql.append("update book set book_count=? where book_id=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setShort(1, nowCount);
				pstmt.setInt(2, cart.getBook_id());

				pstmt.executeUpdate();
			}

			sql.append("delete from cart where buyer=?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, id);

			pstmt.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.out.print("BuyDBBean insertBuy 에러  : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
	}

	// id에 해당하는 buy 테이블의 레코드 수를 얻어내는 메소드
	public int getListCount(String id) throws Exception {
		int x = 0;

		try {
			conn = DBConnection.getConnection();

			sql.append("select count(*) from buy where buyer=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.print("BuyDBBean getListCount(String) 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}

	// buy테이블의 전체 레코드 수를 얻어내는 메소드
	public int getListCount() throws Exception {
		int x = 0;

		try {
			conn = DBConnection.getConnection();

			sql.append("select count(*) from buy");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.print("BuyDBBean getListCount 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}

	// id에 해당하는 buy 테이블의 구매 목록을 얻어내는 메소드
	public List<BuyDataBean> getBuyList(String id) throws Exception {
		BuyDataBean buy = null;
		List<BuyDataBean> lists = null;

		try {
			conn = DBConnection.getConnection();

			sql.append("select * from buy where buyer = ?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			lists = new ArrayList<BuyDataBean>();

			while (rs.next()) {
				buy = new BuyDataBean();

				buy.setBuy_id(rs.getLong("buy_id"));
				buy.setBook_id(rs.getInt("book_id"));
				buy.setBook_title(rs.getString("book_title"));
				buy.setBuy_price(rs.getInt("buy_price"));
				buy.setBuy_count(rs.getByte("buy_count"));
				buy.setBook_image(rs.getString("book_image"));
				buy.setSanction(rs.getString("sanction"));

				lists.add(buy);
			}
		} catch (Exception e) {
			System.out.print("BuyDBBean getBuyList(String) 에러 :");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return lists;
	}

	// buy 테이블의 전체 목록을 얻어내는 메소드
	public List<BuyDataBean> getBuyList() throws Exception {
		BuyDataBean buy = null;
		List<BuyDataBean> lists = null;

		try {
			conn = DBConnection.getConnection();

			sql.append("select *from buy");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			lists = new ArrayList<BuyDataBean>();

			while (rs.next()) {
				buy = new BuyDataBean();

				buy.setBuy_id(rs.getLong("buy_id"));
				buy.setBuyer(rs.getString("buyer"));
				buy.setBook_id(rs.getInt("book_id"));
				buy.setBook_title(rs.getString("book_title"));
				buy.setBuy_price(rs.getInt("buy_price"));
				buy.setBuy_count(rs.getByte("buy_count"));
				buy.setBook_image(rs.getString("book_image"));
				buy.setAccount(rs.getString("account"));
				buy.setDeliveryName(rs.getString("deliveryName"));
				buy.setDeliveryTel(rs.getString("deliveryTel"));
				buy.setDeliveryAddress(rs.getString("deliveryAddress"));
				buy.setSanction(rs.getString("sanction"));

				lists.add(buy);
			}
		} catch (Exception e) {
			System.out.print("BuyDBBean getBuyList 에러 :");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return lists;
	}
}
