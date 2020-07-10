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
@Order(3)
public class AdminAspect {

	@Around ("execution(* controller.Admin*.*(..))")
	public Object adminCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		User loginUser = null;
		for(Object o : joinPoint.getArgs())
			if(o instanceof HttpSession) {
				HttpSession session = (HttpSession) o;
				loginUser = (User) session.getAttribute("loginUser");
			}
		if(loginUser == null) throw new LoginException("[System] : 로그인 후 이용하세요.", "../user/login.shop");
		else if (!loginUser.getUserid().equals("admin")) throw new LoginException("[System] : 해당 권한이 없습니다.", "../user/main.shop");
		return joinPoint.proceed();
	}
}
