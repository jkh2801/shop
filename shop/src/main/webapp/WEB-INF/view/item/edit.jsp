<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품수정</title>
</head>
<body>
<form:form modelAttribute="item" action="update.shop" enctype="multipart/form-data">
<!-- multipart/form-data : post 방식으로 진행 -->
	<form:hidden path="id"/>
	<form:hidden path="pictureURL"/>
	<h2>상품정보 수정</h2>
	<table>
	<tr>
	<td>상품명</td>
	<td><form:input path="name"  maxlength="20"/></td>
	<td><font color="red"><form:errors path="name"></form:errors></font></td>
	</tr>
	<tr>
	<td>상품가격</td>
	<td><form:input path="price"  maxlength="20"/></td>
	<td><font color="red"><form:errors path="price"></form:errors></font></td>
	</tr>
	<tr>
	<td>상품이미지</td>
	<td><input type="file" name="picture"></td>
	<td>${item.pictureURL}</td>
	</tr>
	<tr>
	<td>상품설명</td>
	<td><form:textarea path="description"  cols="20" rows="5"/></td>
	<td><font color="red"><form:errors path="description"></form:errors></font></td>
	</tr>
	<tr>
	<td colspan="3"><input type="submit" value="수정등록">&nbsp;
	<input type="button" value="상품목록" onclick="location.href='list.shop'"></td>
	</tr>
	</table>
</form:form>
</body>
</html>