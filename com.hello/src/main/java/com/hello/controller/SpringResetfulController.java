package com.hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringResetfulController {

	@RequestMapping(path = "/user/test", method = RequestMethod.GET)
	public @ResponseBody  String  hellWOrld() {
         return "dfdfssssdfsdfsf999999";
	}
}
