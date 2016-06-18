package edu.fullerton.ecs.cpsc476.resources;

import java.net.URISyntaxException;

import io.dropwizard.auth.Auth;

import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import edu.fullerton.ecs.cpsc476.dao.VoteDAO;

@Path("article/{id}/vote")
@Produces(MediaType.APPLICATION_JSON)
public class VoteResource 
{
	private final VoteDAO VoteDao;
	private final Validator validator;
	public VoteResource(DBI jdbi, Validator validator) 
	{
		VoteDao = jdbi.onDemand(VoteDAO.class);
		this.validator = validator;
	} 
	
	@GET
	@Path("/up")
	public Response getUpVote(@PathParam("id") int articleId, @Auth String userName) throws URISyntaxException
	{
		int isValidArt = VoteDao.isValidArticle(articleId);
		if(isValidArt==0)
		{
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " +articleId).build();
		}
		
		int i = VoteDao.getVoteExists(articleId, userName);
		System.out.println("VOTE: "+i);
		if(i==0)
		{
			int noOfVote = VoteDao.getArticleVote(articleId);
			noOfVote++;
			int j = VoteDao.updateArticleVote(articleId, noOfVote);
			int upVoteId = VoteDao.createUpVote(articleId, userName) ;
			return Response.ok("You have up voted for articleId "+articleId).build();
		}	
		else
		{
			int type = VoteDao.getTypeOfVote(articleId, userName);
			if(type==1)
			{
				return Response.ok("You already up voted for this article").build();
			}	
			else
			{
				int noOfVote = VoteDao.getArticleVote(articleId);
				noOfVote++;
				noOfVote++;
				int j = VoteDao.updateArticleVote(articleId, noOfVote);
				int k = VoteDao.updateArticleVoteType(articleId, userName,1);
				return Response.ok("You have up voted for articleId "+articleId).build();
			}
		}	
	}
	
	@GET
	@Path("/down")
	public Response getDownVote(@PathParam("id") int articleId, @Auth String userName) throws URISyntaxException
	{
		int isValidArt = VoteDao.isValidArticle(articleId);
		if(isValidArt==0)
		{
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " +articleId).build();
		}
		
		int i = VoteDao.getVoteExists(articleId, userName);
		System.out.println("VOTE: "+i);
		if(i==0)
		{
			int noOfVote = VoteDao.getArticleVote(articleId);
			if(noOfVote!=0)
			{	
				noOfVote--;
			}
			int j = VoteDao.updateArticleVote(articleId, noOfVote);
			int downVoteId = VoteDao.createDownVote(articleId, userName) ;
			return Response.ok("You have down voted for articleId "+articleId).build();
		}	
		else
		{
			int type = VoteDao.getTypeOfVote(articleId, userName);
			if(type==0)
			{
				return Response.ok("You already down voted for this article").build();
			}	
			else
			{
				int noOfVote = VoteDao.getArticleVote(articleId);
				if(noOfVote!=0)
				{	
					noOfVote--;
				}
				if(noOfVote!=0)
				{	
					noOfVote--;
				}
				int j = VoteDao.updateArticleVote(articleId, noOfVote);
				int k = VoteDao.updateArticleVoteType(articleId, userName,0);
				return Response.ok("You have down voted for articleId "+articleId).build();
			}
		}
		
	}
}
