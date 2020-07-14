<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Websocket Client</title>
<c:set var="port" value="${pageContext.request.localPort }"></c:set>
<c:set var="server" value="${pageContext.request.serverName }"></c:set>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<script type="text/javascript">
$(function () {
	var ws = new WebSocket("ws://${server}:${port}${path}/chatting.shop");
	ws.onopen = function () {
		$("#chatStatus").text("info:connection opend");
		$("input[name=chatInput]").on("keydown", function(evt) {
			if (evt.keyCode == 13) {
				var msg = $("input[name=chatInput]").val();
				ws.send(msg);
				$("input[name=chatInput]").val("");		
			}
		})
	}
	ws.onmessage = function (event) { // 서버에서 메시지를 수신한 경우
		console.log(event);
		$("textarea").eq(0).prepend(event.data+"\n");
	}
	ws.onclose = function (event) {
		$("#chatStatus").text("info:connection close");
	}
})
</script>
</head>
<body>
<input type="hidden" value="${server}">
<input type="hidden" value="${port }">
<input type="hidden" value="${path }">
<div id="chatStatus"></div>
<textarea rows="15" cols="40" name="chatMsg"></textarea><br>
메시지 입력: <input type="text" name="chatInput">
</body>
</html>