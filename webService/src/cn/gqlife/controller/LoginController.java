package cn.gqlife.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
	@RequestMapping("login.do")
	protected String login(HttpServletRequest request, HttpServletResponse response) {
		int image=(int)(Math.random()*10+1);
		request.setAttribute("image", image);
		return "/login";
	}
}
