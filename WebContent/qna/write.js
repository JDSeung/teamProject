$(document).ready(function(){
	$("#regist").click(function(){
		//등록버튼 클릭
		var book_kind = $("#book_kind").val();
		var book_id = $("#book_id").val();
		
		var query = {qna_content:$("#qnaCont").val(),
				qna_writer:$("#qna_writer").val(),
				book_title:$("#book_title").val(),
				book_id:book_id,
				qora:$("#qora").val()
		};
		$.ajax({
			type:"post",
			url:"/shoppingmall/qnaPro.do",
			data:query,
			success:function(data){
				var str1 = '<p id="ck">';
				var loc = data.indexOf(str1);
				var len =str1.length;
				var check = data.substr(loc+len,1);
				if(check==1){
					alert("QnA가 등록되었습니다.");
					var query = "/shoppingmall/bookContent.do?book_id="+book_id;
					query+= "&book_kind="+book_kind;
					window.location.href=query;
					//()로되어있엇음window.location.href(query);
				}else {
					alert("QnA 등록 실패");
				}
			}
		});
	});
	
	$("#cancle").click(function(){
		//취소 버튼 등록
		var book_kind =$("#book_kind").val();
		var book_id = $("#book_id").val();
		var query = "/shoppingmall/bookContent.do?book_id="+book_id;
		query += "&book_kind="+book_kind;
		window.location.href=query;
	});
});