package com.javafor.redis.manager;

import java.util.HashMap;
import java.util.Map;

import com.javafor.ssist.ClassMethod;
import com.javafor.ssist.MethodInfo;

import javassist.NotFoundException;

/**
 * 类方法管理
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @review Nov 10, 2017 by Harjoe;
 **/
public final class ClassMethodManager {

	/**
	 * 所有类的方法信息.
	 */
	private Map<String, ClassMethod> classMethods = new HashMap<String, ClassMethod>();

	/**
	 * 查找记录, 防止重复查找
	 */
	private Map<String, MethodInfo> requestedMethods = new HashMap<String, MethodInfo>();
	
	private ClassMethodManager() {
		// private
	}

	private synchronized ClassMethod getClassMethod(String className) {
		ClassMethod classMethod = this.classMethods.get(className);
		if (classMethod == null) {
			try {
				classMethod = new ClassMethod();
				classMethod.load(className);
				this.classMethods.put(className, classMethod);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		return classMethod;
	}

	public MethodInfo getInvokerMethod(String className, String method, int invokeLineNumber) {
		String signal = String.format("%s.%s.%d", className, method, invokeLineNumber);
		if (this.requestedMethods.containsKey(signal)){
			return requestedMethods.get(signal);
		}
		ClassMethod classMethod = this.getClassMethod(className);
		MethodInfo invokerMethod = classMethod.getInvokerMethod(method, invokeLineNumber);
		this.requestedMethods.put(signal, invokerMethod);
		return invokerMethod;
	}

	public MethodInfo getInvokerMethod(StackTraceElement codeTrace) {
		return this.getInvokerMethod(codeTrace.getClassName(), codeTrace.getMethodName(), codeTrace.getLineNumber());
	}

	/*
	 * *********** static of class **********
	 */
	private static ClassMethodManager INSTANCE = new ClassMethodManager();

	public static ClassMethodManager getIntance() {
		return INSTANCE;
	}

}
