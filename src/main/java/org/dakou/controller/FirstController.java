package org.dakou.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@Controller
@RequestMapping("r")
public class FirstController {
	@RequestMapping("r1")
	public String c1(){
		System.out.println("c1");
		return "index";
	}
	@RequestMapping("r2")
	@ResponseBody
	//No converter found for return value of type: class java.util.HashMap
	public Map<String, Object> c2(){
		System.out.println("c2");
		Map<String, Object>result=new HashMap<>();
		result.put("username", "dakou");
		return result;
	}
	@RequestMapping("r3")
	@ResponseBody//直接
	public String c3(){
		System.out.println("c3");
		return "index";
	}
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.FOUND)
	public void login(){
		return;
	}
}
