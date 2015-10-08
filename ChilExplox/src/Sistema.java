
public class Sistema {
	private static int precioPorKilo;
	private static int id_pedido;
	private static int id_encomienda;
	private static Empresa empresa;
	
	public static int GetPrecioPorKilo() {
		return precioPorKilo;
	}

	public static int Get_id_pedido() {
		int valor = id_pedido;
		id_pedido++;
		return valor;
	}

	public static int Get_id_encomienda() {
		int valor = id_encomienda;
		id_encomienda++;
		return valor;
	}


	// Metodos de Empresa
	public static void AgregarPedido(Pedido pedido) {
		empresa.AgregarPedido(pedido);
	}

	public static Pedido GetPedido (int id) {
		return empresa.GetPedido(id);
	}

	public static void AgregarEncomienda(Encomienda encomienda) {
		empresa.AgregarEncomienda(encomienda);
	}

	public static Encomienda GetEncomienda (int id) {
		return empresa.GetEncomienda(id);
	}
}
