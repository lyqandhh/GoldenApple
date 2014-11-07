package cn.gqlife.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gqlife.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView index() {
		return new ModelAndView();
	}
	/**
	 * 修改密码动作
	 * @param request
	 * @param response
	 * @return 返回true Or false ，用于ajax回调
	 */
	@RequestMapping(value = "setPassword.do", headers = "Accept=application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public boolean setPassword(HttpServletRequest request, HttpServletResponse response) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserServiceImpl userService=(UserServiceImpl)ac.getBean("userService");
		return userService.setPassword(request);
	}
	/**
	 * 修改密码页面跳转动作
	 * @param request
	 * @param response
	 * @return 修改密码页面 password.jsp
	 */
	@RequestMapping("password.do")
	protected String password(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("menuActive", 3);
		return "/password";
	}
}
