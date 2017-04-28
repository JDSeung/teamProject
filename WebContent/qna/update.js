$(document).ready(function(){
	$("#update").click(function(){
		// 수정 버튼클릭
		var book_id =$("#book_id").val();
		var book_kind=$("book_kind").val();
		
		var query = {
				qna_content:$("#updateCont").val(),
				qna_id:$("#qna_id").val()
		};
		$.ajax({
			type:"post",
			url:"/shoppingmall/qnaUpdatePro.do",
			data:query,
			success:function(data){
				var str1 = '<p id="ck">';
				var loc = data.indexOf(str1);
				var len =str1.length;
				var check = data.substr(loc+len,1);
				if(check==1){
					alert("QnA가 수정 되었습니다.");
					var query = "/shoppingmall/bookContent.do?book_id=" + book_id;
					query += "&book_kind="+book_kind;
					window.location.href=query;
				}else {
					alert("QnA수정 실패");
				}
			}
		});
	});
	
	$("#calcle").click(function(){
		var book_id =$("#book_id").val();
		var book_kind =$("#book_kind").val();
		var query = "/shoppingmall/bookContent.do?book_id="+book_id;
		query += "&book_kind="+book_kind;
		window.location.href=query;
	});
});