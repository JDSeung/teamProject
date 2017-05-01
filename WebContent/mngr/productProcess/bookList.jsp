<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-3.2.1.min.js"></script>
<script src="/shoppingmall/mngr/productProcess/booklist.js"></script>
<c:if test="${empty sessionScope.id}">
	<meta http-equiv="Refresh" content="0; url=/shoppingmall/mg/managerMain.do">
</c:if>
<div id="listHeader">
	<p>등록된 상품 목록(전체 상품 : ${count})</p>
	<button id="regist">책 등록</button>
	<button id="bookMain">관리자 메인으로</button>
</div>
<div id="books">
	<c:if test = "${count == 0}">
		<ul>
			<li>등록된 상품이 없습니다.</li>
		</ul>
	</c:if>
	<c:if test = "${count > 0}">
		<table>
			<tr class="title">
				<td align="center" width="30">번호</td>
				<td align="center" width="50">책분류</td>
				<td align="center" width="300">제목</td>
				<td align="center" width="100">가격</td>
				<td align="center" width="70">수량</td>
				<td align="center" width="150">저자</td>
				<td align="center" width="150">출판사</td>
				<td align="center" width="150">출판일</td>
				<td align="center" width="350">책이미지</td>
				<td align="center" width="50">할인율</td>
				<td align="center" width="150">등록일</td>
				<td align="center" width="50">수정</td>
				<td align="center" width="50">삭제</td>
			</tr>			
			<c:set var="number" value="${0}"/>
			<c:forEach var="book" items="${bookList}">
			<tr>
				<td align="center" width="50">
					<c:set var="number" value="${number+1}"/>
					<c:out value="${number}"/>
				</td>
				<td width="30" align="center">
					${book.getBook_kind()}
				</td>
				<td width="100"  align="center">
					${book.getBook_title() }
				</td>
				<td width="50" align="right">
					${book.getBook_price()} 원
				</td>
				<td width="50" align="right">
				<c:if test="${book.getBook_count() == 0}">
					<font color="red">일시품절</font>
				</c:if>
				<c:if test="${book.getBook_count() >0}">
					&nbsp;${book.getBook_count()}권
				</c:if>
				</td>
				<td width="70">
					&nbsp;${book.getAuthor()}
				</td>
				<td width="70">
					&nbsp;${book.getPublishing_com()}
				</td>
				<td width="50">
					&nbsp;${book.getPublishing_date()}
				</td>
				<td width="50">
					&nbsp;${book.getBook_image()}
				</td>
				<td width="50" align="right">
					${book.getDiscount_rate()}%&nbsp;
				</td>
				<td width="50" align="center">
					<fmt:formatDate value="${book.getReg_date()}" pattern="yyyy-MM-dd"/>
				</td>
				<td width="50" align="center">
					<button id="edit" name="${book.getBook_id()},${book.getBook_kind()}"
							onclick="edit(this)">수정</button>
				</td>
				<td width="50" align="center">
					<button id="delete" name="${book.getBook_id()},${book.getBook_kind()}"
							onclick="del(this)">삭제</button>
				</td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
</div>