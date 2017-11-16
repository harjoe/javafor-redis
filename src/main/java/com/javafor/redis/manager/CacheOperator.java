package com.javafor.redis.manager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javafor.redis.accessor.CacheAccessor;
import com.javafor.redis.key.KeyGenerator;
import com.javafor.redis.key.KeySeat;
import com.javafor.redis.key.MoreKeyFoundException;

/**
 * 缓存存操作
 * 
 * @require target.class implements EntityClassRequire
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since 2016年6月12日
 * @lastet 2016年8月8日
 * @lastet Dec 29, 2016 by Harjoe;
 **/
public class CacheOperator {

	private static final Logger log = LoggerFactory.getLogger(CacheOperator.class);

	/**
	 * key规格生成器
	 */
	private KeyGenerator keyGenerator;
	
	/**
	 * cache 访问器
	 */
	private CacheAccessor cacheAccessor;

	/*
	 * ********** gets and sets **********
	 */

	public CacheAccessor getCacheAccessor() {
		return cacheAccessor;
	}

	public void setCacheAccessor(CacheAccessor cacheAccessor) {
		this.cacheAccessor = cacheAccessor;
	}
	
	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	/*
	 * ********** public methods ***********
	 */

	public Object getCache(String signal, Serializable id) {
		if (id == null)
			return null;
		log.info("get cache: " + signal + "; " + id);
		String key = this.keyGenerator.getKey(signal, id);
		return this.cacheAccessor.getCache(key);
	}

	public Object getCache(String signal, KeySeat keyMap) {
		log.info("get cache: " + signal + "; by keyMap");
		String pattern = this.keyGenerator.getPattern(signal, keyMap);
		Collection<String> keys = this.cacheAccessor.keys(pattern);
		if (keys.size() > 1)
			throw new MoreKeyFoundException();
		for(String key: keys)
			return this.cacheAccessor.getCache(key);
		return null;
	}

	public void cleanCache(String signal) {
		log.info("clean cache: " + signal);
		String pattern = this.keyGenerator.getPrefix(signal);
		pattern = pattern + "*";
		Collection<String> keys = this.cacheAccessor.keys(pattern);
		this.cacheAccessor.cleanCache(keys);
	}
	
	public void cleanCache(String signal, Serializable id) {
		if (id == null)
			return;
		log.info("clean cache: " + signal + ", " + id);
		String pattern = this.keyGenerator.getKey(signal, id);
		pattern = pattern + "*";
		Collection<String> keys = this.cacheAccessor.keys(pattern);
		this.cacheAccessor.cleanCache(keys);
	}

	public void cleanCache(String signal, List<Serializable> ids) {
		log.info("clean caches: " + signal + ", .... count, " + ids.size());
		for (Serializable id : ids) {
			String pattern = this.keyGenerator.getKey(signal, id);
			pattern = pattern + "*";
			Collection<String> keys = this.cacheAccessor.keys(pattern);
			this.cacheAccessor.cleanCache(keys);
		}
	}
	
	public void saveCache(String signal, Serializable id, Serializable instance, long timeout) {
		log.info("save cache: " + signal + ", " + id);
		String key = this.keyGenerator.getKey(signal, id);
		this.cacheAccessor.saveCache(key, instance, timeout);
	}

	public void saveCache(String signal, KeySeat keyMap, Serializable instance, long timeout) {
		log.info("save cache: " + signal + ", keyMap");
		String key = this.keyGenerator.getPattern(signal, keyMap);
		this.cacheAccessor.saveCache(key, instance, timeout);
	}
	
}
