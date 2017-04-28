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
<script src="/shoppingmall/mngr/managerMain.js"></script>
</head>
<body>
	<c:if test="${empty sessionScope.id}">
		<div id="mList">로그인하세요</div>
	</c:if>
	<c:if test="${!empty sessionScope.id}">
		<div id="mList">
			<ul>
				<li>상품관련 작업</li>
				<li><button id="registProduct">상품등록</button></li>
				<li><button id="updateProduct">상품수정/삭제</button></li>
			</ul>
			<ul>
				<li>구매된 상품관련 작업</li>
				<li><button id="qna">상품 QnA답변</button></li>
			</ul>
		</div>
	</c:if>
</body>
</html>