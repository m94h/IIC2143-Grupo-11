package backend;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Sucursal {
	private int id;
	private String direccion;
	private int telefono;
	private int capacidad;
	private Map<String, Empleado> empleados;

	/*
	 * Camiones disponibles para despachar
	 */
	private ArrayList<MedioDeTransporte> mediosArrivados;

	/*
	 * Camiones disponibles para cargar pedidos
	 */
	private ArrayList<MedioDeTransporte> mediosDisponibles;



	/*private ArrayList<Pedido> pedidos;*/
	private Map<Integer, Mensaje> mensajes;

	public Sucursal(int id, String direccion, int telefono, int capacidad) {
	    this.id = id;
	    this.Initialize(direccion, telefono, capacidad);
	  }

	private void Initialize (String direccion, int telefono, int capacidad) {
		this.direccion = direccion;
		this.telefono = telefono;
	    this.capacidad = capacidad;
	    this.empleados = new HashMap<String, Empleado>();
	    this.mediosArrivados = new ArrayList<MedioDeTransporte>();
	    this.mediosDisponibles = new ArrayList<MedioDeTransporte>();
	    this.mensajes = new HashMap<Integer, Mensaje>();
	}

	public int GetId() {
		return this.id;
	}

	public String GetDireccion() {
		return this.direccion;
	}

	public int GetTelefono() {
		return this.telefono;
	}

	public int GetCapacidad() {
		return this.capacidad;
	}

	public Map<String, Empleado> GetEmpleados() {
		return this.empleados;
	}

	public void AgregarEmpleado(Empleado empleado) {
		this.empleados.put(empleado.GetRut(), empleado);
	}

	public void CambiarDireccion(String nuevaDireccion){
		this.direccion = nuevaDireccion;
	}

	public void AgrandarSucursal(int nueva_capacidad){
		this.capacidad = this.capacidad + nueva_capacidad;
	}

	public ArrayList<MedioDeTransporte> GetMediosDisponibles() {
		return this.mediosDisponibles;
	}

	public ArrayList<MedioDeTransporte> GetMediosArrivados() {
		return this.mediosArrivados;
	}

	public void AgregarMedioArrivado(MedioDeTransporte medio){
		this.mediosArrivados.add(medio);
	}

	public void AgregarMedioDisponible(MedioDeTransporte medio){
		this.mediosDisponibles.add(medio);
	}

	public void SetMedioDisponible(MedioDeTransporte medio){
		this.mediosArrivados.remove(medio);
		this.mediosDisponibles.add(medio);
	}

	public void EnviarMedio(MedioDeTransporte medio){
		this.mediosDisponibles.remove(medio);
	}
	  /*
	  public ArrayList<Pedido> GetPedidos() {
		  return this.pedidos;
	  }

	  public boolean AgregarPedido(Pedido pedido){
	    if (pedidos.size() == 0 || pedido.GetUrgencia() == 1) {
	      pedidos.add(pedido);
	      return true;
	    }
	    for(int i = 0; i < pedidos.size(); i++){
	      if(pedido.GetUrgencia() > pedidos.get(i).GetUrgencia()) {
	        pedidos.add(i, pedido);
	        return true;
	      }
	    }
	    return false;
	  }*/

	public void RecibirMensaje(Mensaje mensaje) {
		this.mensajes.put(mensaje.GetId(), mensaje);
	}

	public Map<Integer, Mensaje> GetMensajes() {
		return mensajes;
	}

		public Mensaje GetMensaje(int id) {
		return this.mensajes.get(id);
	}

}
