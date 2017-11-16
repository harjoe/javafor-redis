package com.javafor.redis.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面基类
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Nov 11, 2016
 **/
public class AspectProxy {
	
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		throw new UnsupportedOperationException();
	}

	public void doBefore(JoinPoint point) {
		throw new UnsupportedOperationException();
	}

	public void doAfter(JoinPoint point) {
		throw new UnsupportedOperationException();
	}

}
