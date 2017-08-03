package com.springboot.poc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.poc.model.User;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

@RestController
public class HomeController {

	@Value("${dev.app.db.username}")
	private String username;
	
	@Value("${dev.app.db.password}")
	private String password;
	
	@Value("${dev.app.db.url}")
	private String url; 
	
	@RequestMapping("/")
	public String index() {
		return "Welcome to DB test";
	}

	@RequestMapping(method = RequestMethod.GET, value="/getData/{id}")
	public User getData(@PathVariable("id") String id) {
		/*String username = "TOSS_DLMS";
		String password = "toss_dlms";
		String url = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = dukeortv97.corp.cox.com)(PORT = 1521)) (CONNECT_DATA = (SERVER =DEDICATED) (SERVICE_NAME = DTELOPS.WORLD)))";
		*/User user = new User();
		System.out.println("====>>>>>>dev_app_db_username Env ======="+System.getProperty("dev_app_db_username"));
		System.out.println("====>>>>>>JWS_ADMIN_PASSWORD Env ======="+System.getProperty("JWS_ADMIN_PASSWORD"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Here");
			Connection con = DriverManager.getConnection(url, username, password);
			PreparedStatement ps = con
					.prepareStatement("select FIRSTNAME, LASTNAME, EMAIL FROM DL_USER WHERE ID = "+id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setFirstname(rs.getString(1));
				user.setLastname(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setMessage("User Exists");
			} else {
				user.setMessage("Not Found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/getjndicon")
	public String getJNDIConnection (@PathVariable("id") String id) {

		  /** Uses JNDI and Datasource (preferred style).   */
		    String DATASOURCE_CONTEXT = "java:DapsDS";
		    
		    Connection result = null;
		    try {
		      Context initialContext = new InitialContext();
		      if ( initialContext == null){
		    	  System.out.println("JNDI problem. Cannot get InitialContext.");
		      }
		      DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
		      if (datasource != null) {
		        result = datasource.getConnection();
		      }
		      else {
		    	  System.out.println("Failed to lookup datasource.");
		      }
		    }
		    catch ( NamingException ex ) {
		    	System.out.println("Cannot get connection: " + ex);
		    }
		    catch(SQLException ex){
		    	System.out.println("Cannot get connection: " + ex);
		    }
		    return "Connecting to JNDI Lookup";
		  
	}
}
