package controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.Sale;
import logic.SaleItem;
import logic.ShopService;
import logic.User;

@Controller // controller 역할을 수행하는 @Component 객체이다.
@RequestMapping("user") // path에 cart으로 들어오는 요청을 처리해준다. (/cart/)
public class UserController {

	@Autowired
	private ShopService service;
	
	@GetMapping("*")
	public ModelAndView detail() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new User());
		return mav;
	}
	
	@PostMapping("userEntry")
	public ModelAndView userEntry(@Valid User user, BindingResult bresult ) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.input.user");
			return mav;
		}
		try {
			service.userInsert(user);
			mav.setViewName("redirect:login.shop");
		} catch (Exception e) {
			e.printStackTrace();
			bresult.reject("error.duplicate.user");
			mav.getModel().putAll(bresult.getModel());
		}
		return mav;
	}
	
	@PostMapping("login")
	public ModelAndView login(@Valid User user, BindingResult bresult, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		System.out.println(bresult);
		if(bresult.hasErrors()) {
			bresult.reject("error.input.user");
			return mav;
		}
		try {
			User userinfo = service.getUserByID(user.getUserid());
			if(user.getPassword().equals(userinfo.getPassword())) {
				session.setAttribute("loginUser", userinfo);
				mav.setViewName("redirect:main.shop");
			}else bresult.reject("error.login.password");
		} catch (Exception e) {
			e.printStackTrace();
			bresult.reject("error.login.id");
		}
		
		return mav;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.shop";
	}
	
	@RequestMapping("main")
	public String loginCheck(HttpSession session) {
		return null;
	}
	
	@RequestMapping("mypage")
	public ModelAndView mypageCheck(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = service.getUserByID(id);
		List <Sale> list = service.getlist(id);
		for(Sale s : list) {
			List <SaleItem> saleitemlist = service.getsaleitemlist(s.getSaleid());
			for(SaleItem si : saleitemlist) {
				Item item = service.getItem(Integer.parseInt(si.getItemid()));
				si.setItem(item);
			}
			s.setitemList(saleitemlist);
		}
		mav.addObject("user", user);
		mav.addObject("salelist", list);
		return mav;
	}
}
