$(document).ready(function(){
	$("#regist").click(function(){
		//책 등록버튼 클릭
		window.location.href="/shoppingmall/mg/bookRegisterForm.do";
	});
	$("#bookMain").click(function(){
		//관리자 메인으로 버튼 클릭
		window.location.href="/shoppingmall/mg/managerMain.do";
	});
});

//수정버튼 클릭하면 자동실행
function edit(editBtn){
	var rStr = editBtn.name;
	var arr = rStr.split(",");
	var query = "/shoppingmall/mg/bookUpdateFrom.do?booK_id=?" + arr[0];
	query += "&book_kind="+arr[1];
	window.location.href(query);
}
//삭제버튼 클릭하면 자동실행
function edit(delBtn){
	var rStr = delBtn.name;
	var arr = rStr.split(",");
	var query = "/shoppingmall/mg/bookDeleteFrom.do?booK_id=?" + arr[0];
	query += "&book_kind="+arr[1];
	window.location.href(query);
}

