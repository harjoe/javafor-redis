package com.javafor.redis.cache.element;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @review Nov 10, 2017 by Harjoe;
 * @release V2.1.10; Nov 10, 2017 by Harjoe;
 **/
public class CacheOption {

	private String name;

	/**
	 */
	private Long timeout;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	/*
	 * ********** constructor **********
	 */
	
	public CacheOption(){
		
	}
	
	public CacheOption(Long timeout) {
		this.setTimeout(timeout);
	}
	
	
}
