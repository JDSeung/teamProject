$(document).ready(function(){
	$("#bookMain").click(function(){
		window.location.href ="/shoppingmall/mg/managerMain.do";
	});
});
function reply(replyBtn){
	//답변하기 버튼 클릭
	var rStr = replyBtn.name;
	var query = "/shoppingmall/mg/qnaReplyForm.do?qna_id="+rStr;
	window.location.href = query;
}
function edit(editBtn){
	//수정 버튼 클릭
	var rStr = editBtn.name;
	var query = "/shoppingmall/mg/qnaReplyUpdateForm.do?qna_id="+rStr;
	window.location.href = query;
}