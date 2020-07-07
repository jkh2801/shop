<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%-- isErrorPage : exception 객체를 내장 객체로 할당 --%>
<script type="text/javascript">
alert("${exception.message}");
location.href = "${exception.url}";
</script>
