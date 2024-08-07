package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/SignOut")
	public String signout() {
		return "SignOut";
	}
	
	@GetMapping("/attendanceReport")
    public String showAttendanceReport() {
        return "attendanceReport"; 
    }
	
	@GetMapping("/admin")
    public String adminPage() {
        return "adminPage"; 
    }
}
