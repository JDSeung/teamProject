<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width, initial-scale=1.0"/>
<title>Insert title here</title>
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/js/jquery-3.2.1.min.js"></script>
<script src="/shoppingmall/js/managermain.js"></script>
</head>
<body>
	<c:if test="${empty sessionScope.id}">
		<div id="status">
			<ul>
				<li>
					<label for="id">아이디</label>
					<input type="email" name="id" id="id" size="20" maxlength="50" placeholder="eample@kings.com" />
					<label for="passwd">비밀번호</label>
					<input type="password" name="passwd" id="passwd" size="20" placeholder="6~16자 숫자/문자" maxlength="16"/>
					<button id="login">로그인</button>
				</li>
			</ul>
		</div>
		<c:if test="${!empty sessionScope.id}">
			<div id="status">
				<ul>
					<li>
						관리자 로그인 성공!!.. 작업중...
						<button id="logout">로그아웃</button>
					</li>
				</ul>
			</div>
		</c:if>
	</c:if>

</body>
</html>