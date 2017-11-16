package com.javafor.redis.spring;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 **/
public class ProxierTraceAnalyzer {

	public static StackTraceElement getInvokerTrace(StackTraceElement[] traces, String targetClass, String signature) {
		int index = -1;
		for (int i = 0; i < traces.length; i++) {
			StackTraceElement element = traces[i];
			if (element.getClassName().contains(targetClass) && element.getMethodName().contains(signature)) {
				index = i;
				break;
			}
		}
		return traces[index + 1];
	}

}
