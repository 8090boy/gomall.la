package  la.gomall.web.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class UtilSMS {

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	public static Boolean SendRandomForMob( String...args ) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");
		int mobile_code = (int) ((Math.random() * 9 + 1) * 1000); // 验证码
		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		if(args[1] != null &&  args[1].equals("1")){
			System.out.println(args[0] + "：已发送：" + content);
			return true;
		}
		
		NameValuePair[] data = {// 提交短信
				new NameValuePair("account", "cf_dapengma"),
				new NameValuePair("password", "qq611041314"), // 密码可以使用明文密码或使用32位MD5加密
				new NameValuePair("mobile", args[0]),
				new NameValuePair("content", content), };
		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			if (root.elementText("code").equalsIgnoreCase("2")) { // 短信提交成功
				return true;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}

}