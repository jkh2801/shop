package controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService2;

public class DetailController {
	private ShopService2 shopService;

	public void setShopService(ShopService2 shopService) {
		this.shopService = shopService;
	}
	
	@RequestMapping // detail.shop 요청시 호출되는 메서드
	public ModelAndView detail(Integer id) { // 매개변수의 이름은 파라미터 이름이 같을 경우 값이 설정된다.
		Item item = shopService.getItemById(id);
		ModelAndView mav = new ModelAndView(); // view 이름 : detail 설정
		mav.addObject("item", item); // view으로 전송할 data를 저장
		return mav;
	}
}
