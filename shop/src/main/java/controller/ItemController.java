package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

@Controller // controller 역할을 수행하는 @Component 객체이다.
@RequestMapping("item") // path에 item으로 들어오는 요청을 처리해준다. (/item/)
public class ItemController {
	
	@Autowired
	private ShopService service;
	
	@RequestMapping("list") // /item/list.shop 실행시 처리
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List<Item> itemList = service.getItemList();
		mav.addObject("itemList", itemList);
		return mav;
	}
	
	@GetMapping("*")// if("*") : 존재하지 않는 나머지 해당 요청을 실행한다.(/item/*.shop)
	public ModelAndView detail(Integer id) {
		ModelAndView mav = new ModelAndView();
		Item item = service.getItem(id);
		System.out.println(item);
		mav.addObject("item", item);
		return mav;
	}
	
	@RequestMapping("create")
	public String create(Model model) {
		model.addAttribute(new Item());
		return "item/add";
	}
	
	@RequestMapping("register") // @Valid : 유효성 검사
	public ModelAndView add(@Valid Item item, BindingResult bresult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/add");
		System.out.println(item);
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		service.itemCreate(item, request);
		mav.setViewName("redirect:/item/list.shop"); // 리다이렉트 방식
		return mav;
	}
	
	@RequestMapping("update") // @Valid : 유효성 검사
	public ModelAndView update(@Valid Item item, BindingResult bresult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/edit");
		System.out.println(item);
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		service.itemUpdate(item, request);
		mav.setViewName("redirect:/item/detail.shop?id="+item.getId());
		return mav;
	}
	
	@GetMapping("delete")
	public ModelAndView delete(Integer id) {
		ModelAndView mav = new ModelAndView();
		service.itemDelete(id);
		mav.setViewName("redirect:/item/list.shop");
		return mav;
	}
}
