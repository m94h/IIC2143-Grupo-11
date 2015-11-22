package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Empresa {
	private String nombre;
	private String RUT;
	private Map<Integer, Sucursal> sucursales;
	private Map<String, Empleado> empleados;
	private Map<String, Cliente> clientes;
	private Map<String, MedioDeTransporte> flota;
	private Map<Integer, Pedido> pedidos;
	private Map<Integer, Encomienda> encomiendas;
	private ArrayList<MedioDeTransporte> mediosEnTransito;
	private Map<Integer, Error> errores;
	private Map<String, Viaje> viajes;


	public Empresa (String nombre, String rut) {
		this.nombre = nombre;
		this.RUT = rut;
		this.sucursales = new HashMap<Integer, Sucursal>();
		this.clientes = new HashMap<String, Cliente>();
		this.flota = new HashMap<String, MedioDeTransporte>();
		this.pedidos = new HashMap<Integer, Pedido>();
		this.encomiendas = new HashMap<Integer, Encomienda>();
		this.empleados = new HashMap<String, Empleado>();
		this.errores = new HashMap<Integer, Error>();
		this.mediosEnTransito = new ArrayList<MedioDeTransporte>();
		this.viajes = new HashMap<String, Viaje>();
	}

	public void AgregarSucursal (Sucursal newSucursal) {
		this.sucursales.put(newSucursal.GetId(), newSucursal);
	}

	public void AgregarCliente (Cliente newCliente) {
		this.clientes.put(newCliente.GetRut(), newCliente);
	}

	public void AgregarEmpleado (Empleado newEmpleado) {
		this.empleados.put(newEmpleado.GetRut(), newEmpleado);
	}

	public void AgregarPedido(Pedido pedido) {
		this.pedidos.put(pedido.GetId(), pedido);
	}

	public String GetNombre() {
		return this.nombre;
	}

	public String GetRut() {
		return this.RUT;
	}

	public Map<String, Cliente> GetClientes() {
		return this.clientes;
	}

	public Cliente GetCliente(String rut) {
		return this.clientes.get(rut);
	}

	public void BorrarCliente(String rut) {
		this.clientes.remove(rut);
	}

	public Map<String, Empleado> GetEmpleados() {
	    return this.empleados;
	}

	public Empleado GetEmpleado(String rut) {
		return this.empleados.get(rut);
	}

	public Map<Integer, Sucursal> GetSucursales() {
		return this.sucursales;
	}

	public Sucursal GetSucursal(int id) {
		return this.sucursales.get(id);
	}

	public Map<Integer, Pedido> GetPedidos() {
	    return this.pedidos;
	}

	public Pedido GetPedido (int id) {
		return this.pedidos.get(id);
	}

	public void AgregarEncomienda(Encomienda encomienda) {
		this.encomiendas.put(encomienda.GetId(), encomienda);
	}

	public Map<Integer, Encomienda> GetEncomiendas() {
	    return this.encomiendas;
	}

	public Encomienda GetEncomienda (int id) {
		return this.encomiendas.get(id);
	}

	public Map<String, MedioDeTransporte> GetTransportes() {
	    return this.flota;
	}

	public void AgregarTransporte(MedioDeTransporte vehiculo){
		this.flota.put(vehiculo.GetPatente(), vehiculo);
	}

	public MedioDeTransporte GetTransporte(String patente){
		return this.flota.get(patente);
	}
	
	public Map<Integer, Error> GetErrores() {
		return this.errores;
	}
	
	public Error GetError(int id) {
		return this.errores.get(id);
	}
	
	public void AgregarError(Empleado empleado, String mensaje) {
		Error error = new Error(empleado, mensaje);
		this.errores.put(error.GetID(), error);
	}

	/*
	 *  Agregar/eliminar de lista con camiones en transito
	 */

	public void AgregarMedioEnTransito(MedioDeTransporte medio){
		this.mediosEnTransito.add(medio);
	}

	public void EliminarMedioEnTransito(MedioDeTransporte medio){
		this.mediosEnTransito.remove(medio);
	}

	public ArrayList<MedioDeTransporte> GetMediosEnTransito() {
		return this.mediosEnTransito;
	}

	public void AgregarViaje(Viaje viaje) {
		this.viajes.put(viaje.GetPatente(), viaje);
	}

	public Map<String, Viaje> GetViajes() {
	    return this.viajes;
	}

}
