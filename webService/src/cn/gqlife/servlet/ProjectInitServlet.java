package cn.gqlife.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gqlife.BackgroundService;

public class ProjectInitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("◆◆◆◆◆◆◆◆ 项目启动，开始初始化配置 ◆◆◆◆◆◆◆◆");
		BackgroundService backgroundService = new BackgroundService();
		backgroundService.init();
		System.out.println("◆ 启动后台服务完毕");
		System.out.println("◆◆◆◆◆◆◆◆ 初始化结束");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
