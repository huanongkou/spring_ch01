package org.dakou.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Map model){
		System.out.println("登陆成功");
		model.put("username", "dakou2");
		return "index";
	}
}
