package cn.gqlife;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class BackgroundService {

	public void init() {

		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

		long zeroTimeDifference = 0; // 每天00:00定时任务时间差
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +1);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		zeroTimeDifference = calendar.getTimeInMillis();
		long nowTime = (new java.util.Date()).getTime();
		if (zeroTimeDifference - nowTime >= 0) {
			Timer timer = new Timer();
			timer.schedule(new RunTaskOfEveryDay(), zeroTimeDifference - nowTime, 24 * 60 * 60 * 1000);
			System.out.println(now + " " + "定时器启动" + " " + "每天任务" + ":" + "服务：更新登陆页面" + (zeroTimeDifference - nowTime) + "毫秒后开始运行！");
		}
	}
}
