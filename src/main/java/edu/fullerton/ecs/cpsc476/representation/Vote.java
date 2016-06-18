package edu.fullerton.ecs.cpsc476.representation;

public class Vote 
{
	private final int id;
	
	private final int articleId;
	
	private final int userId;
	
	private final int type;
	
	public Vote() 
	{
		this.id = 0;
		this.articleId = -1;
		this.userId = -1;
		this.type = -1;
	}

	public Vote(int id, int articleId, int userId, int type)
	{
		this.id = id;
		this.articleId = articleId;
		this.userId = userId;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public int getArticleId() {
		return articleId;
	}

	public int getUserId() {
		return userId;
	}

	public int getType() {
		return type;
	}
	
}
