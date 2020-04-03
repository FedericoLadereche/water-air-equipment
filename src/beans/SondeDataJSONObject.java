package beans;

import java.util.ArrayList;
import java.util.Date;

public class SondeDataJSONObject extends JSONObject{

	public SondeDataJSONObject(String idContrato, String dateTime, Object data) {
		super(idContrato, dateTime, data);
	}
	
	protected void fillDataFromSondeData(Object data) {
		SondeData sondeData = (SondeData)data;
		
		this.data = new ArrayList();
		ArrayList valueFieldBattery = new ArrayList<>();
		ArrayList valueFieldCable = new ArrayList<>();
		ArrayList valueFieldPressure = new ArrayList<>();
		
		valueFieldBattery.add("6");
		valueFieldBattery.add("bp");
		valueFieldBattery.add(sondeData.getBatteryPower());		
		valueFieldBattery.add("1");		
		valueFieldBattery.add("1");
		
		this.data.add(valueFieldBattery);
		
		valueFieldCable.add("6");
		valueFieldCable.add("cp");
		valueFieldCable.add(sondeData.getCablePower());		
		valueFieldCable.add("1");		
		valueFieldCable.add("1");
		
		this.data.add(valueFieldCable);
		
		valueFieldPressure.add("6");
		valueFieldPressure.add("pp");
		valueFieldPressure.add(sondeData.getPressure());		
		valueFieldPressure.add("1");		
		valueFieldPressure.add("1");
		
		this.data.add(valueFieldPressure);
		
	}
	
}
