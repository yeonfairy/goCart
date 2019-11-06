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
	table {
		border-spacing: 30px 0px;
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
<c:if test="${empty cartSession}">
	<h2>장바구니가 비었습니다.</h2>
</c:if>
<body>
	<table>
		<tr>
			<c:forEach var="cart" items="${cartSession}" varStatus="status">
				<td><div id="content" class="content">
						<img src="resources/img/${cart.img}" class="cartImg"
							style="border: 2px solid white;" onclick="detail(${cart.id})">
						<input type="checkbox" id="chkBox" name="chkBox" class="chkBox"
							onclick = "delCart('${cart.id}', '${cart.img}', '${cart.title}', '${cart.con}')">
						<p>${cart.title}</p>
						<p>${cart.con}</p>
						<c:if test="${!empty cart.file}">
							<p>첨부파일 O</p>
						</c:if>
						<c:if test="${empty cart.file}">
							<p>첨부파일 X</p>
						</c:if>
					</div> 
				</td>
			<c:if test="${status.count%4 == 0}">
		</tr>
		<tr>
			</c:if>
			</c:forEach>
		</tr>
	</table>
</body>
<script type="text/javascript">

function delCart(id, img, title, con){
	var conf = confirm("장바구니에서 삭제하시겠습니까?");
	if(conf == true){
		$.ajax({
			url : "${pageContext.request.contextPath}/cart",
			type : "post",
			data : {
				id : id,
				img : img,
				title : title,
				con : con,
				chk : "false"
			},
			success : function(data){
				alert(data);
				location.reload();
			}
		});
	} else {
		location.reload();
	}
}

</script>
</html>