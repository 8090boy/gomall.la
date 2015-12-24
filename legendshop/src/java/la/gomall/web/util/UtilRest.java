package la.gomall.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class UtilRest {

	public static void restJson(String result, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("application/json");
		try {
			out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			System.out.println("JSON Error !");
			e.printStackTrace();
		}
	}

	public static void restJson(Object result, HttpServletResponse response) {
		JSONArray jsonArray = JSONArray.fromObject(result);
		PrintWriter out = null;
		response.setContentType("application/json");
		try {
			out = response.getWriter();
			out.write(jsonArray.toString());
		} catch (IOException e) {
			System.out.println("JSON Error !");
			e.printStackTrace();
		}
	}

}
