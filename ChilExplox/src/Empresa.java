import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Empresa {
	private String nombre;
	private String RUT;
	private Map<Integer, Sucursal> sucursales;
	private Map<Integer, MedioDeTransporte> flota;
	private Map<Integer, Pedido> pedidos;
	private Map<Integer, Encomienda> encomiendas;

	
	public Empresa (String nombre, String rut) {
		this.nombre = nombre;
		this.RUT = rut;
		this.sucursales = new HashMap<Integer, Sucursal>();
		this.flota = new HashMap<Integer, MedioDeTransporte>();
		this.pedidos = new HashMap<Integer, Pedido>();
		this.encomiendas = new HashMap<Integer, Encomienda>();
	}
	
	public void AgregarSucursal (int id, Sucursal newSucursal) {
		this.sucursales.put(id, newSucursal);
	}

	public void AgregarPedido(Pedido pedido) {
		pedidos.put(pedido.GetId(), pedido);
	}

	public void GetSucursal(int id) {
		return sucursales.get(id);
	}

	public Pedido GetPedido (int id) {
		return pedidos.get(id);
	}

	public void AgregarEncomienda(Encomienda encomienda) {
		encomiendas.put(encomienda.GetId(), encomienda);
	}

	public Encomienda GetEncomienda (int id) {
		return encomiendas.get(id);
	}
}
