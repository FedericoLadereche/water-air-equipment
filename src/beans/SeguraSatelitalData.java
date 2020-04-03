package beans;

public class SeguraSatelitalData {
	private String norden;
	private String estacion;
	private String fecha;
	private String motivo;
	private String canal;
	private String dato;
	
	public SeguraSatelitalData(String norden, String estacion, String fecha, String motivo, String canal, String dato) {
		this.norden = norden;
		this.estacion = estacion;
		this.fecha = fecha;
		this.motivo = motivo;
		this.canal = canal;
		this.dato = dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
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
	
	public String getDato() {
		return dato;
	}

}
