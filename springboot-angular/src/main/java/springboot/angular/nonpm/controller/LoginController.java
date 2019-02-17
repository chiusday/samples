package springboot.angular.nonpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import springboot.angular.nonpm.model.User;
import springboot.angular.nonpm.service.LoginService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public User login(@RequestBody User user){
		return loginService.getUser(user);
	}
}
