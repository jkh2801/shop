package controller;

import logic.ShopService;
import util.LoginValidator;

public class LoginController {
	private ShopService shopService;
	private LoginValidator loginValidator;
	
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	public void setLoginValidator(LoginValidator loginValidator) {
		this.loginValidator = loginValidator;
	}
	
	
}
