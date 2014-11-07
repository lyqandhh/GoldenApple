package cn.gqlife.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class CommunicationController {
	@RequestMapping("login.do")
	protected void login(HttpServletRequest request, HttpServletResponse response) {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		JSONObject json = new JSONObject();  
		json.put("userName", userName);
		json.put("password", password);
		response.setContentType("application/json");
		try {
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
