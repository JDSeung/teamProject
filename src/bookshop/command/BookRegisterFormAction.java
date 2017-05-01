package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.process.CommandAction;

public class BookRegisterFormAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("type", new Integer(0));
		return "/mngr/productProcess/bookRegisterForm.jsp";
	}

}
