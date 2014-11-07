package cb.gq.web.utils;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cb.gq.web.bll.HttpContext;
import cb.gq.web.model.space.AreaBean;
import cb.gq.web.model.space.CityBean;
import cb.gq.web.model.space.MemberLoginBean;

/**
 * 
 * @author Administrator
 * 
 */
public class CopyOfFunHelper {

	/**
	 * 返回带端口的绝对地址 是否添加
	 * 
	 * @param add
	 *            是否????添加
	 * @return
	 */
	public static String getBasePath() {
		HttpContext context = CacheManage.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		String path = request.getContextPath() + "/";

		String basePath = request.getScheme() + "://" + request.getServerName();
		// 如果不是80端口，将输出端口??
		if (request.getServerPort() != 80)
			basePath += ":" + request.getServerPort();
		basePath += path;

		return basePath;
	}

	/**
	 * 获取请求的参数，如果为空，将输出默认??
	 * 
	 * @param name
	 *            参数??
	 * @param defValue
	 *            默认??
	 * @return
	 */
	public static String getParameter(String name, String defValue) {
		HttpContext context = CacheManage.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		String parString = request.getParameter(name);
		// 如果为空，返回默认??
		if (parString == null || parString.length() == 0)
			return defValue;
		return parString;
	}

	/**
	 * 设置URL，用于输出点击URL菜单（只适用于商家空间）
	 * 
	 * @param href
	 * @return
	 */

	/**
	 * 设置URL，用于输出点击URL菜单（只适用于商家空间）
	 * 
	 * @param href
	 * @return
	 */
	public static String SetUrl(String href) {
		String newurl = CopyOfFunHelper.getBasePath() + "/space" + href;
		return String.format(" href='%s' %s ", newurl,
				includeUrl(newurl, "class='current'"));
	}

	/**
	 * 设置URL，用于输出点击URL菜单
	 * 
	 * @param sys
	 * @param href
	 * @param add
	 * @return
	 */
	public static String SetUrl(String sys, String href) {
		// 生成基础地址
		String newurl = CopyOfFunHelper.getBasePath() + "/" + sys + href;
		return String.format(" href='%s' %s ", newurl,
				includeUrl(newurl, "class='current'"));
	}

	public static String SetUrl(String sys, String href, String classname) {
		// 生成基础地址
		String newurl = CopyOfFunHelper.getBasePath() + "/" + sys + href;
		return String.format(" href='%s' %s ", newurl,
				includeUrl(newurl, "class='" + classname + "'"));
	}

	/**
	 * 是否包含设置的URL,如果包括，返回内??
	 * 
	 * @param url
	 * @param content
	 * @return
	 */
	public static String includeUrl(String url, String content) {
		if (includeUrl(url))
			return content;
		return "";
	}

	/**
	 * 当前请求地址是否包含设置的URL
	 * 
	 * @param url
	 * @return
	 */
	public static Boolean includeUrl(String url) {
		HttpContext context = CacheManage.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		// 当前请求地址
		String reqUri = request.getRequestURL().toString();
		if (request.getQueryString() != null
				&& request.getQueryString().length() > 0)
			reqUri += "?" + request.getQueryString();
		// 如果包含返回true，否则返回false
		if (reqUri.indexOf(url) != -1)
			return true;
		else
			return false;
	}

