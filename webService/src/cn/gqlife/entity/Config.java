package cn.gqlife.entity;

import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;
import net.sf.json.util.PropertyFilter;

public class Config {
	private String wspath;
	private String tongbaoWspath;
	private String pushpath;
	private String jdbcDriverUrl;

	public String getJdbcDriverUrl() {
		return jdbcDriverUrl;
	}

	public void setJdbcDriverUrl(String jdbcDriverUrl) {
		this.jdbcDriverUrl = jdbcDriverUrl;
	}

	public String getPushpath() {
		return pushpath;
	}

	public void setPushpath(String pushpath) {
		this.pushpath = pushpath;
	}

	public String getWspath() {
		return wspath;
	}

	public void setWspath(String wspath) {
		this.wspath = wspath;
	}

	public String getTongbaoWspath() {
		return tongbaoWspath;
	}

	public void setTongbaoWspath(String tongbaoWspath) {
		this.tongbaoWspath = tongbaoWspath;
	}
	
	public static JsonConfig getInitJsonConfig(Class<?> classType) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(classType);
		// 空值转换过滤
		jsonConfig.setJavaPropertyFilter(
			new PropertyFilter() {
			   public boolean apply(Object source, String name, Object value) {
				  if(value.equals(null)) {
	                  return true;
	              }
	              return false;
			   }  
			}
		);
		// 首字母为大字（全大字没问题）出错解决 
		jsonConfig.setJavaIdentifierTransformer(
			new JavaIdentifierTransformer() {
				@Override      
                public String transformToJavaIdentifier(String str) {
					char[] chars = str.toCharArray();        
					if(Character.isUpperCase(chars[0]) && Character.isUpperCase(chars[1])) {
						return str;      
					}else {
						chars[0] = Character.toLowerCase(chars[0]);        
						return new String(chars);      
					}
                }          
           }
        );
		return jsonConfig;
	}
	
	public static String getRootPath() { 
		//因为类名为"Application"，因此" Application.class"一定能找到 
		String result = Config.class.getResource("Config.class").toString(); 
		int index = result.indexOf("WEB-INF"); 
		if(index == -1){ 
			index = result.indexOf("bin"); 
		} 
		result = result.substring(0,index); 
		if(result.startsWith("jar")) { 
			result = result.substring(10); 
		}else if(result.startsWith("file")) { 
			result = result.substring(6); 
		} 
		if(result.endsWith("/"))result = result.substring(0,result.length()-1);//不包含最后的"/" 
		return result; 
	} 
	
	
	
}
