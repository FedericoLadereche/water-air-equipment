package common;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import beans.JSONObject;
import beans.SeguraSatelitalData;
import beans.SeguraSatelitalJSONObject;
import beans.SondeData;
import beans.SondeDataJSONObject;
import common.exceptions.IncomeMessageFormat;

public class JSONConverter {
	
	public static String createJSONSondeDataFromObject(String msgRecivedFromClient) {
		try {
			JSONObject sondeDataJSONObject = createSondeDataJSONFromMessage(msgRecivedFromClient);
			Gson gson = new Gson();
			String jsonString = gson.toJson(sondeDataJSONObject);

			return jsonString;
			
		} catch (IncomeMessageFormat e) {
			e.printStackTrace();
			return "";
		}

	}

	private static JSONObject createSondeDataJSONFromMessage(String msgRecivedFromClient) throws IncomeMessageFormat {
		SondeData sondeData = createSondeDataObject(msgRecivedFromClient);

		return createJSONContractObject(sondeData);
	}

	private static SondeData createSondeDataObject(String msgRecivedFromClient) throws IncomeMessageFormat {
		String sondeDataContainer[] = msgRecivedFromClient.split(" ");
		
		if(sondeDataContainer.length == 1)
			throw new IncomeMessageFormat("Formato de mensaje inválido (ejemplo # 14.58 10.28 0.00)");
		else {
			String batteryPower = sondeDataContainer[1];
			String cablePower = sondeDataContainer[2];
			String pressure = sondeDataContainer[3];
	
			return new SondeData(batteryPower, cablePower, pressure);
		}
	}

	private static SondeDataJSONObject createJSONContractObject(SondeData sondeData) {
		Date date = new Date();
		
		return new SondeDataJSONObject("496", date, sondeData);
	}
	
	public static String createJSONSeguraSatelitalDataFromObject(String msgRecivedFromClient) {
		try {
			JSONObject seguraSatelitalDataJSONObject = createSeguraSatelitalJSONFromMessage(msgRecivedFromClient);
			Gson gson = new Gson();
			String jsonString = gson.toJson(seguraSatelitalDataJSONObject);

			return jsonString;
			
		} catch (IncomeMessageFormat e) {
			e.printStackTrace();
			return "";
		}

	}

	private static JSONObject createSeguraSatelitalJSONFromMessage(String msgRecivedFromClient) throws IncomeMessageFormat {
		SeguraSatelitalData seguraSatelitalData = createSeguraSatelitalDataObject(msgRecivedFromClient);

		return createSeguraSatelitalJSONContractObject(seguraSatelitalData);
	}

	private static SeguraSatelitalData createSeguraSatelitalDataObject(String msgRecivedFromClient) throws IncomeMessageFormat {
		String norden = "";
		String estacion = "";
		String fecha = "";
		String motivo = "";
		String canal = "";
		
		String json = msgRecivedFromClient;
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
		for (Map.Entry<String, JsonElement> entry: entries) {
			switch(entry.getKey().toUpperCase()) {
			case "N_ORDEN":
				norden = entry.getValue().toString();
				break;
			case "ESTACION":
				estacion = entry.getValue().toString();
				break;
			case "FECHA":
				fecha = entry.getValue().toString();
				break;
			case "MOTIVO":
				motivo = entry.getValue().toString();
				break;
			case "CANAL":
				canal = entry.getValue().toString();
				break;
			default:
				break;			
			}
		}
		
		return new SeguraSatelitalData(norden, estacion, fecha, motivo, canal);
	}

	private static SeguraSatelitalJSONObject createSeguraSatelitalJSONContractObject(SeguraSatelitalData seguraData) {
		Date date = new Date();
		
		return new SeguraSatelitalJSONObject("496", date, seguraData);
	}

}
