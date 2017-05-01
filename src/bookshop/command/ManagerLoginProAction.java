package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.process.CommandAction;

public class ManagerLoginProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");//한글인코딩
			//넘어온 요청의 데이터를 얻어냄
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			//DB와 연동해서 사용자의 인증을 처리
			MngrDBBean dbPro = MngrDBBean.getInstance();
			int result = dbPro.userCheck(id, passwd);
			
			//해당 뷰(응답 페이지)로 보낼 내용을 request 속성에 지정
			request.setAttribute("result", new Integer(result));
			request.setAttribute("id", id);
		}catch (Exception e) {
			System.out.println("ManagerLoginProAction requestPro 에러 : ");
			e.printStackTrace();
		}
		return "/mngr/logon/mLoginPro.jsp";
	}

}
