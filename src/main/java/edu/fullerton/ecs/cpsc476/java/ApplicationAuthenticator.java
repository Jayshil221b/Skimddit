package edu.fullerton.ecs.cpsc476.java;

import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;

import edu.fullerton.ecs.cpsc476.dao.HelpDAO;
import edu.fullerton.ecs.cpsc476.dao.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class ApplicationAuthenticator  implements Authenticator<BasicCredentials, String>
{
	private final UserDAO userDao;
	public ApplicationAuthenticator(DBI jdbi) 
	{
		userDao = jdbi.onDemand(UserDAO.class);
	}
	
	public Optional<String> authenticate(BasicCredentials c) throws AuthenticationException 
	{
		System.out.println("********************************************************");
		HelpDAO hd = new HelpDAO();
		String Md5 = hd.getMd5(c.getPassword());
		System.out.println(c.getUsername()+c.getPassword()+Md5);
		boolean validUser = (userDao.getAuthentication(c.getUsername(), Md5) == 1);
		System.out.println("*********************"+validUser+"*******************");
		if (validUser) 
		{
			return Optional.of(c.getUsername());
		}
			return Optional.absent();
	}
}
