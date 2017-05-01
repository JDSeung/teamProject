<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="/shoppingmall/js/jquery-3.2.1.min.js"></script>
<script src="/shoppingmall/member/register.js"></script>
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<div id="regForm" class="box">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="email" name="id" size="20" maxlength="50" 
					   placeholder="example@kings.com" autofocus id="id" />
				<button id="checkId">ID중복확인</button>
			</li>
			<li><label for="passwd">비밀번호</label> <input type="password"
				id="passwd" name="passwd" size="20" placeholder="6~16자 숫자/문자"
				maxlength="16" /></li>
			<li><label for="repass">비밀번호 재입력</label> <input type="password"
				name="repass" size="20" placeholder="비밀번호 재입력" maxlength="16"
				id="repass" /></li>
			<li><label for="name">이름</label> <input type="text" name="name"
				size="20" placeholder="홍길동" maxlength="10" id="name" /></li>
			<li><label for="address">주소</label> <input type="text"
				name="address" size="30" placeholder="주소 입력" maxlength="50"
				id="address" /></li>
			<li><label for="tel">전화번호</label> <input type="tel" name="tel"
				size="20" placeholder="전화번호 입력" id="tel" maxlength="20" /></li>
			<li class="label2"><button id="process">가입하기</button>
				<button id="cancle">취소</button></li>
		</ul>
	</div>
</body>
</html>