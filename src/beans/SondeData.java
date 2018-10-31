package beans;

public class SondeData {
	
	private String batteryPower;  
	private String cablePower;   
	private String pressure;
	
	public SondeData(String batteryPower, String cablePower, String pressure) {
		this.batteryPower = batteryPower;
		this.cablePower = cablePower;
		this.pressure = pressure;
	}
	
	public String getBatteryPower() {
		return batteryPower;
	}
	public String getCablePower() {
		return cablePower;
	}
	public String getPressure() {
		return pressure;
	}

}
