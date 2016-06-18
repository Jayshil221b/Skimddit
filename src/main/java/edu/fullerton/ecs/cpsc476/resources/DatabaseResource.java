package edu.fullerton.ecs.cpsc476.resources;

import java.util.List;

import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import edu.fullerton.ecs.cpsc476.dao.DatabaseDAO;
import edu.fullerton.ecs.cpsc476.representation.Article;



@Path("/database")
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseResource 
{
	private final DatabaseDAO DatabaseDao;
	private final Validator validator;
	public DatabaseResource(DBI jdbi, Validator validator) 
	{
		DatabaseDao = jdbi.onDemand(DatabaseDAO.class);
		this.validator = validator;
	} 
	
	@GET
	@Path("/createalltables")
	public Response getAllUser()
	{
		int i = DatabaseDao.createSchema();
		int j = DatabaseDao.createUserTable();
		int k = DatabaseDao.createArticleTable();
		int l = DatabaseDao.createVoteTable();
		return Response.ok("All tables are created now you are able to use this webservice").build();
	}
}
