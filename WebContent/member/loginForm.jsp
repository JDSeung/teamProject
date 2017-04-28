<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/member/login.js"></script>
<script src="/shoppingmall/js/jquery-3.2.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<c:if test="${empty sessionScope.id }">
		<div id="lStatus">
			<ul>
				<li><label for="cid">아이디</label> <input type="email" name="cid"
					size="20" maxlength="50" placeholder="example@kings.com" id="cid" />
					<label for="cpasswd">비밀번호</label> <input type="password"
					name="cpasswd" size="20" placeholder="6~16자 숫자/문자" maxlength="16"
					id="cpasswd" />
					<button id="uLogin">로그인</button>
					<button id="uRes">회원가입</button></li>
			</ul>
		</div>
	</c:if>

	<c:if test="${!empty sessionScope.id }">
		<div id="lStatus">
			<ul>
				<li>${sessionScope.id }님이로그인하셨습니다.
					<div id="info">
						<table>
							<tr height="10">
								<td><button id="uLogout">로그아웃</button></td>
								<td><button id="uUpdate">회원 정보 변경</button></td>
								<td>
									<form action="/shoppingmall/cartList.do" method="post"
										id="cartForm">
										<input type="hidden" name="buyer" value="${sessionScope.id }" />
										<input type="submit" name="cart" value="장바구니" />
									</form>
								</td>
								<td>
									<form action="/shoppingmall/buyList.do" method="post"
										id="buyForm">
										<input type="hidden" name="buyer" value="${sessionScope.id }" />
										<input type="submit" name="buy" value="구매내역" />
									</form>
								</td>
							</tr>
						</table>
					</div>
				</li>
			</ul>
		</div>
	</c:if>
</body>
</html>