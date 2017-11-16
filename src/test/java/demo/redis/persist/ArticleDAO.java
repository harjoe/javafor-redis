package demo.redis.persist;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 **/
public class ArticleDAO {

	public List<Article> findAll() {
		//假设向数据库发出 sql请求
		System.out.println("sql: select * from Student");
		//假设以下是请求的返回数据
		List<Article> articles = new ArrayList<Article>();
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.setId(i);
			article.setTitle("title" + i);
			articles.add(article);
		}
		return articles;
	}

	public List<Article> findByTag(String tag) {
		//假设向数据库发出 sql请求
		System.out.println("sql: select * from Student where tag = " + tag);
		//假设以下是请求的返回数据
		List<Article> articles = new ArrayList<Article>();
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.setId(i);
			article.setTag("tag_title" + i);
			article.setTag(tag);
			articles.add(article);
		}
		return articles;
	}
}
