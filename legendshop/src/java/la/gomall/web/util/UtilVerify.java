package la.gomall.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilVerify {

	/**
	 * 是手机或email
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobilesOrEmail(String str) {
		if (isMobileNO(str) || isEmail(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		String regex = "^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
		return match(regex, mobiles);
	}

	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		str = replaceStr(str);

		Pattern p = Pattern.compile("\\s*|\t|\r|\n|\'|\"");
		Matcher m = p.matcher(str);
		if (m.replaceAll("").length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符替换
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceStr(String str) {
		str = str.replaceAll("\\|", "");
		str = str.replaceAll("\\^", "");
		// str = new MD5Util().getMD5ofStr(str);
		return str;
	}

	/**
	 * 通用的正则匹配
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 是中文名
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCnName(String str) {
		String regex = "([\u4E00-\u9FA5]{2,6})";
		return match(regex, str);
	}

	/**
	 * 是否邮箱
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}

 
 

}
