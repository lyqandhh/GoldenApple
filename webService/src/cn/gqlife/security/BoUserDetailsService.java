package cn.gqlife.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.gqlife.entity.Config;

@Service("BoUserDetailsService")
public class BoUserDetailsService implements UserDetailsService {
	@Autowired
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection c = null;
		Statement stmt = null;
		User user = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			stmt = c.createStatement();
			ResultSet rs = null;
			rs = stmt.executeQuery("SELECT * FROM users where username = '" + username + "'");
			String rsUsername = null;
			String rsPassword = null;
			String accountname=null;
			boolean enabled=false;
			while (rs.next()) {
				rsUsername = rs.getString("username");
				rsPassword = rs.getString("password");
				System.out.println(rsPassword);
				accountname=rs.getString("accountname");
				enabled=rs.getBoolean("enabled");
				System.out.println(rsUsername+"|"+accountname+"|"+enabled);
			}
			rs.close();
			rs = stmt.executeQuery("SELECT * FROM authorities where username = '" + username + "'");
			while (rs.next()) {
				authorities.add(new SimpleGrantedAuthority(rs.getString("authority")));
			}
			rs.close();
			user = new User(rsUsername+"|"+accountname, rsPassword, enabled, true, true, true, authorities);
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return user;
	}
	
}
