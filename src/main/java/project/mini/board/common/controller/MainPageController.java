package project.mini.board.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
	@GetMapping("/")
	public String getMainPageView() {
		return "/main/not-login";
		// return "/main/login";
	}
}
