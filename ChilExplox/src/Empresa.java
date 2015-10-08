import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Empresa {
	private String nombre;
	private String RUT;
	private List<Sucursal> sucursales;
	private List<MedioDeTransporte> flota;
	private Map<Integer, Pedido> pedidos;
	private Map<Integer, Encomienda> encomiendas;

	
	
	public Empresa (String nombre, String rut) {
		this.nombre = nombre;
		this.RUT = rut;
		this.sucursales = new ArrayList<Sucursal>();
		this.flota = new ArrayList<MedioDeTransporte>();
		this.pedidos = new HashMap<Integer, Pedido>();
		this.encomiendas = new HashMap<Integer, Encomienda>();
	}
	
	public void AgregarSucursal (Sucursal newSucursal) {
		this.sucursales.add(newSucursal);
	}

	public void AgregarPedido(Pedido pedido) {
		pedidos.put(pedido.GetId(), pedido);
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
