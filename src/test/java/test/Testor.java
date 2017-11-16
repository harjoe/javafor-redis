package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import demo.redis.persist.ArticleController;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 **/
public class Testor {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = getApplicationContext();
		
		ArticleController studentController = context.getBean(ArticleController.class);
		studentController.index();
		studentController.getRedisArticles();
		//重复执行
		System.out.println("以下是重复执行-------");
		studentController.index();
		studentController.getRedisArticles();;
		
		
	}
	
	public static ApplicationContext getApplicationContext() throws Exception {
		try {
			System.out.println("test start.");
			ApplicationContext context = new FileSystemXmlApplicationContext(
					"classpath:applicationContext.xml");
			return context;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
