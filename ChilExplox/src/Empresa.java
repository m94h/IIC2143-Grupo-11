import java.util.ArrayList;
import java.util.List;

public class Empresa {
	private String nombre;
	private String RUT;
	private List<Sucursal> sucursales;
	private List<MedioDeTransporte> flota;
	private Map<int, Pedido> pedidos;
	private Map<int, Encomienda> encomiendas;
	private int precioPorKilo;
	private int id_pedido;
	private int id_encomienda;
	
	
	public Empresa (String nombre, String rut) {
		this.nombre = nombre;
		this.RUT = rut;
		this.sucursales = new ArrayList<Sucursal>();
		this.flota = new ArrayList<MedioDeTransporte>();
		this.pedidos = new HashMap<int, Pedido>();
		this.encomiendas = new HashMap<int, Encomienda>();
	}
	
	public void AgregarSucursal (Sucursal newSucursal) {
		this.sucursales.add(newSucursal);
	}

	public int GetPrecioPorKilo() {
		return precioPorKilo;
	}

	public int Get_id_pedido() {
		valor = id_pedido;
		id_pedido++;
		return valor;
	}

	public int Get_id_encomienda() {
		valor = id_encomienda;
		id_encomienda++;
		return valor;
	}

	public void AgregarPedido(Pedido pedido) {
		pedidos.put(pedido.id, pedido);
	}

	public Pedido GetPedido (int id) {
		pedidos.get(id);
	}

	public void AgregarEncomienda(Encomienda encomienda) {
		encomiendas.put(encomienda.id, encomienda);
	}

	public Encomienda GetEncomienda (int id) {
		encomiendas.get(id);
	}
}
