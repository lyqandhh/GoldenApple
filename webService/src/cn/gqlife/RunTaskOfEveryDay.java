package cn.gqlife;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

public class RunTaskOfEveryDay extends TimerTask {

	public void run() {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String image=String.valueOf((int)(Math.random()*10+1));
		if(GqboApplication.getLogin_image().equals(image)){
			image=String.valueOf((int)(Math.random()*10+1));
		}
		GqboApplication.setLogin_image(image);
		System.out.println(now + " 执行更新: 登陆背景图片切换为"+GqboApplication.getLogin_image());
	}

}
