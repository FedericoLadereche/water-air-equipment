package beans;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class SondeDataJSONObject {
	
	private String id_contrato;
	private String datetime;
	private ArrayList data;
	
	public SondeDataJSONObject(String idContrato, Date dateTime, SondeData sondeData) {
		
		this.id_contrato = idContrato;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.datetime = dateFormat.format(dateTime);
		fillDataFromSondeData(sondeData);
	}

	private void fillDataFromSondeData(SondeData sondeData) {
		
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
	
	public String getId_Contrato() {
		return id_contrato;
	}
	public String getDatetime() {
		return datetime;
	}
	public ArrayList<String> getData() {
		return data;
	}
	
}
