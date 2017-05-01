<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
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

	<div id="mStatus">
		<form action="/shoppingmall/modifyForm.do" method="post" id="uForm">
			<ul>
				<li><label for="passwd">비밀번호</label> <input type="password"
					name="passwd" size="20" placeholder="6~16자 숫자/문자" maxlength="16"
					id="passwd" /> <input type="hidden" name="id"
					value="${sessionScope.id }" id="id" /> <input type="submit"
					value="정보수정" id="modify" /></li>
			</ul>
		</form>

		<form action="/shoppingmall/deletePro.do" method="post" id="dForm">
			<ul>
				<li><label for="passwd">비밀번호</label> <input type="password"
					name="passwd" size="20" placeholder="6~16자 숫자/문자" maxlength="16"
					id="passwd" /> <input type="hidden" name="id"
					value="${sessionScope.id }" id="id" /> <input type="submit"
					value="탈퇴" id="delete" /> <small class="cau">[탈퇴]버튼을 누르면
						바로 탈퇴됨</small></li>
			</ul>
		</form>

		<button id="shopMain"
			onclick="window.location.href('/shoppingmall/index.do')">메인으로</button>
	</div>
</body>
</html>