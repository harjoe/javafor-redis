package com.javafor.redis.accessor;

import java.util.Collection;

/**
 * 缓存操作操作器, 读,写,清除 功能 </p>
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Nov 8, 2017
 **/
public interface CacheAccessor {

	/**
	 * 查询缓存介质中的 key,  
	 * @param pattern 式样.
	 * @return
	 */
	public Collection<String> keys(String pattern);

	/**
	 * 清除缓存, 
	 * @param key key.
	 * */
	public void cleanCache(String key);

	/**
	 * 清除缓存,批量
	 * @param keys
	 */
	public void cleanCache(Collection<String> keys);

	/**
	 * 获取缓存
	 * */
	public Object getCache(String key);

	/**
	 * 保存对象到缓存介质, 时效永久
	 * */
	public void saveCache(String key, Object instance);

	/**
	 * 保存对象到缓存介质, 有生命周期
	 * 
	 * @param timeout 过期时间, 单位为秒
	 * */
	public void saveCache(String key, Object instance, long timeout);

}
