package exception;

public class LoginException extends RuntimeException {
	// 자바에서 예외처리는 필수
		// 단, RuntimeException 예외는 예외처리를 생략할 수 있다.
		// => 예외는 RuntimeException과 그 외 Exception으로 나뉜다.
		// RuntimeException 클래스의 하위클래스들도 예외처리 생략이 가능하다.
		
		private String url;

		public LoginException(String msg, String url) {
			super(msg);
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
		
	}
