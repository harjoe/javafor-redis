package com.javafor.redis.key;

/**
 * 
 * 键值不是唯 一.
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Dec 29, 2016
 **/
public class MoreKeyFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8045229119724190126L;

	public MoreKeyFoundException(){
		super();
	}
	
	
}
