<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 정보 수정</title>
</head>
<body>
	<h2>사용자 정보 수정</h2>
	<form:form modelAttribute="user" method="post" action="update.shop">
		<spring:hasBindErrors name="user">
			<font color="red"> <c:forEach items="${errors.globalErrors }"
					var="error">
					<spring:message code="${error.code }"></spring:message>
				</c:forEach>
			</font>
		</spring:hasBindErrors>
		<table>
			<tr>
			<td>아이디</td>
			<td><form:input path="userid" readonly="true"/>
				<font color="red"><form:errors path="userid"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>비밀번호</td>
			<td><form:password path="password"/>
				<font color="red"><form:errors path="password"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>이름</td>
			<td><form:input path="username"/>
				<font color="red"><form:errors path="username"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>전화번호</td>
			<td><form:input path="phoneno"/>
				<font color="red"><form:errors path="phoneno"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>우편번호</td>
			<td><form:input path="postcode"/>
				<font color="red"><form:errors path="postcode"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>주소</td>
			<td><form:input path="address"/>
				<font color="red"><form:errors path="address"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>이메일</td>
			<td><form:input path="email"/>
				<font color="red"><form:errors path="email"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td>생년월일</td>
			<td><form:input path="birthday"/>
				<font color="red"><form:errors path="birthday"></form:errors></font>
			</td>
			</tr>
			<tr>
			<td colspan="2" align="center">
			<input type="submit" value="수정">
			<input type="reset" value="초기화">
			</td>
			</tr>
		</table>
	</form:form>
</body>
</html>