package com.javafor.redis.key;

import java.io.Serializable;

/**
 * Cache key 生成器. 可以根据类名.id, 方法等参数根据自己的规则生成 key.
 * 
 * <pre>
 * getKey
 * getKeyPrefix
 * </pre>
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since 2016年6月13日
 * @lastet 2016年6月13日
 * @lastet Nov 8, 2016 by Harjoe - 删除以Class<?>为参数的方法,,简单化.
 **/
public interface KeyGenerator {

	/**
	 * 根据类类型名称
	 * @param signal 类标志, 通常取类的全名, ex: java.util.Integer
	 * @param identity
	 * @return
	 */
	public String getKey(String signal, Serializable identity);

	/**
	 * @param signal
	 * @param keySeat
	 * @return
	 */
	public String getKey(String signal, KeySeat keySeat);

	/**
	 * 根据类类型以及方法名构成key.
	 * 
	 * @param signal 类类型; ex: 通常取类的全名, ex: java.util.Integer;
	 * @param method 方法名; ex: findList; 通常用来做servie某一方法的标志
	 * @param args 参数
	 * @return
	 */
	public String getKey(String signal, String method, Object... args);

	/**
	 * 根据类类型以及方法名构成key.
	 * @param signal
	 * @param method
	 * @param expired 过期时间
	 * @param args
	 * @return
	 */
	public String getKey(String signal, String method, long expired, Object... args);

	/**
	 * 键的前缀, getKey(String, Serializable) 的前缀.
	 * @param signal
	 * @return
	 */
	public String getPrefix(String signal);

	/**
	 * getKey(String, String, Object...) 的键前缀.
	 * @param signal
	 * @param method
	 * @return
	 */
	public String getPrefix(String signal, String method);

	/**
	 * 得到匹配模式
	 * @param signal
	 * @return
	 */
	public String getPattern(String signal);

	/**
	 * 得到匹配模式 
	 * @param signal
	 * @param keySeat
	 * @return
	 */
	public String getPattern(String signal, KeySeat keySeat);

	/**
	 * @param signal
	 * @param method
	 * @return
	 */
	public String getPattern(String signal, String method);

	/**
	 * 得到匹配模式
	 * @param signal
	 * @param method
	 * @param args
	 * @return
	 */
	public String getPattern(String signal, String method, Object... args);

}
