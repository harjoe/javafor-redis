package demo.redis.persist;

import java.util.List;

import com.javafor.redis.annotation.Cache;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 **/
public class ArticleService {

	private ArticleDAO articleDAO;

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	public List<Article> findAll() {
		return this.articleDAO.findAll();
	}

	@Cache(timeout = 500L)
	public List<Article> findTag(String tag) {
		return this.articleDAO.findByTag("redis");
	}

}
