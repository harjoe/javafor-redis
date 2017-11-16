package com.javafor.redis.spring.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Nov 9, 2017
 **/
public final class JoinPointUtils {

	private JoinPointUtils() {
		// private
	}

	/**
	 * get target object
	 * @param point
	 * @return
	 */
	public static Object getTarget(ProceedingJoinPoint point) {
		return point.getTarget();
	}

	/**
	 * get target class
	 * @param point
	 * @return
	 */
	public static Class<?> getTargetClass(ProceedingJoinPoint point) {
		return point.getTarget().getClass();
	}

	public static String getMethodArgs(ProceedingJoinPoint point) {
		Object[] objects = point.getArgs();
		String parameters = "";
		for (Object object : objects)
			parameters = parameters + String.format("%s, ", object);
		return parameters;
	}
	
	public static String getTargetFunction(ProceedingJoinPoint point) {
		String targetClassName = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();
		String methodArgs = getMethodArgs(point);

		return String.format("%s.%s(%s)", targetClassName, methodName, methodArgs);
	}

	
	/**
	 * get method name
	 * @param point
	 * @return
	 */
	public static String getMethodName(ProceedingJoinPoint point) {
		return point.getSignature().getName();
	}
	
	public static Method getMethod(ProceedingJoinPoint point) throws NoSuchMethodException, SecurityException {
		Signature signature = point.getSignature();
		if (!(signature instanceof MethodSignature)) {
			throw new IllegalArgumentException();
		}
		MethodSignature methodSignature = (MethodSignature) signature;
		Class<?> clazz = point.getTarget().getClass();
		return clazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
	}
	

}
