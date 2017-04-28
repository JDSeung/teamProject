package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.LogonDBBean;
import bookshop.process.CommandAction;

public class LoginProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");

		// 사용자가 입력한 id,passwd를 가지고 인증 체크후 값 반환
		LogonDBBean manager = LogonDBBean.getInstance();
		int check = manager.userCheck(id, passwd);

		request.setAttribute("id", id);
		request.setAttribute("check", new Integer(check));
		return "/member/loginPro.jsp";
	}

}
