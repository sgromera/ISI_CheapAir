
public class Airport {
	private String codigo,nombre,ciudad,pais;
	
	public Airport(String cod, String name, String pais) {
		this.codigo = cod;
		this.nombre = name;
		this.pais = pais;
	}
	
	public Airport(String cod, String name) {
		this.codigo = cod;
		this.nombre = name;
	}
	
	public Airport() {}
	
	public void setNombre(String name) {
		this.nombre = name;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getPais() {
		return this.pais;
	}
	
}
