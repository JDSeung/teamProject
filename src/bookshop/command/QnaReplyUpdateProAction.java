package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;
import bookshop.process.CommandAction;

public class QnaReplyUpdateProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("UTF-8");
			int qna_id = Integer.parseInt(request.getParameter("qna_id"));
			String qna_content = request.getParameter("qna_content");
			
			//QnA 답변글 수정 정보 설정
			QnaDataBean qna = new QnaDataBean();
			qna.setQna_id(qna_id);
			qna.setQna_content(qna_content);
			
			//QnA답변글 수정 처리
			QnaDBBean qnaProcess = QnaDBBean.getInstance();
			int result = qnaProcess.updateArticle(qna);
			request.setAttribute("result", new Integer(result));
		}catch (Exception e) {
			System.out.println("QnaReplyUpdateProAction requestPro 에러 :");
			e.printStackTrace();
		}

		return "/mngr/qnaProcess/qnaReplyUpdatePro.jsp";
	}

}
