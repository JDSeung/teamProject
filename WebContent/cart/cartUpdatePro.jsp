<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width, initial-scale=1.0"/>
<title>Insert title here</title>
</head>
<body>
	<c:if test="${empty sessionScope.id}">
		<meta htp-equiv="Refresh" content="0; url=/shoppingmall/index.do">
	</c:if>
	
	<div id="updateResult">
		<p>수량이 수정되었습니다.</p>
	</div>
	
	<div id="cartUpdatePro">
		<form id="cartUpdatePro" method="post" action="/shoppingmall/cartList.do">
			<input type="hidden" name="buyer" value="${sessionScope.id}"/>
			<input type="submit" value="장바구니로 되돌아가기"/>
		</form>
	</div>
</body>
</html>