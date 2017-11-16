package com.javafor.redis.cache.trace;

import java.util.HashMap;
import java.util.LinkedHashMap;
import com.javafor.redis.cache.element.CacheOption;

/**
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Nov 13, 2017
 **/
import com.javafor.ssist.MethodInfo;

public class MethodInvoker {

	/**
	 * 调用的方法信息
	 */
	private MethodInfo method;

	private HashMap<String, CacheTrace> traces = new LinkedHashMap<String, CacheTrace>();

	private CacheTrace lastTrace;

	/*
	 * ********** gets and sets **********
	 */

	public MethodInfo getMethod() {
		return method;
	}

	public void setMethod(MethodInfo method) {
		this.method = method;
	}

	/*
	 * ********** constructor **********
	 */

	public MethodInvoker() {
		// none
	}

	public MethodInvoker(MethodInfo method) {
		this.method = method;
	}

	public synchronized void putIfAbsent(CacheTrace trace) {
		if (this.traces.containsKey(trace.getSignal()))
			return;
		this.traces.put(trace.getSignal(), trace);
		this.lastTrace = trace;
	}

	/**
	 * 得到目标对象的CacheOption.
	 * @param targetTraceElement
	 * @return
	 */
	public synchronized CacheOption getCacheOption(StackTraceElement targetTraceElement) {
		if (this.traces == null || this.traces.size() == 0)
			return null;
		String signal = CacheTrace.signal(targetTraceElement);
		CacheTrace cacheTrace = this.traces.get(signal);
		if (cacheTrace != null) {
			return cacheTrace.isCacheable() ? cacheTrace.getCacheOption() : null;
		} else {
			cacheTrace = new CacheTrace(TraceType.TARGET, targetTraceElement);
			if (this.lastTrace.getTraceType().equals(TraceType.OPTION)) {
				cacheTrace.setCacheable(true);
				cacheTrace.setCacheOption(lastTrace.getCacheOption());
			} else {
				cacheTrace.setCacheable(false);
			}
			this.traces.put(signal, cacheTrace);
			this.lastTrace = cacheTrace;
			return cacheTrace.getCacheOption();
		}
	}

}
