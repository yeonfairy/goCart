<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
	@media(max-width : 980px){
		p {
			font-size : 50px;
		}
	}
</style>
</head>
<body>

<div id = "container" style = "text-align:center;">
	<div>
		<img src = "${pageContext.request.contextPath}/resources/img/${detail.img}" style = "width : 50%;">
		<p>제목 : ${detail.title}</p>
		<p>내용 : ${detail.con}</p>
		<c:if test="${!empty detail.file}">
			<p>첨부파일 : <a href = "${pageContext.request.contextPath}/download?id=${detail.id}" download>${detail.originalFile}</a></p>
		</c:if>
	</div>
	<form action = "${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method = "post">
		<input type = "hidden" id = "id" name = "id" value = "${detail.id}">
		<input type = "file" id = "upload" name = "multipartFile">
		<input type = "submit" id = "submit">
	</form>
</div>

</body>
<script type="text/javascript">
	$("#submit").click(function(){
		var submit = confirm("첨부파일을 등록하시겠습니까?");
		if(submit){
			$("#submit").submit();
		}
	});
</script>
</html>