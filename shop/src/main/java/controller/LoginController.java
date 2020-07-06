package controller;

import javax.servlet.http.HttpSession;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService2;
import logic.User;
import util.LoginValidator;

public class LoginController {
	private ShopService2 shopService;
	private LoginValidator loginValidator;
	
	public void setShopService(ShopService2 shopService) {
		this.shopService = shopService;
	}
	public void setLoginValidator(LoginValidator loginValidator) {
		this.loginValidator = loginValidator;
	}
	
	@GetMapping
	public String loginForm(Model model) { // model : view에 전달될 데이터 객체
		model.addAttribute(new User());
		System.out.println(model);
		return "login"; // view 이름
	}
	
	@PostMapping
	public ModelAndView login (User user, BindingResult bresult, HttpSession session) { // session 객체 추가
		ModelAndView mav = new ModelAndView();
		loginValidator.validate(user, bresult);
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		try {
			User userinfo = shopService.getUser(user.getUserid());
			if(user.getPassword().equals(userinfo.getPassword())) {
				session.setAttribute("loginUser", userinfo);
				mav.setViewName("loginSuccess");
			}else {
				bresult.reject("error.login.password");
				mav.getModel().putAll(bresult.getModel());
				return mav;
			}
		} catch (EmptyResultDataAccessException e) { // DB에 user의 정보가 없는 경우 실행 (Spring JDBC 이용할 경우에만 사용 가능)
			// MyBatis의 경우 사용이 안된다.
			e.printStackTrace();
			bresult.reject("error.login.id");
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		return mav;
	}
}
