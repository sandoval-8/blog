package com.blog.bloggui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(path = "/home2")
	public String getHome(Model model) {
		model.addAttribute("saludo", "Hola home");
		return "home";
	}
	
}
