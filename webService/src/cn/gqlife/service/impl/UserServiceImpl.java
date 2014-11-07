package cn.gqlife.service.impl;

import javax.servlet.http.HttpServletRequest;

import cn.gqlife.dao.UserDAO;

public class UserServiceImpl implements cn.gqlife.service.UserService {
	UserDAO userDAO;
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Override
	public boolean setPassword(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return userDAO.setPassword(request);
	}

}
