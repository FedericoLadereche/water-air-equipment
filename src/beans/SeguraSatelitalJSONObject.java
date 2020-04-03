package beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class SeguraSatelitalJSONObject extends JSONObject {

	public SeguraSatelitalJSONObject(String idContract, String date, Object segruraData) throws ParseException {
		super(idContract, date, segruraData);

		SeguraSatelitalData seguraSateliteData = (SeguraSatelitalData) segruraData;

		date = formatDate(seguraSateliteData.getFecha());
		
		idContract = getContractNumber(seguraSateliteData.getCanal());
		
		this.datetime = date;
		this.id_contrato = idContract;
		this.fillDataFromSondeData(segruraData);
	}


	private String formatDate(String seguraSateliteDate) throws ParseException {
		
		String stringForDate = seguraSateliteDate.replaceAll("\"", "");
		Instant instant = Instant.parse(stringForDate);
		Date dateFromSonde = Date.from(instant);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return dateFormat.format(dateFromSonde);
	}
	
	private static String getContractNumber(String canal) {
		return Integer.valueOf(canal) < 45 ? "545":"624";
	}

	@Override
	protected void fillDataFromSondeData(Object data) {
		HashMap<Integer, String> parametersMap = new HashMap<>();
		parametersMap = initializeMap(parametersMap);
		SeguraSatelitalData seguraSateliteData = (SeguraSatelitalData) data;

		this.data = new ArrayList();
		ArrayList parameterdata = new ArrayList<>();

		parameterdata.add("6");
		parameterdata.add(parametersMap.get(Integer.valueOf(seguraSateliteData.getCanal())));
		parameterdata.add(seguraSateliteData.getDato());
		parameterdata.add("1");
		parameterdata.add("1");

		this.data.add(parameterdata);

	}
	
	private HashMap<Integer, String> initializeMap(HashMap<Integer, String> parametersMap) {
		parametersMap.put(0, "velocidad_viento");
		parametersMap.put(2, "dv");
		parametersMap.put(29, "t");
		parametersMap.put(30, "ph");
//		this.parametersMap.put(31, "ph");
		parametersMap.put(32, "predox");
		parametersMap.put(33, "turbidez");
		parametersMap.put(34, "sst");
		parametersMap.put(35, "rdoc");
		parametersMap.put(36, "rdos");
		parametersMap.put(37, "pao");
		parametersMap.put(38, "conduc");
		parametersMap.put(39, "cee");
		parametersMap.put(40, "sal");
		parametersMap.put(41, "sdt");
		parametersMap.put(42, "resistividadp");
		parametersMap.put(43, "densidad");
		parametersMap.put(44, "voltajee");
		parametersMap.put(45, "t");
		parametersMap.put(46, "bgaf");
		parametersMap.put(47, "bgac");
		parametersMap.put(48, "clorofilaaf");
		parametersMap.put(49, "clorofilaa");
		parametersMap.put(50, "voltajee");
		parametersMap.put(94, "velocidad_viento");
		
		return parametersMap;
	}

}
