package backend;

import java.time.LocalDate;

public class Viaje {

	private int id;
	
	private String patente_medio;
	
	private Empleado conductor;
	
	private Sucursal origen;
	
	private Sucursal destino;
	
	private LocalDate fechaSalida; 
	
	private LocalDate fechaLlegada = null; 
	
	public Viaje (int id, String patente, Empleado conductor, Sucursal origen, Sucursal destino, LocalDate fechaSalida) {
		this.id = id;
		this.patente_medio = patente;
		this.conductor = conductor;
		this.origen = origen;
		this.destino = destino;
		this.fechaSalida = fechaSalida;
	}
	
	public Viaje (int id, String patente, Empleado conductor, Sucursal origen, Sucursal destino, LocalDate fechaSalida, LocalDate fechaLlegada) {
		this.id = id;
		this.patente_medio = patente;
		this.conductor = conductor;
		this.origen = origen;
		this.destino = destino;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
	}
	
	public Viaje (String patente, Empleado conductor, Sucursal origen, Sucursal destino, LocalDate fechaSalida) {
		this.id = Sistema.GetInstance().Get_id_viaje();
		this.patente_medio = patente;
		this.conductor = conductor;
		this.origen = origen;
		this.destino = destino;
		this.fechaSalida = fechaSalida;
	}

	
	
	public int GetId() { return this.id; }

	public String GetPatente() {return this.patente_medio;}

	public Empleado GetConductor() {return this.conductor;}

	public Sucursal GetOrigen() {return this.origen;}

	public Sucursal GetDestino() {return this.destino;}	

	public LocalDate GetFechaSalida() {return this.fechaSalida;}	

	public LocalDate GetFechaLlegada() {return this.fechaLlegada;}	
	
	public void AvisarLlegada() {
		this.fechaLlegada = LocalDate.now();
	}
}
