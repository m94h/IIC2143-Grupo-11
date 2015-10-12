import java.io.BufferedReader;
import java.io.FileReader;

//Fachada, Sistema
//Será una clase Singleton
public class Sistema {
	private static Sistema INSTANCE;

	// Estos no se si los dejamos aca o en empresa
	private int precioPorKilo;
	private int id_pedido;
	private int id_encomienda;
	private int id_sucursal;

	private Empresa empresa;

	//Constructor privado por singleton
	private Sistema() {

		//leer archivo de empresa
		BufferedReader br = new BufferedReader(new FileReader("archivos/empresa.data"));

		String sCurrentLine = br.readLine();
		String [] parametros = sCurrentLine.split(",");

		//instanciar clase empresa
		this.empresa = new Empresa(parametros[0], parametros[1]); //nombre, rut
		try {
			this.precioPorKilo = Integer.parseInt(parametros[2]);
			this.id_pedido = Integer.parseInt(parametros[3]);
			this.id_encomienda = Integer.parseInt(parametros[4]);
		}
		catch(Exception e) {
			// Error en los archivos	
		}

		//leer archivo de sucursales
		br = new BufferedReader(new FileReader("archivos/sucursales.data"));
		
		while ((sCurrentLine = br.readLine()) != null) {
			parametros = sCurrentLine.split(",");
			
			try {
				int id = Integer.parseInt(parametros[0]);
				String direccion = parametros[1];
				int telefono = Integer.parseInt(parametros[2]);
				int capacidad = Integer.parseInt(parametros[3]);
				Sucursal sucursal = new Sucursal(id, direccion, telefono, capacidad);
				this.empresa.AgregarSucursal(id, sucursal);
			}
			catch(Exception e) {
				// Error en los archivos
			}

		}

		//leer archivo de empleados
		br = new BufferedReader(new FileReader("archivos/empleados.data"));
		
		while ((sCurrentLine = br.readLine()) != null) {
			parametros = sCurrentLine.split(",");


			// Aqui hay que arreglar, no entiendo nada, el que hizo esto que me explique lo que trato de hacer
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
	public static Sistema GetInstance() {
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

	public int Get_id_encomienda() {
		int valor = id_encomienda;
		id_encomienda++;
		return valor;
	}

	public int Get_id_sucursal() {
		int valor = id_sucursal;
		id_sucursal++;
		return valor;
	}

	// Interaccion con usuario
	public int CrearPedido (OperarioVenta vendedor, Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
	    return vendedor.CrearPedido(cliente, origen, destino, urgencia);
	}

	public int CrearEncomienda (OperarioVenta vendedor, Pedido pedido, int peso, int volumen) {
	  	return vendedor.CrearEncomienda(pedido, peso, volumen);
	}

	public void EnviarMensaje (OperarioBodega creador, String texto, Sucursal destino) {
		Mensaje m = creador.CrearMensaje(texto, destino);
		// Enviar mensaje
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

	public void AgregarTransporte(MedioDeTransporte vehiculo){
		empresa.AgregarTransporte(vehiculo);
	}

	public MedioDeTransporte GetVehiculo (String patente){
		return empresa.GetTransporte(patente);
	}
}
