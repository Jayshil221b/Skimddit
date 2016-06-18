package edu.fullerton.ecs.cpsc476.representation;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Article
{
	private final int id;
	
	@NotBlank
	@Length(min=1, max=255)
	private final String articleName;
	
	@NotBlank
	@Length(min=1, max=255)
	private final String articleUrl;
	
	private final String userName;
	
	private final int vote;
	
	private Timestamp time;
	
	public Article() 
	{
		this.id = 0;
		this.articleName = null;
		this.articleUrl = null;
		this.userName = null;
		this.vote = 1;
		this.time = null;
	}
	
	public Article(int id, String articleName, String articleUrl, String userName, int vote, Timestamp time) 
	{
		this.id = id;
		this.articleName = articleName;
		this.articleUrl = articleUrl;
		this.userName = userName;
		this.vote = vote;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public String getArticleName() {
		return articleName;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public String getUserId() {
		return userName;
	}

	public int getVote() {
		return vote;
	}

	public Date getTime() {
		return time;
	}
	
}
