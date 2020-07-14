package websocket;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EchoHandler extends TextWebSocketHandler implements InitializingBean {

	private Set<WebSocketSession> clients = new HashSet<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		System.out.println("클라이언트 접속: "+ session.getId());
		clients.add(session);
		System.out.println("session: "+session);
		System.out.println("set: "+clients);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String loadMessage = (String) message.getPayload();
		System.out.println(session.getId() + ": 클라이언트 메시지 : "+loadMessage);
		clients.add(session);
		for(WebSocketSession s : clients)
			s.sendMessage(new TextMessage(loadMessage)); // 접속된 모든 클라이언트에게 수신된 메세지를 전송
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		System.out.println("오류발생: "+exception.getMessage());
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		System.out.println("클라이언트 접속 해제: "+status.getReason());
		clients.remove(session);
	}

	public void afterPropertiesSet() throws Exception {
	}

	
	
}
