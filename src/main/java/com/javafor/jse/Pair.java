package com.javafor.jse;

import java.io.Serializable;

/**
 * 一对数据类型 Pair&ltP, Q&gt.
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @see Tern, Quaternion
 * @since 2016年6月23日
 * @param <P>
 * @param <Q>
 **/
public class Pair<P, Q> implements Serializable {

	/**
	 * serial version uid.
	 */
	private static final long serialVersionUID = -620693103159495877L;

	/**
	 * p value.
	 */
	private P pv;

	/**
	 * q value.
	 */
	private Q qv;

	/*
	 * ********** gets and sets **********
	 */

	public P getP() {
		return pv;
	}

	public void setP(P pv) {
		this.pv = pv;
	}

	public Q getQ() {
		return qv;
	}

	public void setQ(Q qv) {
		this.qv = qv;
	}

	/*
	 * ********** constructor **********
	 */
	public Pair() {
		// none
	}

	public Pair(P pv, Q qv) {
		this.pv = pv;
		this.qv = qv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (pv == null ? 0 : pv.hashCode());
		result = prime * result + (qv == null ? 0 : qv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (pv == null) {
			if (other.pv != null) {
				return false;
			}
		} else if (!pv.equals(other.pv)) {
			return false;
		}
		if (qv == null) {
			if (other.qv != null) {
				return false;
			}
		} else if (!qv.equals(other.qv)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断 是否类型 是否一致.
	 */
	public boolean isInstance(Class<?> classA, Class<?> classB) {
		return classA.isInstance(pv) && classB.isInstance(qv);
	}
}
