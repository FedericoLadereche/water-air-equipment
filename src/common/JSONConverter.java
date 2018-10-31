package common;

import java.util.Date;

import com.google.gson.Gson;

import beans.SondeData;
import beans.SondeDataJSONObject;
import common.exceptions.IncomeMessageFormat;

public class JSONConverter {
	
	public static String createJSONFromObject(String msgRecivedFromClient) {
		try {
			SondeDataJSONObject sondeDataJSONObject;
			sondeDataJSONObject = createSondeDataJSONFromMessage(msgRecivedFromClient);
			Gson gson = new Gson();
			String jsonString = gson.toJson(sondeDataJSONObject);

			return jsonString;
		} catch (IncomeMessageFormat e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	private static SondeDataJSONObject createSondeDataJSONFromMessage(String msgRecivedFromClient) throws IncomeMessageFormat {
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

}
