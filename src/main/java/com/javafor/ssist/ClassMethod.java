package com.javafor.ssist;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 类的方法集
 *
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @review Nov 10, 2017 by Harjoe;
 **/
public class ClassMethod {

	private static ClassPool CLASS_POOL;

	static {
		CLASS_POOL = ClassPool.getDefault();
		ClassClassPath classPath = new ClassClassPath(new ClassMethod().getClass());
		CLASS_POOL.insertClassPath(classPath);
	}

	private String className;

	private List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();

	private void checkCodeStyle() {
		for (int i = 0; i < this.methodInfos.size() - 1; i++) {
			for (int j = i + 1; j < this.methodInfos.size(); j++) {
				if (methodInfos.get(i).getLineNumber().equals(methodInfos.get(j).getLineNumber()))
					throw new RuntimeException("don't write more lines code in a line.");
			}
		}
	}

	public String getClassName() {
		return this.className;
	}

	public void load(String className) throws NotFoundException {
		this.className = className;
		CtClass cc = CLASS_POOL.get(className);
		CtMethod[] methods = cc.getDeclaredMethods();
		for (CtMethod method : methods) {
			MethodInfo methodLine = new MethodInfo(method.getName(), method.getMethodInfo().getLineNumber(0));
			this.methodInfos.add(methodLine);
		}
		// check
		this.checkCodeStyle();
	}

	public List<MethodInfo> getMethodInfos(String methodName) {
		List<MethodInfo> infos = new ArrayList<MethodInfo>();
		for (MethodInfo element : this.methodInfos) {
			if (methodName.equals(element.getMethodName())) {
				infos.add(element);
			}
		}
		return infos;
	}

	/**
	 * 找到对应的方法
	 * @param methodName
	 * @param invokerLineNumber
	 * @return
	 */
	public MethodInfo getInvokerMethod(String methodName, int invokerLineNumber) {
		List<MethodInfo> infos = this.getMethodInfos(methodName);
		if (infos.size() == 0)
			return null;
		if (infos.size() == 1)
			return infos.get(0);
		//
		int max = 0;
		for (MethodInfo element : infos) {
			if (element.getLineNumber().intValue() > invokerLineNumber)
				continue;
			int temp = element.getLineNumber();
			if (temp > max)
				max = temp;
		}
		return new MethodInfo(methodName, max);
	}
}
