package springboot.angular.nonpm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import springboot.angular.nonpm.model.User;

@Service
public class LoginService {

	//Just to simulate data that usually would be persisted externally
	private List<User> users;
	
	public LoginService(){
		users = new ArrayList<>();
		users.add(new User(1001, "Ricardo", "Sample_1"));
		users.add(new User(1002, "Jon","Sample_2"));
		users.add(new User(1003, "Chiu","Sample_3"));
	}
	
	public User getUser(User user){
		for (User existingUser : users){
			if(existingUser.getName().equals(user.getName()) && 
					(existingUser.getPassword().equals(user.getPassword()))){
				
				return existingUser;
			}
		}
		//user doesn't exists
		return new User(0, "notfound", "notfound");
	}
}
