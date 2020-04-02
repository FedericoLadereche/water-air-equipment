package beans;

import java.util.ArrayList;
import java.util.Date;

public class SeguraSatelitalJSONObject extends JSONObject{

	public SeguraSatelitalJSONObject(String idContrato, Date dateTime, Object data) {
		super(idContrato, dateTime, data);
	}

	@Override
	protected void fillDataFromSondeData(Object data) {
		SeguraSatelitalData seguraSateliteData = (SeguraSatelitalData)data;
		
		this.data = new ArrayList();
		ArrayList valueFieldNorden = new ArrayList<>();
		ArrayList valueFieldEstacion = new ArrayList<>();
		ArrayList valueFieldFecha = new ArrayList<>();
		ArrayList valueFieldMotivo = new ArrayList<>();
		ArrayList valueFieldCanal = new ArrayList<>();
		
		valueFieldNorden.add("6");
		valueFieldNorden.add("no");
		valueFieldNorden.add(seguraSateliteData.getNorden());		
		valueFieldNorden.add("1");		
		valueFieldNorden.add("1");
		
		this.data.add(valueFieldNorden);
		
		valueFieldEstacion.add("6");
		valueFieldEstacion.add("es");
		valueFieldEstacion.add(seguraSateliteData.getEstacion());		
		valueFieldEstacion.add("1");		
		valueFieldEstacion.add("1");
		
		this.data.add(valueFieldEstacion);
		
		valueFieldFecha.add("6");
		valueFieldFecha.add("fc");
		valueFieldFecha.add(seguraSateliteData.getFecha());		
		valueFieldFecha.add("1");		
		valueFieldFecha.add("1");
		
		this.data.add(valueFieldFecha);		
		
		valueFieldMotivo.add("6");
		valueFieldMotivo.add("mo");
		valueFieldMotivo.add(seguraSateliteData.getMotivo());		
		valueFieldMotivo.add("1");		
		valueFieldMotivo.add("1");
		
		this.data.add(valueFieldMotivo);		
		
		valueFieldCanal.add("6");
		valueFieldCanal.add("ca");
		valueFieldCanal.add(seguraSateliteData.getCanal());		
		valueFieldCanal.add("1");		
		valueFieldCanal.add("1");
		
		this.data.add(valueFieldCanal);		
	}
	
	

}
