package edu.fullerton.ecs.cpsc476.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface VoteDAO
{
	@SqlQuery("select count(*) from skimmdit.article where id = :articleId")
	int isValidArticle(@Bind("articleId") int articleId);
	
	@SqlQuery("select count(*) from skimmdit.vote where articleId = :articleId AND userId = (select Id from skimmdit.user where userName = :userName)")
	int getVoteExists(@Bind("articleId") int articleId, @Bind("userName") String userName);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into skimmdit.vote (id, articleId, userId, type) values (NULL, :articleId, (select Id from skimmdit.user where userName= :userName), 1)")
	int createUpVote(@Bind("articleId") int articeId,@Bind("userName") String userName);
	
	@SqlQuery("select type from skimmdit.vote where articleId = :articleId AND userId = (select Id from skimmdit.user where userName = :userName)")
	int getTypeOfVote(@Bind("articleId") int articleId, @Bind("userName") String userName);
	
	@SqlQuery("select vote from skimmdit.article where Id = :articleId")
	int getArticleVote(@Bind("articleId") int articleId);
	
	@SqlUpdate("update skimmdit.article set vote = :vote where Id = :articleId")
	int updateArticleVote(@Bind("articleId") int articleId, @Bind("vote") int vote);
	
	@SqlUpdate("update skimmdit.vote set type = :type where articleId = :articleId AND userId = (select Id from skimmdit.user where userName = :userName)")
	int updateArticleVoteType(@Bind("articleId") int articleId, @Bind("userName") String userName, @Bind("type") int type);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into skimmdit.vote (id, articleId, userId, type) values (NULL, :articleId, (select Id from skimmdit.user where userName= :userName), 0)")
	int createDownVote(@Bind("articleId") int articeId,@Bind("userName") String userName);
	
}
