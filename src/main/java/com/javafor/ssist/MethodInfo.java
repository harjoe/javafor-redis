package com.javafor.ssist;

/**
 * 
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @review Nov 10, 2017 by Harjoe;
 * @release V2.1.10; Nov 13, 2017 by Harjoe;
 **/
public class MethodInfo {

	private String methodName;

	private Integer lineNumber;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	/*
	 * ********** constructor **********
	 */
	
	public MethodInfo() {
		//null
	}

	public MethodInfo(String methodName, Integer lineNumber) {
		this.setMethodName(methodName);
		this.setLineNumber(lineNumber);
	}

	public String getSignal() {
		return String.format("%s.%d", this.methodName, this.lineNumber);
	}
}