	/**
	 * ?根据名字获取cookie ?
	 * 
	 * @param request
	 *            ?
	 * @param name
	 *            cookie名字 ?
	 * @return ?
	 */
	public static Cookie getCookieByName(String name) {
		// 通过名称查找COOKIE中的对象 
		if(null != CacheManage.getCurrentContext() && null != CacheManage.getCurrentContext().getRequest()){
		Cookie[] cookies = CacheManage.getCurrentContext().getRequest().getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name))
					return cookie;
			}
		}
		}
		return null;
	}

	/**
	 * 通过NAME获取Cookie的??
	 * 
	 * @param name
	 *            键??
	 * @param isEncryption
	 *            是否????加密
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getCookieValueByName(String name, boolean isEncryption)
			throws UnsupportedEncodingException {
		Cookie cookie = getCookieByName(name);
		if (null != cookie) {
			return java.net.URLDecoder.decode(cookie.getValue(), "gb2312");
		}
		return "";
	}

	/**
	 * 将cookie封装到Map里面 ?
	 * 
	 * @param request
	 * @return ?
	 */
	public static Map<String, Cookie> ReadCookieMap() {

		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = CacheManage.getCurrentContext().getRequest()
				.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 设置Cookie
	 * 
	 * @param name
	 * @param value
	 * @param isEncryption
	 * @throws Exception
	 */
	public static void setCookie(String name, String value, String domain,
			boolean isEncryption) throws Exception {
		String setvalue = java.net.URLEncoder.encode(value, "utf-8");
		Cookie cookie = new Cookie(name, setvalue);
		cookie.setMaxAge(60 * 60 * 24 * 6);
		cookie.setDomain(domain);
		addCookie(cookie);
	}
	
	/**
	 * 添加到COOKIE
	 * 
	 * @param cookie
	 * @throws Exception
	 */
	public static void addCookie(Cookie cookie) throws Exception {
		CacheManage.getCurrentContext().getResponse().addCookie(cookie);
	}

	/**
	 * 删除COOKIE
	 * 
	 * @param name
	 * @throws Exception
	 */
	public static void RemoveCookie(String name) throws Exception {
		Cookie cookie = getCookieByName(name);
		if (cookie != null) {
			cookie.setValue("");
			cookie.setMaxAge(0);
			addCookie(cookie);
		}
	}

	public static boolean loginNetFlag() throws Exception {
		Cookie cookie = getCookieByName("Token");
		if (null != cookie && cookie.getValue() != null
				&& cookie.getValue().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前用户session
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MemberLoginBean getLoginMemberBean() throws Exception {
		try {
			return (MemberLoginBean) CacheManage
					.getObjectFromSession("gqSpaceUser");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取当前用户session
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getGqSapceId() throws Exception {
		try {
			return (String) CacheManage.getObjectFromSession("gqSpaceId");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取当前用户的时间戳session(动态信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getShareTimeStamp() throws Exception {
		try {
			return (String) CacheManage.getObjectFromSession("shareTimeStamp");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取当前用户的时间戳session(标签信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getShareTimeStampTag() throws Exception {
		try {
			return (String) CacheManage
					.getObjectFromSession("shareTimeStampTag");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到当前
	 */
	public final static String getYear() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	/**
	 * 得到当前
	 */
	public final static String getMonth() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 得到当前??
	 */
	public final static String getYearMonth() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.YEAR))
				+ String.valueOf(cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 从数据库获取到的日期转化为字符串
	 */
	public static String transDateToString(Date date) {
		if (null == date) {
			return "";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getDateTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getDateTimemsString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getDateTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期时间！到毫秒
	 */
	public final static String getDateMilliTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		return f.format(new Date());
	}

	/*
	 * 取得系统时间！
	 */
	public final static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
		return f.format(new Date());
	}

	/*
	 * 取得系统时间！
	 */
	public final static String getTimes() {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期!
	 */
	public final static String getDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(new Date());
	}

	// html-text之间的转??-用于多行文本??
	public static String insteadCode(String str, String regEx, String code) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		String s = m.replaceAll(code);
		return s;
	}

	// 把内容替换成html格式，同时过滤掉部分html的标??-保存
	public static String toHTML(String sourcestr) {
		String targetstr = "";
		if (sourcestr != null) {
			targetstr = insteadCode(sourcestr, ">", "&gt;");
			targetstr = insteadCode(targetstr, "<", "&lt;");
			targetstr = insteadCode(targetstr, "\n", "<br>");
			targetstr = insteadCode(targetstr, " ", "&nbsp;");
			targetstr = insteadCode(targetstr, "\" ", "“");
		}
		return targetstr;
	}

	// 输出
	public static String toTEXT(String sourcestr) {
		String targetstr = "";
		if (sourcestr != null) {
			targetstr = insteadCode(sourcestr, "&gt;", ">");
			targetstr = insteadCode(targetstr, "&lt;", "<");
			targetstr = insteadCode(targetstr, "<br>", "\n");
			targetstr = insteadCode(targetstr, "&nbsp;", " ");
		}
		return targetstr;
	}

	/**
	 * 转换特殊字符并返回??合数据库查询的字符串
	 * 
	 * @param str
	 * @return
	 */
	public final static String sqlStr(String str) {
		String sTmp = "";
		if (str == null) {
			sTmp = "";
		} else {
			sTmp = strReplace(str, "\\", "\\\\");
			sTmp = strReplace(sTmp, "'", "\\'");
		}
		return sTmp;
	}

	public final static String strReplace(String line, String ch, String rep) {
		if (line != null && !"".equals(line)) {
			int i = line.indexOf(ch);
			if (i == -1)
				return line;
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(line.substring(0, i) + rep);
			if (i + ch.length() < line.length())
				strBuf.append(strReplace(
						line.substring(i + ch.length(), line.length()), ch, rep));
			return strBuf.toString();
		}
		return line;
	}

	/**
	 * 从数据库获取到的日期转化为字符串
	 */
	public static String transDateToStringWithSecond(Timestamp ts) {
		if (ts == null) {
			return null;
		} else {
			return transDateToString(new Date(ts.getTime()));
		}
	}

	// 把对象转换成String
	public final static String toString(Object obj) {
		return ((obj == null) || "".equals(obj) || "null".equals(obj)) ? ""
				: obj.toString().trim();
	}

	/**
	 * 过滤html标签函数
	 */
	public final static String Html2Text(String inputString) {
		String htmlStr = toString(inputString).intern(); // 含html标签的字符串
		if (htmlStr == "") {
			return "";
		}
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{??script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{??style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return toHTML(textStr);// .replaceAll("&nbsp;", "").replaceAll("<br>",
								// "").replaceAll("</br>", "");返回文本字符??
	}

	/**
	 * 过滤html标签函数
	 */
	public final static String Html2TextOnly(String inputString) {
		String htmlStr = toString(inputString).intern(); // 含html标签的字符串
		if (htmlStr == "") {
			return "";
		}
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{??script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{??style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// .replaceAll("&nbsp;", "").replaceAll("<br>",
						// "").replaceAll("</br>", "");//返回文本字符??
	}

	public final static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 把一个文件转化为字节
	 * 
	 * @param file
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = new FileInputStream(file);
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) // 当文件的长度超过了int的最大值
			{
				System.out.println("this file is max ");
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				System.out.println("file length is error");
				return null;
			}
			is.close();
		}
		return bytes;
	}

	/*
	 * 发送图片-----此方法发送方式为HttpPost
	 */
	public static String sendImage(byte[] imageData, String gqId) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		StringBuilder b = new StringBuilder();
		try {
			if (gqId != null && gqId.length() > 1) {
				URL url = new URL(PropertiesUtils.getValue("picuploadpath")
						+ gqId);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setConnectTimeout(130000);
				con.setReadTimeout(130000);
				/* 允许Input、Output，不使用Cache */
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				/* 设置传送的method=POST */
				con.setRequestMethod("POST");
				/* setRequestProperty */
				con.setRequestProperty("Connection", "Keep-Alive");
				con.setRequestProperty("Charset", "GBK");

				con.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				/* 设置DataOutputStream */
				DataOutputStream ds = new DataOutputStream(
						con.getOutputStream());
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; "
						+ "name=\"file1\";filename=\"" + "aaa.jpg" + "\"" + end);
				ds.writeBytes(end);

				/* 取得文件的FileInputStream */
				// FileInputStream fStream = new FileInputStream(src);
				ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

				/* 设置每次写入1024bytes */
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];

				int length = -1;
				/* 从文件读取数据至缓冲区 */
				while ((length = bais.read(buffer)) != -1) {
					/* 将资料写入DataOutputStream中 */
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

				/* close streams */
				bais.close();
				ds.flush();

				/* 取得Response内容 */
				InputStream is = con.getInputStream();
				int ch;
				byte[] temp = new byte[512];
				b = new StringBuilder();
				while ((ch = is.read(temp, 0, 512)) != -1) {
					b.append(new String(temp, 0, ch));
				}
				ds.close();
			}
		} catch (Exception e) {
			// Log.e("上传报错", e.getMessage());
			return null;
		}
		return b.toString();
	}

	public static boolean JudgeProductFileType(String fileName) {
		boolean flag = false;
		String types = PropertiesUtils.getValue("picturetype"); //
		String[] typeArr = types.split(",");
		if (fileName != null && fileName.length() > 0) {
			fileName = fileName.toLowerCase();
			for (int i = 0; i < typeArr.length; i++) {
				if (fileName.equals(typeArr[i])) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}
	public static boolean JudgeCoverPicType(String fileName) {
		boolean flag = false;
		String types = PropertiesUtils.getValue("coverorbgpictype"); //
		String[] typeArr = types.split(",");
		if (fileName != null && fileName.length() > 0) {
			fileName = fileName.toLowerCase();
			for (int i = 0; i < typeArr.length; i++) {
				if (fileName.equals(typeArr[i])) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}
	/**
	 * 
	 * 
	 * @param fileName
	 * @return true
	 */
	public static int GetProductMaxFileSize() {
		int size = 1024 * 1024;
		String propicsize = PropertiesUtils.getValue("picturesize"); //
		size = Integer.parseInt(propicsize);
		return size;
	}
	/**
	 * 
	 * 
	 * @param fileName
	 * @return true
	 */
	public static int GetCoverMaxFileSize() {
		int size = 1024 * 1024;
		String propicsize = PropertiesUtils.getValue("coverorbgsize"); //
		size = Integer.parseInt(propicsize);
		return size;
	}
	public static String getAreaCity(String cityId){
		String cityName="中山市";
		Map<String,String> map=new HashMap<String,String>();
		map.put("01","东区"); 
		map.put("02","石岐区");
		map.put("03","西区");
		map.put("04","南区");
		map.put("05","火炬开发区");
		map.put("06","五桂山区");
		map.put("07","港口镇");
		map.put("08","沙溪镇");
		map.put("09","小榄镇");
		map.put("10","古镇");
		map.put("11","东升镇");
		map.put("12","南头镇");
		map.put("13","阜沙镇");
		map.put("14","黄埔镇");
		map.put("15","三角镇");
		map.put("16","民众镇");
		map.put("17","东凤镇");
		map.put("18","横栏镇");
		map.put("19","大涌镇");
		map.put("20","三乡镇");
		map.put("21","南朗镇");
		map.put("22","坦洲镇");
		map.put("23","神湾镇");
		map.put("24","板芙镇");
		if(cityId!=null && !cityId.equals("00") && cityId.length()==2){
			cityName=map.get(cityId);
		}
		return cityName;
	}
	public static String getBusinessArea(String cityId){
		String cityName="";
		Map<String,String> map=new HashMap<String,String>();
		map.put("0100","东区");
		map.put("0101","假日广场");
		map.put("0102","库充一带");
		map.put("0103","中山海关");
		map.put("0104","竹苑周边");
		map.put("0105","远洋城");
		map.put("0106","京华利和");
		map.put("0107","白沙湾");
		map.put("0108","东裕路");
		map.put("0109","利和广场");
		map.put("0200","石岐区");
		map.put("0201","大信新都汇");
		map.put("0202","逢源商业街");
		map.put("0203","孙文步行街");
		map.put("0204","峰华28广场");
		map.put("0205","泰安路");
		map.put("0206","兴中广场");
		map.put("0300","西区");
		map.put("0301","歧江公园");
		map.put("0302","富华商圈");
		map.put("0303","黄金广场");
		map.put("0304","翠景商业街");
		map.put("0400","南区");
		map.put("0401","万科一带");
		map.put("0402","悦来南路");
		map.put("0403","南下新码头");
		map.put("0500","火炬开发区");
		map.put("0501","张家边");
		map.put("0502","中山港");
		map.put("0600","五桂山区");
		map.put("0601","长命水广药");
		map.put("0700","港口镇");
		map.put("0701","美景花园");
		map.put("0702","港口市场");
		map.put("0800","沙溪镇");
		map.put("0801","星宝");
		map.put("0900","小榄镇");
		map.put("0901","大信顺昌");
		map.put("0902","永宁");
		map.put("0903","小榄");
		map.put("1000","古镇");
		map.put("1001","古镇");
		map.put("1002","国贸");
		map.put("1100","东升镇");
		map.put("1200","南头镇");
		map.put("1300","阜沙镇");
		map.put("1400","黄埔镇");
		map.put("1500","三角镇");
		map.put("1600","民众镇");
		map.put("1700","东凤镇");
		map.put("1701","名扬世纪广场");
		map.put("1800","横栏镇");
		map.put("1900","大涌镇");
		map.put("2000","三乡镇");
		map.put("2001","雅居乐酒店");
		map.put("2002","华丰片区");
		map.put("2003","三乡市场");
		map.put("2004","顺昌广场");
		map.put("2100","南朗镇");
		map.put("2200","坦洲镇");
		map.put("2300","神湾镇");
		map.put("2400","板芙镇");
		if(cityId!=null && !cityId.equals("0000") && cityId.length()==4){
			cityName=map.get(cityId);
		}
		return cityName;
	}
	public static List<CityBean> getCityAreaList(){
		List<CityBean> list =new ArrayList<CityBean>();
		CityBean bean=new CityBean();
		bean.setCityId("01");
		bean.setCityName("东区");
		list.add(bean);
		bean=new CityBean(); 
		bean.setCityId("02");
		bean.setCityName("石岐区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("03");
		bean.setCityName("西区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("04");
		bean.setCityName("南区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("05");
		bean.setCityName("火炬开发区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("06");
		bean.setCityName("五桂山区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("07");
		bean.setCityName("港口镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("08");
		bean.setCityName("沙溪镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("09");
		bean.setCityName("小榄镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("10");
		bean.setCityName("古镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("11");
		bean.setCityName("东升镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("12");
		bean.setCityName("南头镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("13");
		bean.setCityName("阜沙镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("14");
		bean.setCityName("黄埔镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("15");
		bean.setCityName("三角镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("16");
		bean.setCityName("民众镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("17");
		bean.setCityName("东凤镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("18");
		bean.setCityName("横栏镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("19");
		bean.setCityName("大涌镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("20");
		bean.setCityName("三乡镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("21");
		bean.setCityName("南朗镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("22");
		bean.setCityName("坦洲镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("23");
		bean.setCityName("神湾镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityId("24");
		bean.setCityName("板芙镇");
		list.add(bean);
		return list;
	}
	public static List<CityBean> getBusinessAreaList(){
		List<CityBean> list =new ArrayList<CityBean>();
		CityBean bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0100");
		bean.setCityName("东区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0101");
		bean.setCityName("假日广场");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0102");
		bean.setCityName("库充一带");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0103");
		bean.setCityName("中山海关");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0104");
		bean.setCityName("竹苑周边");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0105");
		bean.setCityName("远洋城");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0106");
		bean.setCityName("京华利和");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0107");
		bean.setCityName("白沙湾");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0108");
		bean.setCityName("东裕路");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0109");
		bean.setCityName("利和广场");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("02");
		bean.setCityId("0200");
		bean.setCityName("石岐区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0201");
		bean.setCityName("大信新都汇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0202");
		bean.setCityName("逢源商业街");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0203");
		bean.setCityName("孙文步行街");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0204");
		bean.setCityName("峰华28广场");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0205");
		bean.setCityName("泰安路");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0206");
		bean.setCityName("兴中广场");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("03");
		bean.setCityId("0300");
		bean.setCityName("西区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0301");
		bean.setCityName("歧江公园");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0302");
		bean.setCityName("富华商圈");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0303");
		bean.setCityName("黄金广场");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0304");
		bean.setCityName("翠景商业街");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("04");
		bean.setCityId("0400");
		bean.setCityName("南区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("04");
		bean.setCityId("0401");
		bean.setCityName("万科一带");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("04");
		bean.setCityId("0402");
		bean.setCityName("悦来南路");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("04");
		bean.setCityId("0403");
		bean.setCityName("南下新码头");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("05");
		bean.setCityId("0500");
		bean.setCityName("火炬开发区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("05");
		bean.setCityId("0501");
		bean.setCityName("张家边");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("05");
		bean.setCityId("0502");
		bean.setCityName("中山港");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("06");
		bean.setCityId("0600");
		bean.setCityName("五桂山区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("06");
		bean.setCityId("0601");
		bean.setCityName("长命水广药");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("07");
		bean.setCityId("0700");
		bean.setCityName("港口镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("07");
		bean.setCityId("0701");
		bean.setCityName("美景花园");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("07");
		bean.setCityId("0702");
		bean.setCityName("港口市场");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("08");
		bean.setCityId("0800");
		bean.setCityName("沙溪镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("08");
		bean.setCityId("0801");
		bean.setCityName("星宝");
		list.add(bean);
		/*bean=new CityBean();bean.setCityPid("09");
		bean.setCityId("0900");
		bean.setCityName("小榄镇");
		list.add(bean);*/
		bean=new CityBean();
		bean.setCityPid("09");
		bean.setCityId("0901");
		bean.setCityName("大信顺昌");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("09");
		bean.setCityId("0902");
		bean.setCityName("永宁");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("09");
		bean.setCityId("0903");
		bean.setCityName("小榄");
		list.add(bean);
		/*bean=new CityBean();bean.setCityPid("10");
		bean.setCityId("1000");
		bean.setCityName("古镇");
		list.add(bean);*/
		bean=new CityBean();
		bean.setCityPid("10");
		bean.setCityId("1001");
		bean.setCityName("古镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("10");
		bean.setCityId("1002");
		bean.setCityName("国贸");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("11");
		bean.setCityId("1100");
		bean.setCityName("东升镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("12");
		bean.setCityId("1200");
		bean.setCityName("南头镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("13");
		bean.setCityId("1300");
		bean.setCityName("阜沙镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("14");
		bean.setCityId("1400");
		bean.setCityName("黄埔镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("15");
		bean.setCityId("1500");
		bean.setCityName("三角镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("16");
		bean.setCityId("1600");
		bean.setCityName("民众镇");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("17");
		bean.setCityId("1700");
		bean.setCityName("东凤镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("17");
		bean.setCityId("1701");
		bean.setCityName("名扬世纪广场");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("18");
		bean.setCityId("1800");
		bean.setCityName("横栏镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("19");
		bean.setCityId("1900");
		bean.setCityName("大涌镇");
		list.add(bean);
		bean=new CityBean();bean.setCityPid("20");
		bean.setCityId("2000");
		bean.setCityName("三乡镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2001");
		bean.setCityName("雅居乐酒店");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2002");
		bean.setCityName("华丰片区");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2003");
		bean.setCityName("三乡市场");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2004");
		bean.setCityName("顺昌广场");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("21");
		bean.setCityId("2100");
		bean.setCityName("南朗镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("22");
		bean.setCityId("2200");
		bean.setCityName("坦洲镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("23");
		bean.setCityId("2300");
		bean.setCityName("神湾镇");
		list.add(bean);
		bean=new CityBean();
		bean.setCityPid("24");
		bean.setCityId("2400");
		bean.setCityName("板芙镇");		
		list.add(bean);
		return list;
	}
	public static String getCityAreaName(String cityId){
		String ctiyname="";
		if(cityId!=null && !cityId.isEmpty() && cityId.length()==2){
		for(CityBean bean:getCityAreaList()){
			if(bean!=null && bean.getCityId().equals(cityId)){
				ctiyname=bean.getCityName();
				break;
			}
		}
		}
		return ctiyname;
	}
	public static String getBusinessAreaName(String cityId){
		String ctiyname="";
		if(cityId!=null && !cityId.isEmpty() && cityId.length()==4){
		for(CityBean bean:getBusinessAreaList()){
			if(bean!=null && bean.getCityId().equals(cityId)){
				ctiyname=bean.getCityName();
				break;
			}
		}
		}
		return ctiyname;
	}
	public static AreaBean getCityAreaList(String cityId){
		AreaBean tbean=new AreaBean();
		tbean.setAreaId(cityId);
		tbean.setAreaName(getCityAreaName(cityId));
		List<CityBean> list =new ArrayList<CityBean>();
		if(cityId!=null && !cityId.isEmpty()){
		for(CityBean bean:getBusinessAreaList()){
			if(bean!=null && bean.getCityId()!=null && bean.getCityId().length()==4 && bean.getCityId().substring(0, 2).equals(cityId) && bean.getCityPid().equals(cityId)){
				list.add(bean);
			}
		}
		}
		tbean.setCityBeanList(list);
		return tbean;
	}
	public static List<AreaBean> getAreaList(){
		List<AreaBean> list=new ArrayList<AreaBean>();
		List<CityBean> tlist=new ArrayList<CityBean>();
		AreaBean abean=new AreaBean();
		abean.setAreaId("01");
		abean.setAreaName("东区");
		CityBean bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0101");
		bean.setCityName("假日广场");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0102");
		bean.setCityName("库充一带");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0103");
		bean.setCityName("中山海关");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0104");
		bean.setCityName("竹苑周边");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0105");
		bean.setCityName("远洋城");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0106");
		bean.setCityName("京华利和");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0107");
		bean.setCityName("白沙湾");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0108");
		bean.setCityName("东裕路");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("01");
		bean.setCityId("0109");
		bean.setCityName("利和广场");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("02");
		abean.setAreaName("石岐区");
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0201");
		bean.setCityName("大信新都汇");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0202");
		bean.setCityName("逢源商业街");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0203");
		bean.setCityName("孙文步行街");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0204");
		bean.setCityName("峰华28广场");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0205");
		bean.setCityName("泰安路");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("02");
		bean.setCityId("0206");
		bean.setCityName("兴中广场");
		list.add(abean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("0300");
		abean.setAreaName("西区");
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0301");
		bean.setCityName("歧江公园");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0302");
		bean.setCityName("富华商圈");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0303");
		bean.setCityName("黄金广场");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("03");
		bean.setCityId("0304");
		bean.setCityName("翠景商业街");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("04");
		abean.setAreaName("南区");

		bean=new CityBean();
		bean.setCityPid("04");
		bean.setCityId("0401");
		bean.setCityName("万科一带");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("04");
		bean.setCityId("0402");
		bean.setCityName("悦来南路");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("04");
		bean.setCityId("0403");
		bean.setCityName("南下新码头");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("05");
		abean.setAreaName("火炬开发区");
		bean=new CityBean();
		bean.setCityPid("05");
		bean.setCityId("0501");
		bean.setCityName("张家边");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("05");
		bean.setCityId("0502");
		bean.setCityName("中山港");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("06");
		abean.setAreaName("五桂山区");
		bean=new CityBean();
		bean.setCityPid("06");
		bean.setCityId("0601");
		bean.setCityName("长命水广药");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("07");
		abean.setAreaName("港口镇");

		bean=new CityBean();
		bean.setCityPid("07");
		bean.setCityId("0701");
		bean.setCityName("美景花园");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("07");
		bean.setCityId("0702");
		bean.setCityName("港口市场");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("08");
		abean.setAreaName("沙溪镇");

		bean=new CityBean();
		bean.setCityPid("08");
		bean.setCityId("0801");
		bean.setCityName("星宝");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("09");
		abean.setAreaName("小榄镇");

		bean=new CityBean();
		bean.setCityPid("09");
		bean.setCityId("0901");
		bean.setCityName("大信顺昌");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("09");
		bean.setCityId("0902");
		bean.setCityName("永宁");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("09");
		bean.setCityId("0903");
		bean.setCityName("小榄");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("10");
		abean.setAreaName("古镇");
		bean=new CityBean();
		bean.setCityPid("10");
		bean.setCityId("1001");
		bean.setCityName("古镇");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("10");
		bean.setCityId("1002");
		bean.setCityName("国贸");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("11");
		abean.setAreaName("东升镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("12");
		abean.setAreaName("南头镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("13");
		abean.setAreaName("阜沙镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("14");
		abean.setAreaName("黄埔镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("15");
		abean.setAreaName("三角镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("16");
		abean.setAreaName("民众镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("17");
		abean.setAreaName("东凤镇");
		bean=new CityBean();
		bean.setCityPid("17");
		bean.setCityId("1701");
		bean.setCityName("名扬世纪广场");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("18");
		abean.setAreaName("横栏镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("19");
		abean.setAreaName("大涌镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("20");
		abean.setAreaName("三乡镇");
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2001");
		bean.setCityName("雅居乐酒店");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2002");
		bean.setCityName("华丰片区");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2003");
		bean.setCityName("三乡市场");
		tlist.add(bean);
		bean=new CityBean();
		bean.setCityPid("20");
		bean.setCityId("2004");
		bean.setCityName("顺昌广场");
		tlist.add(bean);
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("21");
		abean.setAreaName("南朗镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("22");
		abean.setAreaName("坦洲镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("23");
		abean.setAreaName("神湾镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		abean=new AreaBean();
		tlist=new ArrayList<CityBean>();
		abean.setAreaId("24");
		abean.setAreaName("板芙镇");
		abean.setCityBeanList(tlist);
		list.add(abean);
		return list;
	}
	/**
	 * 获取封面或背景图
	 * @param flag 1为封面
	 * @return
	 */
	public static List<String> getGqDefaultBackgroudOrCoverPic(int flag) {
		List<String> list=new ArrayList<String>();
		String picurls="";
		if(flag==1){
			picurls = PropertiesUtils.getValue("defaultcoverpiclist"); //
		}else{
			picurls = PropertiesUtils.getValue("defaultbgpiclist"); //
		}
		if(picurls!=null && picurls.length()>1){
			String [] picArr = picurls.split(",");
			if (picArr != null && picArr.length> 0) {
				for (int i = 0; i < picArr.length; i++) {
					if (picArr[i]!=null && picArr[i].length()>1) {
						list.add(picArr[i]);
					}
				}
			}
		}
		return list;
	}

}
