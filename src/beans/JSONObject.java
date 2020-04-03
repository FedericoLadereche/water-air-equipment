package beans;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class JSONObject {
	
	protected String id_contrato;
	protected String datetime;
	protected ArrayList data;
	
	
	public JSONObject(String idContrato, String dateTime, Object data) {
		
		this.id_contrato = idContrato;
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.datetime = dateTime;
//		fillDataFromSondeData(data);
	}

	protected abstract void fillDataFromSondeData(Object data);
	
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
