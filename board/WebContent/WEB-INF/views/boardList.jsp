<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/board/static/css/w3css.css">
<script src="/board/static/js/jquery-3.1.1.js"></script>
<style type="text/css">
	.w3-content{position: relative; top:3em;}
	#SEARCH_TEXT{position: relative; width: 80em;}
	input[name='title']{width: 20em;}
	#SEARCH_BTN{position: relative; top:-2.5em; left: 21em;}
	#INSEART_BTN{position: relative; top:-2.5em; left:50em;}
</style>
<script>
$(function(){
	
	$("#SEARCH_BTN").click(function (){
		$("form").submit();
	});
	
	$("#INSEART_BTN").click(function(){
		location.href="/board/inputForm.do";
	});
});
</script>
<meta charset="UTF-8">
<title>글목록</title>
</head>
<body>
	<div class="w3-content">
		<div id="SEARCH_TEXT">
		<form action="/board/boardList.do" method="get">
			<input type="text" class="w3-input w3-border w3-round" name="title"/>
		</form>
			<button class="w3-teal w3-btn w3-round" id="SEARCH_BTN">검색</button>
			<button class="w3-blue w3-btn w3-round" id="INSEART_BTN">추가</button>
			
		</div>
		<div id="BOARD_CONTENT">
			<table class="w3-table w3-striped w3-border">
				<tr class="w3-green">
					<th>ID</th>
					<th>제목</th>
				</tr>
			
			<c:choose>
				
				<c:when test="${view.boardList.size() eq 0}">
				<tr>
					<td colspan="2" style="text-align: center;"> 글이 없습니다.</td>
				</tr>				
				</c:when>
				
				<c:otherwise>
				<c:forEach items="${view.boardList}" var="board">
				<tr>
					<td>${board.id}</td>
					<td><a href="/board/getBoard.do?id=${board.id}">${board.title}</a></td>
				</tr>				
				</c:forEach>	
				</c:otherwise>				
			</c:choose>
			
			</table>
		</div>
	</div>
</body>
</html>