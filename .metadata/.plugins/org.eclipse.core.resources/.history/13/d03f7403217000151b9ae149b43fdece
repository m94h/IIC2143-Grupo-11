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

		//leer archivo de empresa
		br = new BufferedReader(new FileReader("archivos/empresa.data"));

		sCurrentLine = br.readLine();
		parametros = sCurrentLine.split(",");

		//instanciar clase empresa
		this.empresa = new Empresa(parametros[0], parametros[1]); //nombre, rut
		this.precioPorKilo = parametros[2];
		this.id_pedido = parametros[3];
		this.id_encomienda = parametros[4];		

		//leer archivo de sucursales
		br = new BufferedReader(new FileReader("archivos/sucursales.data"));
		
		while ((sCurrentLine = br.readLine()) != null) {
			parametros = sCurrentLine.split(",");

			sucursal = new Sucursal(parametros[0], parametros[1], parametros[2], parametros[3]); //id, sucursal, telefono, capacidad
			this.empresa.AgregarSucursal(sucursal);

		}

		//leer archivo de empleados
		br = new BufferedReader(new FileReader("archivos/empleados.data"));
		
		while ((sCurrentLine = br.readLine()) != null) {
			parametros = sCurrentLine.split(",");

			Empleado empleado;
			int sucursal_id;

			if (parametros[0] == "venta") {
				sucursal_id = parametros[0];
				empleado_id = parametros[1];
				empleado = new OperarioVenta(parametros[1], parametros[2], parametros[3], parametros[4]);
			}

			this.empresa.GetSucursal(sucursal_id).AgregarEmpleado(empleado_id, empleado);

		}

		
		
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
