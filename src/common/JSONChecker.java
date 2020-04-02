package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONChecker {

	public static boolean isJSONValid(String testJSON) {
		String json = testJSON;
		Gson gson = new Gson();
		try {
		    Object obj = gson.fromJson(json, Object.class);
		    System.out.println("----------JSON RECIBIDO----------");
		    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(obj));
		    return true;
		} catch (Exception e) {
		    System.out.println("Invalid JSON format");
		    return false;
		}
	}
}
