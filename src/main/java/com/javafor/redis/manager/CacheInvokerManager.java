package com.javafor.redis.manager;

import java.util.HashMap;
import java.util.Map;

import com.javafor.redis.cache.element.CacheOption;
import com.javafor.redis.cache.trace.CacheInvoker;
import com.javafor.redis.cache.trace.CacheTrace;
import com.javafor.redis.cache.trace.MethodInvoker;
import com.javafor.redis.cache.trace.TraceType;
import com.javafor.ssist.MethodInfo;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @review Nov 10, 2017 by Harjoe;
 **/
public final class CacheInvokerManager {

	/**
	 * Map<Class.name, Map<methodName.cacheOptionInvokeLineNumber, CacheIfno>>
	 */
	private Map<String, CacheInvoker> invokers = new HashMap<String, CacheInvoker>();

	private Map<String, CacheOption> requestedOptionTraces = new HashMap<String, CacheOption>();

	private Map<String, CacheOption> requestedTargetTraces = new HashMap<String, CacheOption>();

	private CacheInvokerManager() {
		// private
	}

	public Map<String, CacheInvoker> getInvokers() {
		return this.invokers;
	}

	public void putTraceIfAbsent(CacheTrace cacheTrace) {
		CacheInvoker invoker = this.invokers.get(cacheTrace.getVal().getClassName());
		if (invoker == null) {
			invoker = new CacheInvoker(cacheTrace.getVal().getClassName());
			this.invokers.put(cacheTrace.getVal().getClassName(), invoker);
		}
		invoker.putIfAbsent(cacheTrace);
	}

	public void cache(Long timeout) {
		StackTraceElement[] traces = new Throwable().getStackTrace();
		// 如果已经处理过了,则跳过
		String signal = CacheTrace.signal(traces[1]);
		if (this.requestedOptionTraces.containsKey(signal)) {
			return;
		}
		// 未处理
		CacheTrace optionTrace = new CacheTrace(TraceType.OPTION, traces[1]);
		CacheOption cacheOption = new CacheOption(timeout);
		optionTrace.setCacheOption(cacheOption);
		this.putTraceIfAbsent(optionTrace);
		this.requestedOptionTraces.put(signal, cacheOption);
	}

	public CacheOption getCacheOption(StackTraceElement targetTrace) {
		// 先从变量查找
		String signal = CacheTrace.signal(targetTrace);
		if (this.requestedTargetTraces.containsKey(signal)) {
			return this.requestedTargetTraces.get(signal);
		}
		//
		CacheInvoker invoker = this.invokers.get(targetTrace.getClassName());
		if (invoker == null) {
			this.requestedTargetTraces.put(signal, null);
			return null;
		}
		//
		MethodInfo invokeMethod = ClassMethodManager.getIntance().getInvokerMethod(targetTrace);
		MethodInvoker methodInvoker = invoker.getMethodInvoker(invokeMethod);
		if (methodInvoker == null) {
			this.requestedTargetTraces.put(signal, null);
			return null;
		}
		//
		CacheOption cacheOption = methodInvoker.getCacheOption(targetTrace);
		this.requestedTargetTraces.put(signal, cacheOption);
		return cacheOption;
	}

	/*
	 * *********** static of class **********
	 */

	private static CacheInvokerManager INSTANCE = new CacheInvokerManager();

	public static CacheInvokerManager getInstance() {
		return INSTANCE;
	}

}
