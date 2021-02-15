<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/board/static/css/w3css.css">
<script src="/board/static/js/jquery-3.1.1.js"></script>
<style type="text/css">
	.w3-content{position: relative; top:5em;}
	input[name='title']{position:relative; top:-2.5em; left:5em; width: 30em; background-color: #f2f2f2;}
	textarea {position: relative; left: 5em; top:-2.5em; background-color: #f2f2f2;}
	button{position: relative; left:27em;}
}	
</style>

<script>
$(function(){
	
	//등록 버튼을 눌렀을 때 작업
	$("#INSERT_BTN").click(function(){
		var spaceRegex=  /^\s+|\s+$/g; //스페이스로 이루어진 공백검사
		var title=$("input[name='title']").val();
		var content=$("textarea[name='content']").val();
		
		if((title.replace(spaceRegex,"") == "")||!title){
			alert("제목을 입력해주세요");
			return;
		}
		
		if((content.replace(spaceRegex,"") == "")||!content){
			alert("내용을 입력해주세요");
			return;
		}
		
		$("form").submit();
	});
	
	$("#LIST_BTN").click(function(){
		location.href="/board/boardList.do";
	});
});
</script>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<div class="w3-content">
	<form action="/board/insertBoard.do" method="post">
		<div id="title">
			<span style="font-size: 1.5em;">제목</span>
			<input type="text" name="title" class="w3-input w3-border"/>
		</div>
		<div id="content">
			<span style="font-size: 1.5em;">내용</span><br>
			<textarea rows="20" cols="48" name="content"></textarea>
		</div>
	</form>		
	<button id="INSERT_BTN" class="w3-btn w3-teal w3-round">등록</button>
	<button id="LIST_BTN" class="w3-btn w3-indigo w3-round">목록</button>
	</div>
	
	
</body>
</html>