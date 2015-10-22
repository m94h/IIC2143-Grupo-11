package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;


//Fachada, Sistema
//Sera una clase Singleton
public class Sistema {
	//Se define la instancia para que sea singleton
	private static Sistema INSTANCE = new Sistema();

	// Estos no se si los dejamos aca o en empresa
	private int precioPorKilo;
	//Los id a continuacion son contadores para cuando se cree una nueva...
	private int id_pedido;
	private int id_encomienda;
	private int id_sucursal;

	private Empresa empresa;
	
	//Valores de interaccion con usuario
	private Empleado usuario_loged;
	private Sucursal sucursal_loged;

	//Definir buferes para lectura
	private String sCurrentLine = "";
	private	BufferedReader br = null;
	private	String [] parametros;

	//Constructor privado por singleton
	private Sistema() {
		this.CargarTodo();
	}

	//Get instance para singleton
	public static Sistema GetInstance() {
		return INSTANCE;
	}
	
	public int GetPrecioPorKilo() {
		return precioPorKilo;
	}
	
	public Sucursal GetSucursalLoged() {
		return this.sucursal_loged;
	}
	
	public Empleado GetUsuarioLoged() {
		return this.usuario_loged;
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

	public Map<Integer, Sucursal> GetSucursales() {
		return this.empresa.GetSucursales();
	}

	public int Get_id_sucursal() {
		int valor = id_sucursal;
		id_sucursal++;
		return valor;
	}

	// Interaccion con usuario
	public int CrearPedido (OperarioVenta vendedor, Cliente cliente, Sucursal origen, Sucursal destino, PrioridadPedido urgencia) {
	    return vendedor.CrearPedido(cliente, origen, destino, urgencia); // Retorna el id del pedido
	}

	public int CrearEncomienda (OperarioVenta vendedor, Pedido pedido, int peso, int volumen) {
	  	return vendedor.CrearEncomienda(pedido, peso, volumen); // Retorna el id de la encomienda
	}

	public void EnviarMensaje (OperarioBodega creador, String texto, Sucursal destino) {
		creador.CrearMensaje(texto, destino);
	}

	public void AgregarCliente (String rut, String nombre, int telefono, String direccion) {
		Cliente c = new Cliente (rut, nombre, telefono, direccion);
		empresa.AgregarCliente(c);
	}

	/*
	public String SolicitarCamion(OperarioBodega solicitador){
		solicitador.PedirCamion();
	}
	*/


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
	
	public boolean LogIn(String rut, String clave) {
		if (this.empresa.GetEmpleados().get(rut) != null) {
			Empleado empleado_loged = this.empresa.GetEmpleados().get(rut);			
			if (empleado_loged.CheckLogin(clave)) {
				//login correcto
				this.usuario_loged = empleado_loged;
				this.sucursal_loged = empleado_loged.GetSucursal();
				
				//retornar que se logeo de manera correcta
				return true;
			}
		}
		return false;
	}


	private void CargarEmpresa(){
		try {
			br = new BufferedReader(new FileReader("archivos/empresa.data"));
		} catch (FileNotFoundException e3) {
			System.out.println(e3.getMessage()); // Archivo no encontrado
		}

		try {
			sCurrentLine = br.readLine();
		} catch (IOException e3) {
			// Error en la lectura
		}
		parametros = sCurrentLine.split(";");

		//instanciar clase empresa
		this.empresa = new Empresa(parametros[0], parametros[1]); //nombre, rut
		try {
			this.precioPorKilo = Integer.parseInt(parametros[2]);
			this.id_pedido = Integer.parseInt(parametros[3]);
			this.id_encomienda = Integer.parseInt(parametros[4]);
		}
		catch(Exception e) {
			// Error en el parseo	
		}
	}

	private void CargarSucursales() {
		try {
			br = new BufferedReader(new FileReader("archivos/sucursales.data"));
		} catch (FileNotFoundException e2) {
			// Archivo no encontrado
		}

		int id_sucursal;
		String direccion;
		int telefono;
		int capacidad;
		Sucursal sucursal;
		
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				parametros = sCurrentLine.split(";");
				try {
					id_sucursal = Integer.parseInt(parametros[0]);
					direccion = parametros[1];
					telefono = Integer.parseInt(parametros[2]);
					capacidad = Integer.parseInt(parametros[3]);
					sucursal = new Sucursal(id_sucursal, direccion, telefono, capacidad);
					this.empresa.AgregarSucursal(sucursal);
				}
				catch(Exception e) {
					// Error en el parseo
				}
			}
		} catch (IOException e2) {
			// Error en la lectura
		}
	}

	private void CargarEmpleados() {
		try {
			br = new BufferedReader(new FileReader("archivos/empleados.data"));
		} catch (FileNotFoundException e1) {
				e1.getMessage();
		}
		
		int id_sucursal;
		int telefono;
		Sucursal sucursal;
		Empleado empleado;
		String empleado_id;
		String tipo_empleado;
		String nombre;
		int sueldo;
		String clave;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					id_sucursal = Integer.parseInt(parametros[0]);
					empleado_id = parametros[1]; // es el rut
					tipo_empleado = parametros[2];
					nombre = parametros[3];
					telefono = Integer.parseInt(parametros[4]);
					sueldo = Integer.parseInt(parametros[5]);
					sucursal = this.empresa.GetSucursal(id_sucursal);
					clave = parametros[6];
					
					if (tipo_empleado.equals("venta")) {
						empleado = new OperarioVenta(nombre, empleado_id, telefono, sueldo, sucursal, clave);
					} 
					else if (tipo_empleado.equals("camion")) {
						empleado = new OperarioCamion(nombre, empleado_id, telefono, sueldo, sucursal, clave);
					} 
					else if (tipo_empleado.equals("bodega")) {
						empleado = new OperarioBodega(nombre, empleado_id, telefono, sueldo, sucursal, clave);
					} 
					else {
						//error dato
						continue;
					}
					
					sucursal.AgregarEmpleado(empleado);
					this.empresa.AgregarEmpleado(empleado);
				}
				catch (Exception e) {
					System.out.println("error empleados"); // Error en el parseo
					}
			}
		} 
		catch (IOException e1) {
			// Error en lectura
		}
	}

	private void CargarClientes() {
		try {
			br = new BufferedReader(new FileReader("archivos/clientes.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Cliente cliente;
		String rut;
		int telefono;
		String nombre;
		String direccion;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					rut = parametros[0];  // es el rut
					nombre = parametros[1];
					telefono = Integer.parseInt(parametros[2]);
					direccion = parametros[3];

					cliente = new Cliente(rut, nombre, telefono, direccion);

					this.empresa.AgregarCliente(cliente);
				}
				catch(Exception e) {
					// Error en los archivos
				}
			}
		} 
		catch (IOException e) {
			// Error en la lectura
		}
	}

	private void CargarTodo() {		
		this.CargarEmpresa();
		this.CargarSucursales();
		this.CargarEmpleados();
		this.CargarClientes();
	}
	
	private void GuardarTodo() {
		
		//Definir escritor
		PrintWriter writer;

		//A continuacion se guardan los datos en los archivos

		//guardar archivo de empresa
		try {
			writer = new PrintWriter("archivos/empresa.data", "UTF-8");

			writer.println(this.empresa.GetNombre() + ";" + this.empresa.GetRut() + ";" +  Integer.toString(this.precioPorKilo) + ";" + Integer.toString(this.id_pedido)+ ";" + Integer.toString(this.id_encomienda));

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// Archivo no encontrado o enconding malo
		}

		//guardar archivo de sucursales
		try {
			PrintWriter writer_sucursales = new PrintWriter("archivos/sucursales.data", "UTF-8");
			PrintWriter writer_empleados = new PrintWriter("archivos/empleados.data", "UTF-8");

			//Iterar sobre las sucursales
			//http://stackoverflow.com/questions/46898/iterate-over-each-entry-in-a-map
			for (Map.Entry<Integer, Sucursal> entry : this.empresa.GetSucursales().entrySet())
			{
				Sucursal sucursal = entry.getValue();

				writer_sucursales.println(Integer.toString(sucursal.GetId()) + ";" + sucursal.GetDireccion() + ";" + Integer.toString(sucursal.GetTelefono()) + ";" + Integer.toString(sucursal.GetCapacidad()));

				//recorrer listado de empleados
				for (Map.Entry<String, Empleado> entry_empleado : sucursal.GetEmpleados().entrySet())
				{
					Empleado empleado = entry_empleado.getValue();
					writer_sucursales.println(Integer.toString(sucursal.GetId()) + ";" + empleado.GetRut() + ";" + empleado.GetTipo() + ";" + empleado.GetNombre() + ";" + Integer.toString(empleado.GetTelefono()) + ";" + Integer.toString(empleado.GetSueldo()));
				}

			}
			
			writer_sucursales.close();
			writer_empleados.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e2) {
			// Archivo no encontrado o enconding malo
		}

		//guardar archivo de clientes
		try {
			PrintWriter writer_clientes = new PrintWriter("archivos/clientes.data", "UTF-8");

			//Iterar sobre los clientes
			for (Map.Entry<String, Cliente> entry_clientes : this.empresa.GetClientes().entrySet())
			{
				Cliente cliente = entry_clientes.getValue();
				writer_clientes.println(cliente.GetRut() + ";" + cliente.GetNombre() + ";" + Integer.toString(cliente.GetTelefono()) + ";" + cliente.GetDireccion());
			}
			
			writer_clientes.close();

		} 
		catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// Archivo no encontrado o enconding malo
		}

	}
}
