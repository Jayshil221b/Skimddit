package edu.fullerton.ecs.cpsc476.dao;

import java.sql.Timestamp;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import edu.fullerton.ecs.cpsc476.daomappers.ArticleMapper;
import edu.fullerton.ecs.cpsc476.representation.Article;

public interface ArticleDAO 
{
	@GetGeneratedKeys
	@SqlUpdate("insert into skimmdit.article (id, articleName, articleUrl, time, vote, userId) values (NULL, :articleName, :articleUrl, :time, 1, (select id from skimmdit.user where userName= :userName))")
	int createArticle(@Bind("articleName") String articleName,@Bind("articleUrl") String articleurl, @Bind("time") Timestamp time, @Bind("userName") String userName );
	
	@Mapper(ArticleMapper.class)
	@SqlQuery("select id, articleName, articleUrl, time, vote, userName from skimmdit.article left join skimmdit.user on skimmdit.article.userId = skimmdit.user.Id ORDER BY skimmdit.article.vote DESC")
	List<Article> getAllArticle();
	
}
