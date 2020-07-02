package util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import logic.User;

public class UserValidator implements Validator{
	
	public boolean supports(Class<?> clazz) { // 유효성 검정을 위한 객체 자료형 선택
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;
		String group = "error.required";
		if(user.getUserid() == null || user.getUserid().length() == 0) {
			errors.rejectValue("userid", group);
		}
		if(user.getPassword() == null || user.getPassword().length() == 0) {
			errors.rejectValue("password", group);
		}
		if(user.getUsername() == null || user.getUsername().length() == 0) {
			errors.rejectValue("username", group);
		}
		if(user.getPhoneno() == null || user.getPhoneno().length() == 0) {
			errors.rejectValue("phoneno", group);
		}
		if(user.getPostcode() == null || user.getPostcode().length() == 0) {
			errors.rejectValue("postcode", group);
		}
		if(user.getAddress() == null || user.getAddress().length() == 0) {
			errors.rejectValue("address", group);
		}
		if(user.getEmail() == null || user.getEmail().length() == 0) {
			errors.rejectValue("email", group);
		}
		if(errors.hasErrors()) errors.reject("error.input.user"); // 글로벌 오류
		// 오류 발생
	}
	
}
