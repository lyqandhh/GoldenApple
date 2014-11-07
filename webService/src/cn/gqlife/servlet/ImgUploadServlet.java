package cn.gqlife.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;


public class ImgUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String IMGROOT = "uploads\\";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String userWebAppPath = getWebAppPath();
		/**检查是否有图片上传文件夹*/
		checkImageDir(userWebAppPath);	
		
		/**图片上传的相对路径*/
		String imgUploadPath = null;
		String imgWebAppPath = null;
		/**图片后缀*/
		String imgFileExt = null;
		
		/**图片名称:以当前日期*/
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String imgFileId = formatter.format(new Date());
		try {
			
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(getServletConfig(), request, response);
			smartUpload.upload();
			//文件个数
			int fileCounts =  smartUpload.getFiles().getCount();	
		
			for (int i = 0; i <fileCounts; i++) {
				com.jspsmart.upload.File myFile = smartUpload.getFiles().getFile(i);
				
				if (!myFile.isMissing()) {
					
					imgFileExt = myFile.getFileExt();
					//组装图片真实名称
					imgFileId += i + System.currentTimeMillis() + "." + imgFileExt;
					
					//图片生成路径
					imgWebAppPath = userWebAppPath + imgFileId;
					
					//生成图片文件
					myFile.saveAs(imgWebAppPath);
					//图片的相对路径
					imgUploadPath = IMGROOT + imgFileId;
					//检查图片大小
					BufferedImage src = ImageIO.read(new File(imgWebAppPath)); // 读入文件						 
					int imgSrcWidth = src.getWidth(); // 得到源图宽							 
					if(imgSrcWidth>580){
						String result="{\"path\":\""+1+"\"}";
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(result);
						return;
					}
				}
				
			}
			System.out.println(imgFileId);
			String result="{\"path\":\""+imgFileId+"\"}";
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch(Exception ex){
			String result="{\"path\":\""+imgFileId+"\"}";
			response.setCharacterEncoding("UTF-8");
			result="{\"path\":\""+1+"\"}";
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		}
	}
	
	private String getWebAppPath(){
		String webAppPath = this.getServletContext().getRealPath("/");		
		String userWebAppPath = webAppPath+IMGROOT;
		return userWebAppPath;
	}

	private void checkImageDir(String userWebAppPath) {		
		 File file = new File(userWebAppPath);
		 if(!file.exists()){
			 file.mkdir();
		 }
	}

}
