package com.javafor.redis.cache.trace;

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
public class CacheTrace {

	private TraceType traceType;

	private StackTraceElement val;

	private boolean cacheable = false;
	
	private CacheOption cacheOption;
	
	public TraceType getTraceType() {
		return traceType;
	}

	public void setTraceType(TraceType traceType) {
		this.traceType = traceType;
	}

	public StackTraceElement getVal() {
		return val;
	}

	public void setVal(StackTraceElement trace) {
		this.val = trace;
	}

	public String getSignal() {
		return String.format("%s.%s.%d", this.val.getClassName(), this.val.getMethodName(), this.val.getLineNumber());
	}
	
	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}
	

	public CacheOption getCacheOption() {
		return cacheOption;
	}

	public void setCacheOption(CacheOption cacheOption) {
		this.cacheOption = cacheOption;
	}
	
	/*
	 * ********** constructor **********
	 */
	
	public CacheTrace(){
		
	}
	
	public CacheTrace(TraceType traceType, StackTraceElement element) {
		this.setTraceType(traceType);
		this.setVal(element);
	}

	public static String signal(StackTraceElement element) {
		return String.format("%s.%s.%d", element.getClassName(), element.getMethodName(), element.getLineNumber());
	}

}
