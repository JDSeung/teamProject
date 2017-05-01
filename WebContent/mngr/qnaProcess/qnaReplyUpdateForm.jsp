<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta name = "viewport" content="width=device-width, initial-scale=1.0"/>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-3.2.1.min.js"></script>
<script src="/shoppingmall/mngr/qnaProcess/qnaupdate.js"></script>
<c:if test="${empty sessionScope.id}">
	<meta http-equiv="Refresh" content="0; url=/shoppingmall/mg/managerMain.do">
</c:if>	
<input type="hidden" name="qna_id" value="${qna_id}" />
<div id="editForm" class="box">
	<ul>
		<li>
			<label for="content">내용</label>
			<textarea id="uRContent" cols="50" rows="13">${qna.getQna_content()}</textarea>
		</li>
		<li class="label2">
			<button id="update">수정</button>
			<button id="cancle">취소</button>
		</li>
	</ul>
</div>