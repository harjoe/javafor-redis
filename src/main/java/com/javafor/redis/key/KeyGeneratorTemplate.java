package com.javafor.redis.key;

import java.io.Serializable;
import java.util.Date;

import com.javafor.jse.JacksonUtils;
import com.javafor.jse.MD5;
import com.javafor.jse.Pair;

/***
 * Redis key 生成器,
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @lastet 2016年6月13日
 * @lastet Dec 9, 2016 by Harjoe;
 */
public class KeyGeneratorTemplate implements KeyGenerator {

	public KeyGeneratorTemplate() {
		// none
	}

	/**
	 * 参数构适方式
	 * @param value
	 * @return
	 */
	private String getArgComposite(Object value) {
		String json = JacksonUtils.toJson(value);
		json = json.replace("[", "");
		json = json.replace("]", "");
		json = json.replaceAll("\"", "");
		json = json.replaceAll("\"", "");
		json = json.replaceAll("#", "");
		// json = json.replace("*", "");
		// 如果太长就是进行MD5,
		if (json.length() > 32)
			json = MD5.getEncode(json);
		return json;
	}

	/**
	 * 构成参数部分
	 * @param args
	 * @return
	 */
	private String getArgsKey(Object... args) {
		String key = "";
		for (Object value : args) {
			String json = this.getArgComposite(value);
			key += String.format("%s##", json);
		}
		return key;
	}

	/**
	 * 根据参数, 匹配模式,
	 * @param args
	 * @return
	 */
	private String getArgsPattern(Object... args) {
		String key = "";
		for (Object value : args) {
			if (value == null)
				key += "*##";
			else
				key += String.format("%s##", this.getArgComposite(value));
		}
		key += "*";
		return key;
	}

	/**
	 * for Entity, like this: com.presist.Student.id
	 * 
	 **/
	@Override
	public String getKey(String signal, Serializable identity) {
		if (identity == null)
			return String.format("error.%s.identity.%s", signal, new Date().toString());
		return String.format("%s.identity=%s", signal, identity.toString());
	}

	@Override
	public String getKey(String signal, KeySeat keySeat) {
		if (keySeat == null)
			return String.format("error.%s.seat.%s", signal, new Date().toString());
		//
		for (Pair<String, String> element : keySeat.getMap()) {
			signal += String.format(".%s=%s", element.getP(), element.getQ());
		}
		return signal;
	}

	/**
	 * for service
	 */
	@Override
	public String getKey(String signal, String method, Object... args) {
		if (method == null || method.equals(""))
			return String.format("error.%s.%s.%s", signal, method, new Date().toString());
		//
		String argsJson = this.getArgsKey(args);
		return String.format("%s.%s(%s)", signal, method, argsJson);
	}

	@Override
	public String getKey(String signal, String method, long timeout, Object... args) {
		if (method == null || method.equals(""))
			return String.format("error.%s.%s.%s-d", signal, method, new Date().toString(), timeout);
		//
		String argsJson = this.getArgsKey(args);
		return String.format("%s.%s(%s)-%d", signal, method, argsJson, timeout);
	}

	@Override
	public String getPrefix(String signal) {
		return String.format("%s", signal);
	}

	@Override
	public String getPrefix(String signal, String method) {
		return String.format("%s.%s", signal, method);
	}

	@Override
	public String getPattern(String signal) {
		return String.format("%s.*", signal);
	}

	/**
	 * 根据其字段得到通配key.
	 * @param classSignal
	 * @param property
	 * @param value
	 * @return
	 */
	@Override
	public String getPattern(String signal, KeySeat keySeat) {
		if (keySeat == null)
			return String.format("error.%s.seat.%s", signal, new Date().toString());
		//
		for (Pair<String, String> element : keySeat.getMap()) {
			signal += String.format(".%s=%s", element.getP(), element.getQ());
		}
		return signal;
	}

	@Override
	public String getPattern(String signal, String method) {
		return String.format("%s.%s.*", signal, method);
	}

	/**
	 * 通配模式
	 */
	@Override
	public String getPattern(String signal, String method, Object... args) {
		String argsKeys = this.getArgsPattern(args);
		String key = String.format("%s.%s(%s)*", signal, method, argsKeys);
		return key;
	}

}
