package edu.fullerton.ecs.cpsc476.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import edu.fullerton.ecs.cpsc476.daomappers.UserMapper;
import edu.fullerton.ecs.cpsc476.representation.User;

public interface UserDAO
{
	@SqlQuery("select count(*) from skimmdit.user where userName = :userName and password = :password")
	int getAuthentication(@Bind("userName") String username, @Bind("password") String password);
	
	@SqlQuery("select count(*) from skimmdit.user where id = :id")
	int doesUserExist(@Bind("id") int id);
	
	@SqlQuery("select count(*) from skimmdit.user where userName = :userName")
	int isDuplicateUser(@Bind("userName") String userName);
	
	@Mapper(UserMapper.class)
	@SqlQuery("select id, userName,'*******' AS password from skimmdit.user")
	List<User> getAllUsers();
	
	@Mapper(UserMapper.class)
	@SqlQuery("select * from skimmdit.user where id = :id")
	User getUserById(@Bind("id") int id);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into skimmdit.user (id, userName, password) values (NULL, :userName, :password)")
	int createUser(@Bind("userName") String userName,@Bind("password") String password);
	
	@SqlUpdate("update skimmdit.user set userName = :userName, password = :password where id = :id")
	void updateUser(@Bind("id") int id, @Bind("userName")String userName, @Bind("password") String password);
	
	@SqlUpdate("delete from skimmdit.user where id = :id")
	void deleteUser(@Bind("id") int id);
}
