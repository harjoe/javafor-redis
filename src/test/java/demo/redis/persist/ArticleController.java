package demo.redis.persist;


import com.javafor.redis.manager.CacheInvokerManager;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 **/
public class ArticleController {

	private ArticleService articleService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	/**
	 * 假设这里首页的数据
	 */
	public void index() {
		System.out.println("invoke articleService.findAll");
		CacheInvokerManager.getInstance().cache(500L);
		this.articleService.findAll();
	}

	/**
	 * 假设这是要查找与 redis 相关的文章
	 */
	public void getRedisArticles() {
		System.out.println("invoke articleService.findTag");
		this.articleService.findTag("redis");
	}

}
