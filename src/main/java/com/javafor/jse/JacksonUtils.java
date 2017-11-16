package com.javafor.jse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Jackson Util.
 * 
 * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @since Sep 21, 2016
 * @release Sep 21, 2016 by Harjoe;
 **/
public final class JacksonUtils {

	/**
	 * private.
	 */
	private JacksonUtils() {
		// private
	}

	/**
	 * Object to json string.
	 * 
	 */
	public static String toJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析 json.
	 * 
	 * @param json josn字符串
	 * @param targetClass 解析目标对象类型
	 */
	public static <E> E parse(String json, Class<E> targetClass) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, targetClass);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static String getValue(String content, String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode node = objectMapper.readTree(content);
			return node.get(key).toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
