<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/member/modify.js"></script>
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

	<div id="regForm" class="box">
		<ul>
			<li><p class="encter">회원정보수정</p></li>
			<li><label for="id">아이디</label> <input type="email" name="id"
				size="20" maxlength="50" value="${id }" readonly disabled id="id" />
			</li>
			<li><label for="passwd">비밀번호</label> <input type="password"
				name="passwd" size="20" placeholder="6~16자 숫자/문자" maxlength="16"
				id="passwd" /> <small class="cau">반드시 입력하세요</small></li>
			<li><label for="name">이름</label> <input type="text" name="name"
				size="20" maxlength="10" value="${m.getName() }" id="name" /></li>
			<li><label for="address">주소</label> <input type="text"
				id="address" name="address" size="30" maxlength="50"
				value="${m.getAddress() }" /></li>
			<li><label for="tel">전화번호</label> <input type="tel" id="tel"
				name="tel" size="20" maxlength="20" value="${m.getTel() }" /></li>
			<li class="label2"><button id="modifyProcess">수정</button>
				<button id="cancle">취소</button></li>
		</ul>
	</div>
</body>
</html>