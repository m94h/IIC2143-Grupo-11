import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Empresa {
	private String nombre;
	private String RUT;
	private Map<Integer, Sucursal> sucursales;
	private Map<String, Cliente> clientes;
	private Map<String, MedioDeTransporte> flota;
	private Map<Integer, Pedido> pedidos;
	private Map<Integer, Encomienda> encomiendas;

	
	public Empresa (String nombre, String rut) {
		this.nombre = nombre;
		this.RUT = rut;
		this.sucursales = new HashMap<Integer, Sucursal>();
		this.clientes = new HashMap<String, Cliente>();
		this.flota = new HashMap<String, MedioDeTransporte>();
		this.pedidos = new HashMap<Integer, Pedido>();
		this.encomiendas = new HashMap<Integer, Encomienda>();
	}
	
	public void AgregarSucursal (Sucursal newSucursal) {
		this.sucursales.put(newSucursal.GetId(), newSucursal);
	}

	public void AgregarCliente (Cliente newCliente) {
		this.clientes.put(newCliente.GetRut(), newCliente);
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

	public Map<Integer, Sucursal> GetScursales() {
		return this.sucursales;
	}

	public Sucursal GetSucursal(int id) {
		return this.sucursales.get(id);
	}

	public Pedido GetPedido (int id) {
		return this.pedidos.get(id);
	}

	public void AgregarEncomienda(Encomienda encomienda) {
		this.encomiendas.put(encomienda.GetId(), encomienda);
	}

	public Encomienda GetEncomienda (int id) {
		return this.encomiendas.get(id);
	}

	public void AgregarTransporte(MedioDeTransporte vehiculo){
		this.flota.put(vehiculo.GetPatente(), vehiculo);
	}

	public MedioDeTransporte GetTransporte(String patente){
		return this.flota.get(patente);
	}
}
