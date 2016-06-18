package edu.fullerton.ecs.cpsc476.java;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.fullerton.ecs.cpsc476.resources.ArticleResource;
import edu.fullerton.ecs.cpsc476.resources.DatabaseResource;
import edu.fullerton.ecs.cpsc476.resources.UserResource;
import edu.fullerton.ecs.cpsc476.resources.VoteResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
/**
 * Hello world!
 *
 */
public class App extends Application<ApplicationConfiguration> 
{
	private static final Logger LOGGER =
			LoggerFactory.getLogger(App.class);
	
	@Override
	public void initialize(Bootstrap<ApplicationConfiguration> b) 
	{
		b.addBundle(new ViewBundle());
	}
	
	@Override
	public void run(ApplicationConfiguration c, Environment e) throws Exception
	{
		LOGGER.info("Method App#run() called");
		
		// Create a DBI factory and build a JDBI instance
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "hsql");
		
		// build the client and add the resource to the environment
		//final Client client = new JerseyClientBuilder(e).build("REST Client");
	   // client.addFilter(new HTTPBasicAuthFilter("john_doe", "secret"));
		//e.jersey().register(new ClientResource(client));
		
		 //Register the authenticator with the environment
		//e.jersey().register(new BasicAuthProvider<String>(new ApplicationAuthenticator(jdbi), "Web Service Realm"));
		e.jersey().register(AuthFactory.binder(new BasicAuthFactory<String>(new ApplicationAuthenticator(jdbi),"SECURITY REALM", String.class)));
		// Add the resource to the environment
		e.jersey().register(new UserResource(jdbi, e.getValidator()));
		e.jersey().register(new ArticleResource(jdbi, e.getValidator()));
		e.jersey().register(new VoteResource(jdbi, e.getValidator()));
		e.jersey().register(new DatabaseResource(jdbi, e.getValidator()));
		// Authenticator, with caching support (CachingAuthenticator)
		//CachingAuthenticator<BasicCredentials, Boolean> authenticator = new CachingAuthenticator<BasicCredentials, Boolean>(e.metrics(), new ApplicationAuthenticator(jdbi),CacheBuilderSpec.parse("maximumSize=10000, expireAfterAccess=10m"));
		// Register the authenticator with the environment
		//e.jersey().register(new BasicAuthProvider<Boolean>(authenticator, "Web Service Realm"));
	}
	
	public static void main( String[] args ) throws Exception
	{
		try{
		new App().run(args);
		}catch(Exception e){System.out.println(e);}
	}
}
