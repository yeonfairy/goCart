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
	a {
		text-decoration : none;
		border : 1px solid gray;
		width : 30px;
		padding : 5px;
		float : left;
		text-align : center;
		color : black;
	}
	
	table {
		margin : auto;
		border-spacing: 30px 0px;
	}
	.btnGroup {
		text-align : center;
		margin-bottom : 20px;
	}
	.contentImg:hover {
		border : 2px solid blue !important;
	}
	.contentImg:hover{
		cursor : pointer;
	}
	.previous, .next {
		margin: 0px 10px;
	}
	
	@media(max-width : 980px){
		 .btnGroup img{
			width : 30%;
		}
		/* table img {
			width : 100%;
		}
		table {
			width : 800px;
		}
		table p {
			font-size : 30px;
		}
		.paging span {
			font-size : 30px;
		} */
		.numBox a {
			font-size : 10px;
			width : 10px;
		}
	}
	.content {
		position: relative;
	}
	.chkBox {
		position: absolute;
  		right: 1px;
 		top: 52px;
 		width: 20px;
  		height: 20px;
	}
	
</style>
</head>
<body>
<div id = "container">
	<div id = "btnGroup" class = "btnGroup" style = "position : relative;">
		<img src = "resources/img/icon_3page.png" id = "3line" onclick = "line(3)">
	    <img src = "resources/img/icon_4page.png" id = "4line" onclick = "line(4)">
		<img src = "resources/img/icon_5page.png" id = "5line" onclick = "line(5)">
		<button onclick = "getCart()" style = "position: absolute; margin-left: 5px;">장바구니에 저장</button>
		<button id = "cart" onclick = "checkBox()" style = "position: absolute; margin-left: 120px;">장바구니</button></div>
	<div id = "contentsWrap">
		<table>
			<tr>
				<c:forEach var = "list" items = "${list.boardList}" varStatus="status">
					<td><div id = "content" class = "content">
						<img src = "resources/img/${list.img}" class = "contentImg" style = "border : 2px solid white;" onclick = "detail(${list.id})">
						<input type = "checkbox" id = "chkBox" name = "chkBox" class = "chkBox" 
						value = "${list.id}" 
						<%-- onclick = "goCart('${list.id}')" --%>>
						<p>${list.title}</p>
						<p>${list.con}</p>
						<c:if test="${!empty list.file}">
							<p>첨부파일 O</p>
						</c:if>
						<c:if test="${empty list.file}">
							<p>첨부파일 X</p>
						</c:if>
					</div></td>
						<c:if test="${status.count%4 == 0}">
							</tr><tr>
						</c:if>
				</c:forEach>
			</tr>
		</table>
	
	<div class = "paging" style = "text-align : center;">
		<div class = "numBox" style = "display : inline-block;">
		<!-- 첫페이지 -->
		<c:choose>
			<c:when test="${param.page == 1 || empty param.page}">
				<a style = "display : hidden; border : none;"></a>
			</c:when>
			<c:otherwise>
				<a href = "?page=1&line=${param.line}" class = "first"><span> << </span></a>
			</c:otherwise>
		</c:choose>
		<!-- 이전페이지 -->
		<c:choose>
			<c:when test="${param.page == 1 || empty param.page}">
				<a style = "display : hidden; border : none;"></a>
			</c:when>
			<c:otherwise>
				<a href = "?page=${param.page - 1}&line=${param.line}" class = "previous"><span> < </span></a>
			</c:otherwise>
		</c:choose>
		<!-- 일반페이지 카운팅 -->
		<c:forEach var = "num" begin = "1" end = "${list.pageTotalCount}">
			<c:if test="${param.page == num}">
				<a class="currentPage" href="?page=${num}&line=${param.line}" style = "background-color : gray;"><span>${num}</span></a>
			</c:if>
			<c:if test="${param.page != num}">
				<a class="otherPage" href="?page=${num}&line=${param.line}"><span>${num}</span></a>
			</c:if>
		</c:forEach>
		<!-- 다음페이지  -->
		<c:choose>
			<c:when test="${param.page == list.pageTotalCount}">
				<a style = "display : hidden; border : none;"></a>
			</c:when>
			<c:otherwise>
				<a href = "?page=${param.page + 1}&line=${param.line}" class = "next"><span> > </span></a>
			</c:otherwise>
		</c:choose>
		<!-- 끝페이지 -->
		<c:choose>
			<c:when test="${param.page == list.pageTotalCount}">
				<a style = "display : hidden; border : none;"></a>
			</c:when>
			<c:otherwise>
				<a href = "?page=${list.pageTotalCount}&line=${param.line}" class = "end"><span> >> </span></a>
			</c:otherwise>
		</c:choose>
		</div>
		</div>
	</div>
</div>
</body>
<script>

	/* $('#3line').click(function(){
		location.href = "${pageContext.request.contextPath}?page=1&line"
	}); */
	function line(num){
		location.href = "${pageContext.request.contextPath}?page=1&line=" + num;
	}
	
	function detail(id){
		location.href = "${pageContext.request.contextPath}/detail?id=" + id;
	}
	
	/* function checkBox(){
		var checkArray = [];
		$("input[name=chkBox]:checked").each(function(i){
			checkArray.push($(this).val());
		});
		console.log(checkArray);
		console.log(checkArray[0].indexOf("title"));
		$.ajaxSettings.traditional = true;
		
		var data = {"checkArray":checkArray}

		$.ajax({
			url : "${pageContext.request.contextPath}/cart",
			type : "post",
			data : data,
			success : function(data){
				alert(data);
				//location.href = "${pageContext.request.contextPath}/cart";
			}
		});
	} */
	
	/* function checkBox(){
		var list = [];
		$("input[name=chkBox]:checked").each(function(i){
			var checkArray = [];
			console.log($(this).val().split(", "));
			console.log($(this).val().split(", "));
			checkArray.push($(this).val().split(", "));
			list.push(checkArray);
		});
		console.log(list);
		$.ajaxSettings.traditional = true;
		var data = {"list":list}
		
		$.ajax({
			url : "${pageContext.request.contextPath}/cart",
			type : "post",
			data : data,
			success : function(data){
				alert(data);
				//location.href = "${pageContext.request.contextPath}/cart";
			}
		});
	} */
	
	$(document).ready(function(){
		$("input:checkbox[id='chkBox']").prop("checked",false);
	});
	
	function checkBox(){
		location.href = "${pageContext.request.contextPath}/cart";
	}
	
	function getCart(){
		$("input:checkbox[name='chkBox']:checked").each(function(){
			var id = $(this).val();
			goCart(id);
		})
	}

	function goCart(id){
		$.ajax({
			url : "${pageContext.request.contextPath}/cart",
			type : "post",
			data : {
				id : id,
				chk : $("input:checkbox[name='chkBox']").is(':checked')
			},
			success : function(data){
				/* console.log(data);
				alert(data); */
			}
		});
	}
	
</script>
</html>