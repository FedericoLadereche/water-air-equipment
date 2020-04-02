package beans;

public class SeguraSatelitalData {
	private String norden;
	private String estacion;
	private String fecha;
	private String motivo;
	private String canal;
	
	public SeguraSatelitalData(String norden, String estacion, String fecha, String motivo, String canal) {
		this.norden = norden;
		this.estacion = estacion;
		this.fecha = fecha;
		this.motivo = motivo;
		this.canal = canal;
	}
	
	public String getNorden() {
		return norden;
	}

	public String getEstacion() {
		return estacion;
	}

	public String getFecha() {
		return fecha;
	}

	public String getMotivo() {
		return motivo;
	}

	public String getCanal() {
		return canal;
	}

}
