package edu.fullerton.ecs.cpsc476.representation;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User
{
	private final int id;
	@NotBlank
	@Length(min=1, max=255)
	private final String userName;
	@NotBlank
	@Length(min=1, max=255)
	private final String password;
	
	public User() 
	{
		this.id = 0;
		this.userName = null;
		this.password = null;
	}

	public User(int id, String userName, String password)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
	
	public int getId() 
	{
		return id;
	}
	public String getuserName() 
	{
		return userName;
	}
	public String getpassword() 
	{
		return password;
	}

}
