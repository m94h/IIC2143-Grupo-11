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
	 * Camiones disponibles para cargar pedidos
	 */
	private ArrayList<MedioDeTransporte> mediosEsperando;
	
	/*
	 * Camiones listos para ser despachados
	 */
	private ArrayList<MedioDeTransporte> mediosListos;
	
	
	/*private ArrayList<Pedido> pedidos;*/
	private ArrayList<Mensaje> cola_mensajes;
	
	public Sucursal(int id, String direccion, int telefono, int capacidad) {
	    this.id = id;
	    this.Initialize(direccion, telefono, capacidad);
	  }

	private void Initialize (String direccion, int telefono, int capacidad) {
		this.direccion = direccion;
		this.telefono = telefono;
	    this.capacidad = capacidad;
	    this.empleados = new HashMap<String, Empleado>();
	    this.mediosEsperando = new ArrayList<MedioDeTransporte>();
	    this.mediosListos = new ArrayList<MedioDeTransporte>();
	    this.cola_mensajes = new ArrayList<Mensaje>();
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
	
	public ArrayList<MedioDeTransporte> GetMedioEsperando() {
		return this.mediosEsperando;
	}
	
	public void AgregarMedioEsperando(MedioDeTransporte medio){
		this.mediosEsperando.add(medio);
	}
	
	public void AgregarMedioListo(MedioDeTransporte medio){
		this.mediosEsperando.remove(medio);
		this.mediosListos.add(medio);
	}
	  
	public void EnviarCamion(MedioDeTransporte medio){
		this.mediosListos.remove(medio);
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
		this.cola_mensajes.add(mensaje);
	}
	
	public Mensaje GetUltimoMensaje() {
		if (this.cola_mensajes.size() > 0) {
			//Get el primer mensaje y luego removerlo de la lista
			Mensaje ret = this.cola_mensajes.get(0);
			this.cola_mensajes.remove(0);
			return ret;
		}
		return null;
	}

}
