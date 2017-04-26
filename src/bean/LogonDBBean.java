package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sound.midi.Patch;
import javax.sql.DataSource;

import com.crypt.BCrypt;
import com.crypt.SHA256;
import com.db.DBConnection;

public class LogonDBBean {

	// LogonDBBean 전역 객체 생성 <- 한개의 객체만 생성해서 공유
	private static LogonDBBean instance = new LogonDBBean();
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = null;
	// LogonDBBean 객체를 리턴하는 메소드

	public static LogonDBBean getInstance() {
		return instance;
	}

	private LogonDBBean() {
	}

	// 회원 가입 처리에서 사용하는 메소드
	public void insertMember(LogonDataBean member) {
		sql = new StringBuffer();
		SHA256 sha = SHA256.getInsatnce();
		try {
			conn = DBConnection.getConnection();

			String orgPass = member.getPasswd();
			String shaPass = sha.getSha256(orgPass.getBytes());
			String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
			sql.append("insert into member values(?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, member.getId());
			pstmt.setString(2, bcPass);
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4, member.getReg_date());
			pstmt.setString(5, member.getAddress());
			pstmt.setString(6, member.getTel());

		} catch (Exception e) {
			System.out.print("LogonDBBean insertMember 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
	}

	// 로그인 폼처리의 사용자 인증처리에서 사용하는 메소드
	public int userCheck(String id, String passwd) {
		int x = -1;

		SHA256 sha = SHA256.getInsatnce();
		try {

			conn = DBConnection.getConnection();

			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());

			sql.append("select passwd from member where id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {// 해당 아이디가 있으면 수행
				String dbpasswd = rs.getString("passwd");
				if (BCrypt.checkpw(shaPass, dbpasswd)) {
					x = 1;// 인증성공
				} else {
					x = 0;// 비밀번호 틀림
				}
			} else {// 해당 아이디 없으면 수행
				x = -1;// 아이디없음
			}
		} catch (Exception e) {
			System.out.print("LogonDBBean userCheck 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}

	// 아이디 중복 확인에서 아이디 중복 여부를 확인하는 메소드
	public int confirmId(String id) {
		int x = -1;
		sql = new StringBuffer();
		try {
			conn = DBConnection.getConnection();

			sql.append("select id from member where id=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {// 아이디 존재
				x = 1;// 같은 아이디 있음
			} else {
				x = -1;// 같은 아이디 없음
			}
		} catch (Exception e) {
			System.out.print("LogonDBBean confirmId 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}

	// 주어진 id에 해당하는 회원 정보를 얻어내는 메소드
	public LogonDataBean getMember(String id) {
		LogonDataBean member = null;
		sql = new StringBuffer();
		try {
			conn = DBConnection.getConnection();

			sql.append("select * from member where id=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {// 해당 아이디에 대한 레코드가 존재
				member = new LogonDataBean();// 데이터 저장빈 객체 생성
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setReg_date(rs.getTimestamp("reg_date"));
				member.setAddress(rs.getString("address"));
				member.setTel(rs.getString("tel"));
			}
		} catch (Exception e) {
			System.out.print("LogonDBBean getMember 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return member;// 데이터 저장빈 객체 member 리턴
	}

	// 주어진 ip, passwd에 해당하는 회원 정보를 얻어내는 메소드
	public LogonDataBean getMember(String id, String passwd) {
		LogonDataBean member = null;
		sql = new StringBuffer();

		SHA256 sha = SHA256.getInsatnce();
		try {
			conn = DBConnection.getConnection();

			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());

			sql.append("select * from member where id=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {// 해당 아이디에 대한 레코드가 존재
				String dbpasswd = rs.getString("passwd");
				// 사용자가 입력한 비밀번호와 테이블의 비밀번호가 같으면 수행
				if (BCrypt.checkpw(shaPass, dbpasswd)) {
					member = new LogonDataBean();// 데이터 저장빈 객체 생성
					member.setId(rs.getString("id"));
					member.setName(rs.getString("name"));
					member.setReg_date(rs.getTimestamp("reg_date"));
					member.setAddress(rs.getString("address"));
					member.setTel(rs.getString("tel"));
				}
			}
		} catch (Exception e) {
			System.out.print("LogonDBBean getMember 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return member;// 데이터 저장빈 객체 member 리턴
	}
	
	//회원 정보 수정을 처리하는 메소드
	@SuppressWarnings("resource")
	public int updateMember(LogonDataBean member){
		int x = -1;
		sql = new StringBuffer();
		
		SHA256 sha = SHA256.getInsatnce();
		try{
			conn = DBConnection.getConnection();
			
			String orgPass = member.getPasswd();
			String shaPass = sha.getSha256(orgPass.getBytes());
			
			sql.append("select passwd from member where id=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();
			
			if(rs.next()){//해당 아이디가 있으면 수행
				String dbpasswd = rs.getString("passwd");
				if(BCrypt.checkpw(shaPass, dbpasswd)){
					sql.append("update member set name=?,address=?,tel=? "+"where id=?");
					pstmt=conn.prepareStatement(sql.toString());
					pstmt.setString(1, member.getName());
					pstmt.setString(2, member.getAddress());
					pstmt.setString(3, member.getTel());
					pstmt.setString(4, member.getId());
					
					pstmt.executeUpdate();
					x=1;//회원 정보 수정처리 성공
				}else {
					x =0;//회원정보 수정처리 실패
				}
			}
		}catch (Exception e) {
			System.out.print("LogonDBBean updateMember 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}
	
	//회원 정보 삭제하는 메소드
	@SuppressWarnings("resource")
	public int deleteMember(String id, String passwd){
		int x =-1;
		sql = new StringBuffer();
		SHA256 sha = SHA256.getInsatnce();
		
		try{
			conn = DBConnection.getConnection();
			
			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());
			
			sql.append("select passwd from member where id=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				String dbpasswd = rs.getString("passwd");
				if(BCrypt.checkpw(shaPass, dbpasswd)){
					sql.append("delete from member where id=?");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					x=1;//회원 탈퇴 처리 성공
				}else {
					x =0;//회원 탈퇴 처리 실패
				}
			}
		}catch (Exception e) {
			System.out.print("LogonDBBean deleteMember 에러 : ");
			e.printStackTrace();
		} finally {
			DBConnection.disConnect(rs, pstmt, conn);
		}
		return x;
	}
}
