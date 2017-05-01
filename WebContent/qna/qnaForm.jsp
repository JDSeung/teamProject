<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/qna/write.js"></script>
<script src="/shoppingmall/js/jquery-3.2.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<c:if test="${empty sessionScope.id }">
		<meta http-equiv="Refresh" content="0;url=/shoppingmall/index.do" />
	</c:if>
	<input type="hidden" id="qna_writer" value="${sessionScope.id }" />
	<input type="hidden" id="book_kind" value="${book_kind }" />
	<input type="hidden" id="book_id" value="${book_id }" />
	<input type="hidden" id="book_title" value="${book_title }" />
	<input type="hidden" id="qora" value="${qora }" />

	<div id="writeForm" class="box">
		<ul>
			<li>[${ book_title}]에 대한 QnA</li>
			<li><label for="content">내용</label> <textarea name=""
					id="qnaCont" cols="50" rows="13"></textarea></li>
			<li class="label2"><button id="regist">등록</button>
				<button id="cancle">취소</button></li>
		</ul>
	</div>
</body>
</html>