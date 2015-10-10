//Fachada, Sistema
//Será una clase Singleton
public class Sistema {
	private static Sistema INSTANCE;
	private int precioPorKilo;
	private int id_pedido;
	private int id_encomienda;
	private Empresa empresa;

	//Constructor privado por singleton
	private Sistema() {
		//leer archivos

	}

	//Get instance para singleton
	public Sistema GetInstance() {
		return INSTANCE;
	}
	
	public int GetPrecioPorKilo() {
		return precioPorKilo;
	}

	public int Get_id_pedido() {
		int valor = id_pedido;
		id_pedido++;
		return valor;
	}

	public  int Get_id_encomienda() {
		int valor = id_encomienda;
		id_encomienda++;
		return valor;
	}


	// Metodos de Empresa
	public  void AgregarPedido(Pedido pedido) {
		empresa.AgregarPedido(pedido);
	}

	public  Pedido GetPedido (int id) {
		return empresa.GetPedido(id);
	}

	public  void AgregarEncomienda(Encomienda encomienda) {
		empresa.AgregarEncomienda(encomienda);
	}

	public  Encomienda GetEncomienda (int id) {
		return empresa.GetEncomienda(id);
	}
}
