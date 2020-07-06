package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping("*")// if("*") : 존재하지 않는 나머지 해당 요청을 실행한다.(/item/*.shop)
	public ModelAndView detail(Integer id) {
		ModelAndView mav = new ModelAndView();
		Item item = service.getItem(id);
		System.out.println(item);
		mav.addObject("item", item);
		return mav;
	}
	
	@RequestMapping("create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("item/add");
		return mav;
	}
}
