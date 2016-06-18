package edu.fullerton.ecs.cpsc476.daomappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import edu.fullerton.ecs.cpsc476.representation.User;

public class UserMapper implements ResultSetMapper<User>
{
	public User map(int index, ResultSet r, StatementContext ctx) throws SQLException 
	{	
		return new User(r.getInt("id"), r.getString("userName"),r.getString("password"));
	}
}
