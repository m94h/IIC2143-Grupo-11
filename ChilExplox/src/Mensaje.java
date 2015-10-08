
public class Mensaje {
	
	private String texto;
	private Sucursal origen;
	private Sucursal destino;
	private Empleado creadoPor;
	
	public Mensaje (String texto, Sucursal origen, Sucursal destino, Empleado creador) {
		this.texto = texto ;
		this.origen = origen;
		this.destino = destino;
		this.creadoPor = creador;
	}
	
	public String GetTexto() {return texto;}
	public Sucursal GetOrigen() {return origen;}
	public Sucursal GetDestino() {return destino;}	
}
