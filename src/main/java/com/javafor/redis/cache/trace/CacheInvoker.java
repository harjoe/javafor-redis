package com.javafor.redis.cache.trace;

import java.util.HashMap;
import java.util.Map;

import com.javafor.redis.manager.ClassMethodManager;
import com.javafor.ssist.MethodInfo;

/**
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Nov 13, 2017
 **/
public class CacheInvoker {

	private String invokerClassName;

	/**
	 * Map<MethodName, List<>>
	 */
	private Map<String, MethodInvoker> methodInvokers = new HashMap<String, MethodInvoker>();

	public String getInvokerClassName() {
		return this.invokerClassName;
	}

	public CacheInvoker() {

	}

	public CacheInvoker(String invokerClassName) {
		this.invokerClassName = invokerClassName;
	}

	public void putIfAbsent(CacheTrace trace) {
		MethodInvoker invoker = this.methodInvokers.get(trace.getVal().getClassName());
		if (invoker == null) {
			MethodInfo invokeMethod = ClassMethodManager.getIntance().getInvokerMethod(trace.getVal());
			invoker = new MethodInvoker(invokeMethod);
			this.methodInvokers.put(invokeMethod.getSignal(), invoker);
		}
		invoker.putIfAbsent(trace);
	}
	
	public MethodInvoker getMethodInvoker(MethodInfo invokeMethod) {
		return this.methodInvokers.get(invokeMethod.getSignal());
	}
	
	public MethodInvoker getMethodInvoker(String methodSignal) {
		return this.methodInvokers.get(methodSignal);
	}
	
}
