package cn.gqlife.dao;

import javax.servlet.http.HttpServletRequest;

public interface UserDAO {
	public boolean setPassword(HttpServletRequest request);
}
