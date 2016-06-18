package edu.fullerton.ecs.cpsc476.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;

import edu.fullerton.ecs.cpsc476.dao.HelpDAO;
import edu.fullerton.ecs.cpsc476.dao.UserDAO;
import edu.fullerton.ecs.cpsc476.representation.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource 
{
	private final UserDAO UserDao;
	private final Validator validator;
	public UserResource(DBI jdbi, Validator validator) 
	{
		UserDao = jdbi.onDemand(UserDAO.class);
		this.validator = validator;
	} 
	
	@GET
	@Path("/showalluser")
	public Response getAllUser()
	{
		List<User> l = UserDao.getAllUsers();
		return Response.ok(l).build();
	}
	
	@GET
	@Path("getuser/{id}")
	public Response getUser(@PathParam("id") int id) 
	{
		int i = UserDao.doesUserExist(id);
		if(i==0)
		{
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " +id).build();
		}
		else
		{	
			// retrieve information about the contact with the provided id
			User user = UserDao.getUserById(id);
			return Response.ok(new User(id, user.getuserName(), "************")).build();
		}
	}
	
	@POST
	@Path("/createuser")
	public Response createUser(User user) throws URISyntaxException
	{
		int i = UserDao.isDuplicateUser(user.getuserName());
		if(i==1)
		{
			return Response.status(Response.Status.NOT_FOUND).entity("UserName Already Exists Try another").build();
		}
		else
		{	
			// Validate the contact's data
			Set<ConstraintViolation<User>> violations = validator.validate(user);
			// Are there any constraint violations?
			if (violations.size() > 0) 
			{
				// Validation errors occurred
				ArrayList<String> validationMessages = new ArrayList<String>();
				for (ConstraintViolation<User> violation : violations)
				{	
					validationMessages.add(violation.getPropertyPath().toString() +":" + violation.getMessage());
				}
				return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
			}
			else 
			{
				// OK, no validation errors
				// Store the new contact
				HelpDAO hd = new HelpDAO();
				String Md5 = hd.getMd5(user.getpassword());
				int newUserId = UserDao.createUser(user.getuserName(), Md5);
				return Response.created(new URI(String.valueOf(newUserId))).build();
			}
		}
	}
	
	@DELETE
	@Path("deleteuser/{id}")
	public Response deleteUser(@PathParam("id") int id)
	{
		int i = UserDao.doesUserExist(id);
		if(i==0)
		{
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " +id).build();
		}
		else
		{	
			// delete the contact with the provided id
			UserDao.deleteUser(id);
			return Response.noContent().build();
		}
	}
	
	@PUT
	@Path("updateuser/{id}")
	public Response updateUser(@PathParam("id") int id, User user) 
	{
		int i = UserDao.doesUserExist(id);
		if(i==0)
		{
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " +id).build();
		}
		else
		{	
			// Validate the updated data
			Set<ConstraintViolation<User>> violations = validator.validate(user);
			// Are there any constraint violations?
			if (violations.size() > 0) 
			{
				// Validation errors occurred
				ArrayList<String> validationMessages = new ArrayList<String>();
				for (ConstraintViolation<User> violation : violations) 
				{
					validationMessages.add(violation.getPropertyPath().toString() +":" + violation.getMessage());
				}
				return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
			}
			else 
			{
				// No errors
				// update the contact with the provided ID
				HelpDAO hd = new HelpDAO();
				String Md5 = hd.getMd5(user.getpassword());
				UserDao.updateUser(id, user.getuserName(), Md5);
				return Response.ok(new User(id, user.getuserName(), "************")).build();
			}
		}	
	}
	
}
