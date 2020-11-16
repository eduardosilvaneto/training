package com.training.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
	@GetMapping("/print")
	public String print() {
		String message="";
		message="tudo bem";
		return message;
	}
}
