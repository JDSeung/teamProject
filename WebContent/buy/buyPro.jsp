<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<c:if test="${empty sessionScope.id }">
		<meta http-equiv="Refresh" content="0;url=/shoppingmall/index.do" />
	</c:if>

	<div id="orderResult">
		<p>${orderStus }</p>
	</div>
	
	<div id="buyProcess">
		<form action="/shoppingmall/buyList.do" method="post" id="buyPro">
			<input type="hidden" name="buyer" value="${sessionScope.id }" />
			<input type="submit" value="주문확인" />
		</form>
	</div>
</body>
</html>