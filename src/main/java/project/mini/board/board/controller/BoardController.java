package project.mini.board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@GetMapping
	public String boardList() {
		return "/html/index";
	}
}
