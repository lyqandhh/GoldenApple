package cn.gqlife.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gqlife.service.ImageService;

public class ImgCropServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 用户经过剪辑后的图片的大小
		Integer x = Integer.parseInt(request.getParameter("x"));
		Integer y = Integer.parseInt(request.getParameter("y"));
		Integer w = Integer.parseInt(request.getParameter("w"));
		Integer h = Integer.parseInt(request.getParameter("h"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		//获取原显示图片路径
		String oldImgPath = request.getParameter("oldImgPath");
		//WEB应用程序根路径
		String webAppPath = getServletContext().getRealPath("/");
		//源图像路径
		String imgName =  webAppPath + oldImgPath;	
		//新图像路径
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String imgFileId = formatter.format(new Date());
		String imgFileExt=oldImgPath.substring(oldImgPath.lastIndexOf("."), oldImgPath.length());
		String newPath=webAppPath+"head"+"\\"+userId+"\\"+imgFileId+imgFileExt;
		//之前上传的图片路径
		webAppPath += oldImgPath;
		//进行剪切图片操作
		try {
			ImageCut.abscut(imgName,newPath, x,y,w, h);
			ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			ImageService imageService = (ImageService) ac.getBean("imageService");
			boolean bool=imageService.setImageHead(userId,1, 1, "/head"+"/"+userId+"/"+imgFileId+imgFileExt);
			String path="";
			if(bool){
				path = "/iframe/setHead.jsp?key="+userId;
			}else{
				path = "/iframe/setHead.jsp?key="+userId;;
			}
			request.getRequestDispatcher(path).forward(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = oldImgPath;
		System.out.println("imgCrop: " + path);
	}
	@Test
	public void test(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String imgFileId = formatter.format(new Date());
		String a="201410151037381413340658866.jpg";
		String imgFileExt=a.substring(a.lastIndexOf("."), a.length());
		System.out.println(imgFileId+imgFileExt);
	}

}
