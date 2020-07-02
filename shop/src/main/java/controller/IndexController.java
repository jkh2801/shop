package controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

public class IndexController {
	private ShopService shopService;

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	
	@RequestMapping // index.shop 요청시 호출되는 메서드
	public ModelAndView itemList() {
		List <Item> itemList = shopService.getItemList();
		System.out.println(itemList);
		ModelAndView mav = new ModelAndView("index"); // view 이름 : index 설정
		mav.addObject("itemList", itemList); // view으로 전송할 data를 저장
		return mav;
	}

}
