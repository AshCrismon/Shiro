package pers.ash.shiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/findAllUsers")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<User> findAllUsers() {
		return userService.findAllUsers();
	}

	@RequestMapping(value = "/findRoles/{id}", method = RequestMethod.GET)
	public @ResponseBody
	List<Role> findRoles(@PathVariable("id") String userId) {
		return userService.findRoles(userId);
	}
}
