package edu.fullerton.ecs.cpsc476.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.dropwizard.auth.Auth;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;

import edu.fullerton.ecs.cpsc476.dao.ArticleDAO;
import edu.fullerton.ecs.cpsc476.dao.HelpDAO;
import edu.fullerton.ecs.cpsc476.representation.Article;

@Path("/article")
@Produces(MediaType.APPLICATION_JSON)
public class ArticleResource 
{
	private final ArticleDAO ArticleDao;
	private final Validator validator;
	public ArticleResource(DBI jdbi, Validator validator) 
	{
		ArticleDao = jdbi.onDemand(ArticleDAO.class);
		this.validator = validator;
	} 
	
	@GET
	@Path("/showallarticle")
	public Response getAllUser()
	{
		List<Article> l = ArticleDao.getAllArticle();
		return Response.ok(l).build();
	}
	
	@POST
	@Path("/createarticle")
	public Response createArticle(Article article, @Auth String userName) throws URISyntaxException
	{
				Set<ConstraintViolation<Article>> violations = validator.validate(article);
				// Are there any constraint violations?
				if (violations.size() > 0) 
				{
					// Validation errors occurred
					ArrayList<String> validationMessages = new ArrayList<String>();
					for (ConstraintViolation<Article> violation : violations)
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
					Timestamp time = hd.getTime();
					int newArticleId = ArticleDao.createArticle(article.getArticleName(), article.getArticleUrl(), time, userName);
					return Response.created(new URI(String.valueOf(newArticleId))).build();
				}
	}
}
