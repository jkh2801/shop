package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import exception.CartEmptyException;
import logic.Cart;
import logic.Item;
import logic.ItemSet;
import logic.ShopService;

@Controller // controller 역할을 수행하는 @Component 객체이다.
@RequestMapping("cart") // path에 cart으로 들어오는 요청을 처리해준다. (/cart/)
public class CartController {

	@Autowired
	private ShopService service;
	
	@RequestMapping("cartAdd")
	public ModelAndView add(Integer id, Integer quantity, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Item item = service.getItem(id);
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart == null) {
			cart = new Cart();
			session.setAttribute("CART", cart);
		}
		boolean state = true;
		for(ItemSet i : cart.getItemSetList()) {
			if(i.getItem().getId().equals(item.getId())) {
				i.setQuantity(i.getQuantity()+quantity);
				state = false;
				break;
			}
		}
		if(state) cart.push(new ItemSet(item, quantity));
		mav.addObject("message", item.getName()+" : "+quantity+" 개 장바구니 추가");
		mav.addObject("cart", cart);
		return mav;
	}
	
	@RequestMapping("cartDelete")
	public ModelAndView cartDelete(int index, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		cart.getItemSetList().remove(index);
		mav.addObject("message", "해당 상품이 삭제되었습니다.");
		mav.addObject("cart", cart);
		return mav;
	}
	
	@RequestMapping("check*")
	public ModelAndView check(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@RequestMapping("cart*")
	public ModelAndView detail(HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart == null || cart.getItemSetList().size() == 0) {
			throw new CartEmptyException("장바구니에 상품이 없습니다.","../item/list.shop");
		}
		mav.addObject("cart", cart);
		return mav;
	}
	
}
