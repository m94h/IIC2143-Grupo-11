
public class Sistema {
	public static int precioPorKilo;
	public static int id_pedido;
	public static int id_encomienda;
	
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
}
