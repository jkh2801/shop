package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

@Component
@Aspect
@Order(1)
public class UserLoginAspect {
	
	// controller 패키지의 User이름으로 시작하는 클래스에서 loginCheck으로 시작하는 메서드
	@Around ("execution(* controller.User*.loginCheck*(..)) && args(..,session)")
	public Object userLoginCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable{
		User loginUser = (User) session.getAttribute("loginUser");
		if(loginUser == null) throw new LoginException("[System] : 로그인 후 거래하세요.", "login.shop");
		return joinPoint.proceed();
	}
	
	@Around ("execution(* controller.User*.mypageCheck*(..)) && args(..,id,session)")
	public Object myPageCheck(ProceedingJoinPoint joinPoint, String id, HttpSession session) throws Throwable{
		User loginUser = (User) session.getAttribute("loginUser");
		if(loginUser == null) throw new LoginException("[System] : 로그인 후 거래하세요.", "login.shop");
		else if(!loginUser.getUserid().equals("admin") && !loginUser.getUserid().equals(id))
			throw new LoginException("[System] : 해당 권한이 없습니다.", "main.shop");
		return joinPoint.proceed();
	}
	
}
