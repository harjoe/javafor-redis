
package com.javafor.jse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
  * @author Harjoe; if you have any questions, please contact me with my blog or
 *         email, thanks.<br>
 *         blog: <a href="http://www.javafor.com">www.javafor.com</a><br>
 *         github: <a href="https://github.com/harjoe">www.github.com/harjoe</a><br>
 *         email: <a href="harjoe@hotmail.com">harjoe@hotmail.com</a><br>
 * @version 1.0 2013-5-9<br>
 * @version Jun 17, 2015; 2.0.0
 */
public final class MD5 {
	
	public static String getEncode(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bs = md.digest(input.getBytes());
			return toMD5String(bs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String toMD5String(byte[] bs) {
		char[] chars = new char[bs.length];
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			int val = ((int) bs[i]) & 0xff;
			if (val < 16)
				buffer.append("0");
			buffer.append(Integer.toHexString(val));
		}
		return buffer.toString();
	}
}
