package bookshop.command;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;
import bookshop.process.CommandAction;

public class BookRegisterProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		
		String filename = "";
		String realFolder = ""; //웹 어플리케이션상의 절대 경로 저장
		String saveFolder = "/bookImage"; //파일 업로드 폴더 저장
		String encType = "UTF-8"; //인코딩 타입
		int maxSize = 1*1024*1024; //최대 업로드될 파일 크기 1MB
		
		MultipartRequest imageup = null;
		
		//웹 어플리케이션의 절대 경로를 구함
		ServletContext context = request.getSession().getServletContext();
		realFolder = context.getRealPath(saveFolder);
		try{
			//파일 업로드를 수행하는 MultipartRequest 객체 생성
			imageup = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
			
			//<input type = "file>인 모든 파라미터를 얻어냄
			Enumeration<?> files = imageup.getFileNames();
			
			//파일 정보가 있다면
			while(files.hasMoreElements()){
				//input 태그 속성이 file인 태그의 name 속성 값 : 파라미터이름
				String name = (String) files.nextElement();
				
				//서버에 저장된 파일 이름
				filename = imageup.getFilesystemName(name);
			}
			
		}catch (Exception e) {
			System.out.print("BookRegisterProAction requestPro 에러 : ");
			e.printStackTrace();
		}
		
		//폼으로부터 넘어온 정보중 파일이 아닌 정보를 얻어냄
		MngrDataBean book = new MngrDataBean();
		String book_kind = imageup.getParameter("book_kind");
		String book_title = imageup.getParameter("book_title");
		String book_price = imageup.getParameter("book_price");
		String book_count = imageup.getParameter("book_count");
		String author = imageup.getParameter("author");
		String publishing_com = imageup.getParameter("publishing_com");
		String book_content = imageup.getParameter("book_content");
		String discount_rate = imageup.getParameter("discount_rate");
		
		//책 등록일 계산
		String year = imageup.getParameter("publishing_year");
		/*
		 * String month = (imageup.getParameter("publishing_month").length()==1)? "0" + 
		 *	imageup.getParameter("publishing_month"): imageup.getParameter("publishing_month");  
		 * String day =  (imageUp.getParameter("publishing_day").length()==1)? "0" + 
		 *	imageUp.getParameter("publishing_day"):
		 *	imageUp.getParameter("publishing_day");
		 */
		String month = imageup.getParameter("publishing_month");
		if(month.length()== 1){
			month = "0" + month;
		}
		String day = imageup.getParameter("publishing_day");
		if(day.length()== 1){
			day = "0" + day;
		}
		book.setBook_kind(book_kind);
		book.setBook_title(book_title);
		book.setBook_price(Integer.parseInt(book_price));
		book.setBook_count(Short.parseShort(book_count));
		book.setAuthor(author);
		book.setPublishing_com(publishing_com);
		book.setPublishing_date(year +"-" + month + "-" + day);
		book.setBook_image(filename);
		book.setBook_content(book_content);
		book.setDiscount_rate(Byte.parseByte(discount_rate));
		book.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		//DB연동 - 넘어온 정보를 테이블 레코드로 추가
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		bookProcess.insertBook(book);
		
		request.setAttribute("book_kind", book_kind);
		return "/mngr/productProcess/bookRegisterPro.jsp";
	}

}
