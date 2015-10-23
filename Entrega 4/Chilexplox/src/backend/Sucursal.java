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
	private ArrayList<Camion> camionesEsperando;
	
	/*
	 * Camiones listos para ser despachados
	 */
	private ArrayList<Camion> camionesListos;
	
	
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
	    this.camionesEsperando = new ArrayList<Camion>();
	    this.camionesListos = new ArrayList<Camion>();
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
	
	public ArrayList<Camion> GetCamionesEsperando() {
		return this.camionesEsperando;
	}
	
	public void AgregarCamionEsperando(Camion camion){
		this.camionesEsperando.add(camion);
	}
	
	public void AgregarCamionListo(Camion camion){
		this.camionesEsperando.remove(camion);
		this.camionesListos.add(camion);
	}
	  
	public void EnviarCamion(Camion camion){
		this.camionesListos.remove(camion);
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
