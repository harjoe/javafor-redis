package com.javafor.redis.accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis访问器, 包括读写,清除,查询键值集合功能.
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Nov 4, 2016
 * @lastet Dec 30, 2016 by Harjoe;
 * @lastet Nov 8, 2017 by Harjoe;
 **/
public class RedisCacheAccessor implements CacheAccessor {

	private static final Logger log = LoggerFactory.getLogger(CacheAccessor.class);

	public RedisTemplate<String, Object> redisTemplate;

	/*
	 * ********** gets and sets **********
	 */

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/*
	 * ********** methods ***********
	 */
	/**
	 * 查询到相应的key,
	 * @param pattern 查询关键字的模式 ; * 代表所有, harjoe* 代表以harjoe为开头的集合
	 */
	@Override
	public Collection<String> keys(String pattern) {
		if (pattern == null || pattern.equals(""))
			return new ArrayList<String>();
		return this.getRedisTemplate().keys(pattern);
	}

	@Override
	public void cleanCache(String key) {
		if (key == null || key.equals(""))
			return;
		this.getRedisTemplate().delete(key);
		log.info("clean cache, key = " + key);
	}

	@Override
	public void cleanCache(Collection<String> keys) {
		this.getRedisTemplate().delete(keys);
		log.info("clean cache, keys = ?");
	}

	@Override
	public Object getCache(String key) {
		if (key == null || key.equals(""))
			return null;
		Object result = getRedisTemplate().boundValueOps(key).get();
		log.info("get cache, key = " + key + ", object = " + result);
		return result;
	}

	@Override
	public void saveCache(String key, Object instance) {
		if (key == null || key.equals(""))
			return;
		if (instance == null) {
			this.cleanCache(key);
			log.info("clean cache, key = " + key + ", intended(save cache, object = null)");
			return;
		}
		getRedisTemplate().boundValueOps(key).set(instance);
		log.info("save cache, key = " + key);
	}

	@Override
	public void saveCache(String key, Object instance, long timeout) {
		if (key == null || key.equals(""))
			return;

		if (instance == null) {
			this.cleanCache(key);
			log.info("clean cache, key = " + key + ", timeout = " + timeout + " intended(save cache, object = null)");
			return;
		}

		if (timeout > 0)
			getRedisTemplate().boundValueOps(key).set(instance, timeout, TimeUnit.SECONDS);
		else
			getRedisTemplate().boundValueOps(key).set(instance);
		log.info("save cache, key = " + key + ", timeout = " + timeout);
	}

}
