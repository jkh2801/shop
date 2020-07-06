package controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService2;
import logic.User;
import util.UserValidator;

public class UserEntryController {
	private ShopService2 shopService;
	private UserValidator userValidator;
	public void setShopService(ShopService2 shopService) {
		this.shopService = shopService;
	}
	public void setUserValidator(UserValidator userValidator) {
		this.userValidator = userValidator;
	}
	
//	@GetMapping 
//	public String userEntryForm() {
//		return null; // view 이름 // 기본이름으로 지정 : userEentry.jsp가 설정된다.
//	}
//	
//	@ModelAttribute
//	public User getUser() {
//		return new User();
//	}
	
	@GetMapping
	public ModelAndView userEntryForm() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new User());
		System.out.println(mav);
		return mav;
	}
	
	@PostMapping
	public ModelAndView userEntry(User user, BindingResult bindResult) {
		// User : 매개변수에 User 타입이 선언된 경우, 파라미터 값과, User의 set 프로퍼티가 동일한 값을 저장한다.
		ModelAndView mav = new ModelAndView();
		System.out.println("user: "+user);
		System.out.println("bindResult: "+bindResult);
		userValidator.validate(user, bindResult);
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel()); // 각각의 파라미터에 해당되는 에러를 출력
			return mav;
		}
		try {
			shopService.insertUser(user);
			mav.addObject("user", user); // 수정된 user를 받는다.
		} catch (Exception e) {
			e.printStackTrace();
			bindResult.reject("error.duplicate.user"); // 이미 DB에 등록되어 있다.
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		mav.setViewName("userEntrySuccess"); // 해당 url로 이동
		return mav;
	}
	
	@InitBinder
	public void initBinder (WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(binder);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true)); // format 형식으로 들어오면 Date 타입으로 변환
		// true: 입력값이 필수가 아니다. (값을 넣지 않아도 통과된다)
		
	}
}
