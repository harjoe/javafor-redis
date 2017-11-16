package com.javafor.redis.spring;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javafor.redis.accessor.CacheAccessor;
import com.javafor.redis.annotation.Cache;
import com.javafor.redis.cache.element.CacheOption;
import com.javafor.redis.cache.trace.CacheTrace;
import com.javafor.redis.key.KeyGenerator;
import com.javafor.redis.manager.CacheInvokerManager;
import com.javafor.redis.spring.aspect.AspectProxy;
import com.javafor.redis.spring.aspect.JoinPointUtils;

/**
 * Service, Cache 代理, 拦截Service<br >
 * 目标对象必须实现 ServiceCacheable </p>
 * 
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since 2016年6月12日
 * @release Dec 9, 2016 by Harjoe;
 **/
public class GeneralCacheProxier extends AspectProxy {

	private static final Logger log = LoggerFactory.getLogger(GeneralCacheProxier.class);

	private static Map<String, CacheOption> REQUESTED_TRACES = new HashMap<String, CacheOption>();  
	
	private CacheAccessor cacheAccessor;

	private KeyGenerator keyGenerator;

	/*
	 * ********** gets and sets **********
	 */

	public CacheAccessor getCacheAccessor() {
		return cacheAccessor;
	}

	public void setCacheAccessor(CacheAccessor cacheEngine) {
		this.cacheAccessor = cacheEngine;
	}

	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	/*
	 * ********** methods ***********
	 */
	
	private CacheOption getCacheOption(StackTraceElement targetTrace, ProceedingJoinPoint point) throws NoSuchMethodException, SecurityException{
		String signal = CacheTrace.signal(targetTrace);
		if (REQUESTED_TRACES.containsKey(signal)){
			return REQUESTED_TRACES.get(signal);
		}
		//
		CacheOption cacheOption = CacheInvokerManager.getInstance().getCacheOption(targetTrace);
		if (cacheOption == null) {
			Method method = JoinPointUtils.getMethod(point);
			if (method.isAnnotationPresent(Cache.class)) {
				Cache cache = method.getAnnotation(Cache.class);
				cacheOption = new CacheOption(cache.timeout());
			}
		}
		REQUESTED_TRACES.put(signal, cacheOption);
		return cacheOption;
	}

	@Override
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		log.info("doAround, target: " + point.getTarget().getClass() + "." + point.getSignature().getName());
		//
		StackTraceElement[] traces = new Throwable().getStackTrace();
		StackTraceElement targetTrace = ProxierTraceAnalyzer.getInvokerTrace(traces, point.getTarget().getClass()
				.getName(), point.getSignature().getName());
		CacheOption cacheOption = this.getCacheOption(targetTrace, point);
		//
		if (cacheOption != null) {
			String signal = point.getTarget().getClass().getName();
			String key = this.keyGenerator.getKey(signal, point.getSignature().getName(), cacheOption.getTimeout(),
					point.getArgs());
			//
			Object result = this.cacheAccessor.getCache(key);
			if (result == null) {
				result = point.proceed(point.getArgs());
				this.cacheAccessor.saveCache(key, result, cacheOption.getTimeout());
			}
			return result;
		} else {
			return point.proceed(point.getArgs());
		}
	}

	public Object doAroundCleanCache(ProceedingJoinPoint point) throws Throwable {
		log.info("doAroundClearCache, target: " + point.getTarget().getClass() + "." + point.getSignature().getName());
		//
		String signal = point.getTarget().getClass().getName();
		String pattern = "";
		if (point.getArgs().length == 0)
			// 清除当前Service所有缓存
			pattern = this.keyGenerator.getPattern(signal);
		else if (point.getArgs().length == 1) {
			// 清除Service指定方法所有的缓存
			String method = point.getArgs()[0].toString();
			pattern = this.keyGenerator.getPattern(signal, method);
		} else if (point.getArgs().length == 2) {
			// 清除指定参数的缓存
			String method = point.getArgs()[0].toString();
			Object[] args = (Object[]) point.getArgs()[1];
			pattern = this.keyGenerator.getPattern(signal, method, args);
		}
		//
		Collection<String> keys = this.cacheAccessor.keys(pattern);
		this.cacheAccessor.cleanCache(keys);
		log.info("doAroundClearCache clean: " + pattern + "*");
		return point.proceed(point.getArgs());
	}

}
