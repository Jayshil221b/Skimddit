package edu.fullerton.ecs.cpsc476.daomappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import edu.fullerton.ecs.cpsc476.representation.Article;

public class ArticleMapper implements ResultSetMapper<Article>
{
	public Article map(int index, ResultSet r, StatementContext ctx) throws SQLException 
	{	
		return new Article(r.getInt("id"), r.getString("articleName"), r.getString("articleurl"),r.getString("userName"), r.getInt("vote"), r.getTimestamp("time"));
	}
}