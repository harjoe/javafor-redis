package com.javafor.redis.key;

import java.util.ArrayList;
import java.util.List;

import com.javafor.jse.Pair;


/**
 * Key Map.
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Dec 29, 2016
 **/
public class KeySeat {

	private List<Pair<String, String>> map = new ArrayList<Pair<String, String>>();

	/**
	 * 放入一个键值
	 * @param property
	 * @param value
	 */
	public void put(String property, String value) {
		Pair<String, String> element = new Pair<String, String>(property, value);
		this.map.add(element);
	}

	public void putIdentity(String value) {
		this.put(KeyConst.IDENTITY, value);
	}
	
	/**
	 * 占个位
	 * @return
	 */
	public void putSeat() {
		this.put("*", "*");
	}

	/**
	 * 占个空位
	 * @param property
	 */
	public void putSeat(String property) {
		this.put(property, "*");
	}
	
	public void putSeatIdentity() {
		this.put(KeyConst.IDENTITY, "*");
	}
	
	public void putMethod(String method) {
		Pair<String, String> element = new Pair<String, String>("#method", method);
		this.map.add(element);
	}

	public List<Pair<String, String>> getMap() {
		return map;
	}

}
