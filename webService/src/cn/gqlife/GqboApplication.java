package cn.gqlife;

public class GqboApplication {
	// 登陆页面背景
	private static String login_image =String.valueOf((int)(Math.random()*10+1));

	public static String getLogin_image() {
		return login_image;
	}

	public static void setLogin_image(String login_image) {
		GqboApplication.login_image = login_image;
	}
	
}
