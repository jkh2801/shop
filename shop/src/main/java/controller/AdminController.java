package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;

@Controller // controller 역할을 수행하는 @Component 객체이다.
@RequestMapping("admin") // path에 cart으로 들어오는 요청을 처리해준다. (/cart/)
public class AdminController {
	
	@Autowired
	private ShopService service;
	
	@GetMapping("list")
	public ModelAndView list(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List <User> list = service.getlistAll();
		System.out.println(list);
		mav.addObject("list", list);
		return mav;
	}
}
