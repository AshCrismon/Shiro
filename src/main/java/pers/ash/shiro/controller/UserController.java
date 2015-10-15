package pers.ash.shiro.controller;

import java.util.Collections;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.service.UserService;

@Controller
@RequestMapping("/controller/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JSONObject message;

	@RequestMapping(value = "/findAllUsers", method = RequestMethod.GET)
//	@RequiresRoles(value = { "admin" })
	public @ResponseBody
	String findAllUsers() {
		List<User> users = userService.findAllUsers();
		return renderData(users);
	}

	@RequestMapping(value = "/findRoles/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String findRoles(@PathVariable("id") String userId) {
		List<Role> roles = userService.findRoles(userId);
		return renderData(roles);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	String createUser(@Validated User user, BindingResult result){
		userService.createUser(user);
		return renderMessage(result);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	String updateUser(User user){
		userService.updateUser(user);
		return renderMessage();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	String deleteUser(@PathVariable("id") String userId){
		userService.deleteUser(userId);
		return renderMessage();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String findUser(@PathVariable("id") String userId){
		User user = userService.findByUserId(userId);
		return renderData(user);
	}
	
	public String renderMessage(BindingResult result){
		message.put("success", "true");
		if(result != null && result.hasErrors()){
			List<ObjectError> errors = result.getAllErrors();
			for(int i = 0; i < errors.size(); i++){
				message.accumulate("msg", errors.get(i).getDefaultMessage());
			}
			message.put("success", "false");
		}
		return message.toString();
	}
	
	public String renderData(Object object){
		String success = (object == null ? "false" : "true");
		Object data = (object == null ? Collections.EMPTY_LIST : object);
		message.put("success", success);
		message.put("data", data);
		return message.toString();
	}
	
	public String renderMessage(){
		return renderMessage(null);
	}
	
}
