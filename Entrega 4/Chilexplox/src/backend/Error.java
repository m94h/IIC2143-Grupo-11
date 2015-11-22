package backend;

import java.time.LocalDate;

public class Error {
	private int ID;
	
	private Empleado empleado;
	
	private LocalDate fecha;
	
	private String mensaje;
	
	public Error(Empleado empleado, String mensaje) {
		//Generar id del error
		this.ID = Sistema.GetInstance().Get_id_error();
		this.empleado = empleado;
		this.mensaje = mensaje;
		this.fecha = LocalDate.now();
	}
	
	public Error(int id, Empleado empleado, String mensaje) {
		this.ID = id;
		this.empleado = empleado;
		this.mensaje = mensaje;
		this.fecha = LocalDate.now();
	}
	
	public int GetID() {
		return this.ID;
	}
	
	public LocalDate GetFecha() {
		return this.fecha;
	}
	
	public Empleado GetEmpleado(){
		return this.empleado;
	}
	
	public String GetMensaje() {
		return this.mensaje;
	}
}
