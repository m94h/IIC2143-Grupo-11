package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


//Fachada, Sistema
//Sera una clase Singleton
public class Sistema {
	//Se define la instancia para que sea singleton
	private static Sistema INSTANCE = new Sistema();

	// Estos no se si los dejamos aca o en empresa
	private int precioPorGr;
	private int precioPorCc;
	//Los id a continuacion son contadores para cuando se cree una nueva...
	private int id_pedido;
	private int id_encomienda;
	private int id_mensaje;
	private int id_error;
	private int id_viaje;
	//private int id_orden_compra;

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

	/*
	 * Getters de los precios por peso y volumen
	 */
	public int GetPrecioPorGr() {
		return precioPorGr;
	}

	public int GetPrecioPorCc() {
		return precioPorCc;
	}

	/*
	 * Informacion sobre el usuario logeado y su sucursal
	 */
	public Sucursal GetSucursalLoged() {
		return this.sucursal_loged;
	}

	public Empleado GetUsuarioLoged() {
		return this.usuario_loged;
	}

	/*
	 * Informacion sobre el id de pedido y de encomienda cuando se crea uno nuevo
	 */
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

	public int Get_id_mensaje() {
		int valor = id_mensaje;
		id_mensaje++;
		return valor;
	}
	
	public int Get_id_viaje() {
		int valor = id_viaje;
		id_viaje++;
		return valor;
	}
	
	public int Get_id_error() {
		int valor = id_error;
		id_error++;
		return valor;
	}

 /*
	public int Get_id_orden_compra() {
		int valor = id_orden_compra;
		id_orden_compra++;
		return valor;
	}
	*/

	/*
	 * Obtener Sucursales y cambiar
	 */
	public Map<Integer, Sucursal> GetSucursales() {
		return this.empresa.GetSucursales();
	}

	public Sucursal GetSucursal(int id) {
		return this.empresa.GetSucursales().get(id);
	}
	
	public void CambiarSucursal(String cual){
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			if (entry.getValue().GetDireccion().equals(cual)) {
				this.sucursal_loged = entry.getValue();
			}
		}
	}


	/*
	 *  Metodos de Empresa
	 */
	public Map<Integer, Pedido> GetPedidos() {
		return empresa.GetPedidos();
	}

	public Pedido GetPedido (int id) {
		return empresa.GetPedido(id);
	}

	public void AgregarPedido(Pedido pedido) {
		empresa.AgregarPedido(pedido);
	}

	public void AgregarEncomienda(Encomienda encomienda) {
		empresa.AgregarEncomienda(encomienda);
	}

	public  Encomienda GetEncomienda (int id) {
		return empresa.GetEncomienda(id);
	}

	public void AgregarTransporte(MedioDeTransporte vehiculo){
		empresa.AgregarTransporte(vehiculo);
	}

	public MedioDeTransporte GetMedio (String patente){
		return empresa.GetTransporte(patente);
	}
	
	public Map<String, MedioDeTransporte> GetMedios() {
		return empresa.GetTransportes();
	}

	public void AgregarMediosEnTransito(MedioDeTransporte medio){
		empresa.AgregarMedioEnTransito(medio);
	}

	public void EliminarMediosEnTransito(MedioDeTransporte medio){
		empresa.EliminarMedioEnTransito(medio);
	}

	public ArrayList<MedioDeTransporte> GetMediosEnTransito() {
		return empresa.GetMediosEnTransito();
	}
	
	public Map<Integer, Error> GetErrores() {
		return this.empresa.GetErrores();
	}
	
	public Error GetError(int id) {
		return this.empresa.GetError(id);
	}
	
	public void AgregarError(Error error) {
		this.empresa.AgregarError(error);
	}
	
	public Map<String, Empleado> GetEmpleados() {
		return this.empresa.GetEmpleados();
	}
	
	public Empleado GetEmpleado(String rut) {
		return this.empresa.GetEmpleado(rut);
	}
	
	public Map<Integer, Viaje> GetViajes() {
		return this.empresa.GetViajes();
	}

	public void AgregarViaje(Viaje viaje) {
		empresa.AgregarViaje(viaje);
	}


	// Interaccion con usuario
	public int CrearPedido (OperarioVenta vendedor, Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha, String caracteristicasString) {
	    return vendedor.CrearPedido(cliente, origen, destino, urgencia, estado, fecha, caracteristicasString); // Retorna el id del pedido
	}

	public int CrearEncomienda (OperarioVenta vendedor, Pedido pedido, String nombre, int peso, int volumen) {
	  	return vendedor.CrearEncomienda(pedido, nombre, peso, volumen); // Retorna el id de la encomienda
	}

	public void EnviarMensaje (OperarioBodega creador, String texto, Sucursal destino) {
		creador.CrearMensaje(texto, destino);
	}

	public void AgregarCliente (String rut, String nombre, int telefono, String direccion) {
		Cliente c = new Cliente (rut, nombre, telefono, direccion);
		empresa.AgregarCliente(c);
	}

	public Cliente GetCliente (String rut) {
		return this.empresa.GetCliente(rut);
	}

	public void BorrarCliente (String rut) {
		this.empresa.BorrarCliente(rut);
	}


// Detalles de reportes
public String[] GetDetallePedido(int id_pedido) {
	Pedido pedido = GetPedido(id_pedido);
	String[] detalles = new String[14];
	detalles[0] = pedido.GetCliente().GetNombre();
	detalles[1] = Integer.toString(pedido.GetPeso());
	detalles[2] = Integer.toString(pedido.GetVolumen());
	detalles[3] = Integer.toString(pedido.GetUrgencia());
	detalles[4] = pedido.GetEstado().toString();

	LocalDate f = pedido.GetFecha();
	detalles[5] = f.toString();

	detalles[6] = pedido.GetOrigen().GetDireccion();
	detalles[7] = "";

	f = pedido.GetFechaEnvio();
	if (f != null)
		detalles[8] = f.toString();
	else
		detalles[8] = "";

	MedioDeTransporte m = pedido.GetCargadoEn();
	if (m != null) 
		detalles[9] = m.GetPatente();
	else 
		detalles[9] = "";

	Empleado e = pedido.GetConductor();
	if (e != null)
		detalles[10] = e.GetNombre();
	else 
		detalles[10] = "";

	f = pedido.GetFechaLlegada();
	if (f != null)
		detalles[11] = f.toString();
	else
		detalles[11] = "";

	detalles[12] = pedido.GetDestino().GetDireccion();
	detalles[13] = "Aun no recibido";


	return detalles;
}

public String[] GetDetalleMedio(String patente) {
	MedioDeTransporte medio = GetMedio(patente);
	String[] detalles = new String[7];
	detalles[0] = patente;
	detalles[1] = Integer.toString(medio.GetCapacidadMax());
	detalles[2] = Integer.toString(medio.GetCapacidadDisponible());
	if (medio.GetEstado() == Estado.EnTransito)
		detalles[3] = "En Transito";
	else
		detalles[3] = medio.GetOrigen().GetDireccion();
	if (medio.EsRadioactivo())
		detalles[4] = "Si";
	else
		detalles[4] = "No";
	if (medio.EsFragil())
		detalles[5] = "Si";
	else
		detalles[5] = "No";
	if (medio.EsRefrigerado())
		detalles[6] = "Si";
	else
		detalles[6] = "No";
	
	return detalles;
}

public String[] GetDetalleViaje(String patente) {
	MedioDeTransporte medio = GetMedio(patente);
	String[] detalles = new String[5];
	detalles[0] = medio.GetOrigen().GetDireccion();
	detalles[1] = medio.GetDestino().GetDireccion();
	detalles[2] = medio.GetConductor().GetNombre();
	detalles[3] = patente;
	detalles[4] = Integer.toString(medio.GetCapacidadActual());
	
	return detalles;
}

	/*
	public String SolicitarCamion(OperarioBodega solicitador){
		solicitador.PedirCamion();
	}
	*/

	/*
	 * Login
	 */
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
	
	/*
	 * Login para empleados
	 */
	public boolean LogInCliente(String rut, int codigoPedido) {
		if (this.empresa.GetCliente(rut) != null && this.empresa.GetPedido(codigoPedido) != null) {
			Cliente cliente_loged = this.empresa.GetCliente(rut);
			Pedido pedido_loged = this.empresa.GetPedido(codigoPedido);
			
			if (pedido_loged.GetCliente().GetRut().equals(cliente_loged.RUT)) {
				//cliente correcto
				return true;
			}
		}
		return false;
	}
	

	/*
	 * Metodos para cargar archivos
	 */
	/*
	 * Cargar info empresa
	 */
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
			this.precioPorGr = Integer.parseInt(parametros[2]);
			this.precioPorCc = Integer.parseInt(parametros[3]);
			this.id_pedido = Integer.parseInt(parametros[4]);
			this.id_encomienda = Integer.parseInt(parametros[5]);
			this.id_mensaje = Integer.parseInt(parametros[6]);
			this.id_error = Integer.parseInt(parametros[7]);
			this.id_viaje = Integer.parseInt(parametros[8]);
		}
		catch(Exception e) {
			// Error en el parseo
		}
	}

	/*
	 * Cargar sucursales
	 */
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

	/*
	 * Cargar empleados
	 */
	private void CargarEmpleados() {
		try {
			br = new BufferedReader(new FileReader("archivos/empleados.data"));
		} catch (FileNotFoundException e1) {
				e1.getMessage();
		}

		Empleado empleado;

		int id_sucursal;
		String empleado_id;
		String tipo_empleado;
		String nombre;
		int telefono;
		int sueldo;
		Sucursal sucursal;
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
					System.out.println(""); // Error en el parseo
					}
			}
		}
		catch (IOException e1) {
			// Error en lectura
		}
	}

	/*
	 * Cargar Clientes
	 */
	private void CargarClientes() {
		try {
			br = new BufferedReader(new FileReader("archivos/clientes.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Cliente cliente;

		String rut;
		String nombre;
		int telefono;
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

	/*
	 * Cargar Pedidos
	 */
	private void CargarPedidos() {
		try {
			br = new BufferedReader(new FileReader("archivos/pedidos.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Pedido pedido;

		int id_pedido;
		Cliente cliente;
		Sucursal sucursal_origen;
		Sucursal sucursal_destino;
		int urgencia;
		Estado estado = null;
		LocalDate fecha;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String caracteristicas;
		Empleado creado_por;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				MedioDeTransporte cargadoEn = null;
				try {
					parametros = sCurrentLine.split(";");
					id_pedido = Integer.parseInt(parametros[0]);
					cliente = this.empresa.GetCliente(parametros[1]);
					sucursal_origen = this.empresa.GetSucursal(Integer.parseInt(parametros[2]));
					sucursal_destino = this.empresa.GetSucursal(Integer.parseInt(parametros[3]));
					if (!parametros[4].equals("0")) 
						cargadoEn =  GetMedio(parametros[4]);
					urgencia = Integer.parseInt(parametros[5]);
					switch (parametros[6]) {
						case "EnTransito":
							estado = Estado.EnTransito;
							break;
						case "EnSucursalOrigen":
							estado = Estado.EnSucursalOrigen;
							break;
						case "EnSucursalDestino":
							estado = Estado.EnSucursalDestino;
							break;
					}
					fecha = LocalDate.parse(parametros[7], formatter);
					creado_por = this.empresa.GetEmpleado(parametros[8]);
					caracteristicas = parametros[9];


					pedido = new Pedido(id_pedido, cliente, sucursal_origen, sucursal_destino, urgencia, estado, fecha, caracteristicas, creado_por);
					pedido.SetCargadoEn(cargadoEn);
					if (cargadoEn != null)
						cargadoEn.CargarPedido(pedido);
					this.empresa.AgregarPedido(pedido);

				}
				catch(Exception e) {
					System.out.println(e);
					// Error en los archivos
				}
			}
		}
		catch (IOException e) {
			// Error en la lectura
		}
	}

	/*
	 * Cargar Encomiendas de los pedidos
	 */
	private void CargarEncomiendas() {
		try {
			br = new BufferedReader(new FileReader("archivos/encomiendas.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Encomienda encomienda;

		int id_encomienda;
		String nombre;
		int peso;
		int volumen;
		int id_pedido;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					id_encomienda = Integer.parseInt(parametros[0]);
					nombre = parametros[1];
					peso = Integer.parseInt(parametros[2]);
					volumen = Integer.parseInt(parametros[3]);
					id_pedido = Integer.parseInt(parametros[4]);

					encomienda = new Encomienda(id_encomienda, nombre, peso, volumen);
					this.empresa.GetPedido(id_pedido).AgregarEncomienda(encomienda);
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

	/*
	 * Cargar Camiones
	 */
	private void CargarCamiones() {
		try {
			br = new BufferedReader(new FileReader("archivos/camiones.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Camion camion;

		String patente;
		String marca;
		String modelo;
		Sucursal origen;
		Sucursal destino = null;
		Estado estado;
		int cap_maxima;
		int km;
		String cualidades;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					patente = parametros[0];
					marca = parametros[1];
					modelo = parametros[2];
					origen = this.empresa.GetSucursal(Integer.parseInt(parametros[3]));
					if (Integer.parseInt(parametros[4]) != 0)
						destino = this.empresa.GetSucursal(Integer.parseInt(parametros[4]));
					cap_maxima = Integer.parseInt(parametros[5]);
					km = Integer.parseInt(parametros[6]);
					estado = Estado.values()[Integer.parseInt(parametros[7])];
					cualidades = parametros[8];
					
					camion = new Camion(patente, marca, modelo, origen, destino, cap_maxima, km, estado, cualidades);
					this.empresa.AgregarTransporte(camion);
					
					/*
					 * Si esta en transito, agregar a listado de en transito de empresa
					 */
					if (estado.equals(Estado.EnTransito))
						this.empresa.AgregarMedioEnTransito(camion);
						camion.setConductor(this.empresa.GetEmpleado(parametros[9]));
										
					/*
					 * Si esta en Sucursal origen es pq esta disponible para salir
					 */
					if (estado.equals(Estado.EnSucursalOrigen))
						origen.AgregarMedioDisponible(camion);
					/*
					 * Si esta en sucursal destino es pq llego y debe descargarse
					 */
					else if(estado.equals(Estado.EnSucursalDestino))
						destino.AgregarMedioArrivado(camion);
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
	
	/*
	 * Cargar Ordenes
	 */
	private void CargarOrdenes() {
		try {
			br = new BufferedReader(new FileReader("archivos/ordenes.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		OrdenCompra orden;

		int monto;
		MedioPago medio = null;
		boolean estado = false;
		int id_pedido;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					monto = Integer.parseInt(parametros[0]);
					switch (parametros[1]){
						case "CREDITO":
							medio = MedioPago.CREDITO;
							break;
						case "DEBITO":
							medio = MedioPago.DEBITO;
							break;
						case "EFECTIVO":
							medio = MedioPago.EFECTIVO;
							break;
						case "CHEQUE":
							medio = MedioPago.CHEQUE;
							break;
 					}
 					switch (parametros[2]){
							case "pagado":
								estado = true;
								break;
							case "deuda":
								estado = false;
								break;
 					}
					id_pedido = Integer.parseInt(parametros[3]);

					orden = new OrdenCompra(monto, medio, estado);
					this.empresa.GetPedido(id_pedido).AgregarOrden(orden);
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
	
	/*
	 * Cargar Mensajes
	 */
	private void CargarMensajes() {
		try {
			br = new BufferedReader(new FileReader("archivos/mensajes.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Mensaje mensaje;

		int id;
		Sucursal destino;
		Empleado creador;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					String texto = "";
					parametros = sCurrentLine.split(";");
					id = Integer.parseInt(parametros[0]);
					destino = this.empresa.GetSucursal(Integer.parseInt(parametros[1]));
					creador = this.empresa.GetEmpleado(parametros[2]);
					for (int i = 3; i < parametros.length; i++) {
						if (i == 3)
							texto = texto + parametros[i]; // Esto se hace por si alguien usa ";" en el mensaje
						else
							texto = texto + ";" + parametros[i];
					}

					mensaje = new Mensaje(id, texto, destino, creador);
					destino.RecibirMensaje(mensaje);
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

		/*
	 * Cargar Viajes
	 */
	private void CargarViajes() {
		try {
			br = new BufferedReader(new FileReader("archivos/viajes.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Viaje viaje;
		int id_viaje;
		String patente;
		Empleado conductor;
		Sucursal origen;
		Sucursal destino;
		LocalDate fechaSalida; 
		LocalDate fechaLlegada; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					id_viaje = Integer.parseInt(parametros[0]);
					patente = parametros[1];
					conductor = this.empresa.GetEmpleado(parametros[2]);
					origen = this.empresa.GetSucursal(Integer.parseInt(parametros[3]));
					destino = this.empresa.GetSucursal(Integer.parseInt(parametros[4]));
					fechaSalida = LocalDate.parse(parametros[5], formatter);
					if (parametros[6].equals("-")) {
						viaje = new Viaje(id_viaje, patente, conductor, origen, destino, fechaSalida);
						this.GetMedio(patente).setViaje(viaje);
					}
					else {
						fechaLlegada = LocalDate.parse(parametros[6], formatter);
						viaje = new Viaje(id_viaje, patente, conductor, origen, destino, fechaSalida, fechaLlegada);
					}

					this.AgregarViaje(viaje);
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

	/*
	 * Cargar Viajes
	 */
	private void CargarErrores() {
		try {
			br = new BufferedReader(new FileReader("archivos/errores.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Error error;

		int id;
		Empleado empleado;
		String mensaje;
		LocalDate fecha; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");		

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					id = Integer.parseInt(parametros[0]);
					empleado = this.empresa.GetEmpleado(parametros[1]);
					mensaje = parametros[2];
					fecha = LocalDate.parse(parametros[3], formatter);
					
					error = new Error(id, empleado, mensaje, fecha);
					this.AgregarError(error);
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

	/*
	 * Caller de Cargar los archivos
	 */
	private void CargarTodo() {
		this.CargarEmpresa();
		this.CargarSucursales();
		this.CargarEmpleados();
		this.CargarClientes();
		this.CargarCamiones();
		this.CargarPedidos();
		this.CargarEncomiendas();
		this.CargarOrdenes();
		this.CargarMensajes();
		this.CargarViajes();
		this.CargarErrores();
	}

	/*
	 * Para guardar los archivos (actualizar la info)
	 */
	public void GuardarTodo() {

		//Definir escritor
		PrintWriter writer;

		//A continuacion se guardan los datos en los archivos

		//guardar archivo de empresa
		try {
			writer = new PrintWriter("archivos/empresa.data", "UTF-8");

			writer.println(this.empresa.GetNombre() + ";" + this.empresa.GetRut() + ";" +  Integer.toString(this.precioPorGr) + ";" +  Integer.toString(this.precioPorCc) + ";" + Integer.toString(this.id_pedido)+ ";" + Integer.toString(this.id_encomienda) + ";" + Integer.toString(this.id_mensaje) + ";" + Integer.toString(this.id_error) + ";" + Integer.toString(this.id_viaje));

			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// Archivo no encontrado o enconding malo
		}

		//guardar archivo de sucursales
		try {
			PrintWriter writer_sucursales = new PrintWriter("archivos/sucursales.data", "UTF-8");
			PrintWriter writer_empleados = new PrintWriter("archivos/empleados.data", "UTF-8");
			PrintWriter writer_mensajes = new PrintWriter("archivos/mensajes.data", "UTF-8");

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
					writer_empleados.println(Integer.toString(sucursal.GetId()) + ";" + empleado.GetRut() + ";" + empleado.GetTipo() + ";" + empleado.GetNombre() + ";" + Integer.toString(empleado.GetTelefono()) + ";" + Integer.toString(empleado.GetSueldo()) + ";" + empleado.GetClave());
				}

				for (Map.Entry<Integer, Mensaje> entry_mensaje : sucursal.GetMensajes().entrySet())
				{
					Mensaje mensaje = entry_mensaje.getValue();
					writer_mensajes.println(Integer.toString(mensaje.GetId()) + ";" + Integer.toString(sucursal.GetId()) + ";" + mensaje.GetCreadorRut() + ";" + mensaje.GetTexto());
				}

			}

			writer_sucursales.close();
			writer_empleados.close();
			writer_mensajes.close();

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

		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// Archivo no encontrado o enconding malo
		}

		//guardar archivo de pedidos y encomiendas
		try {
			PrintWriter writer_pedidos = new PrintWriter("archivos/pedidos.data", "UTF-8");
			PrintWriter writer_encomiendas = new PrintWriter("archivos/encomiendas.data", "UTF-8");
			PrintWriter writer_ordenes = new PrintWriter("archivos/ordenes.data", "UTF-8");

			//Iterar sobre los pedidos
			for (Map.Entry<Integer, Pedido> entry_pedido : this.empresa.GetPedidos().entrySet())
			{
				Pedido pedido = entry_pedido.getValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				if (pedido.GetCargadoEn() != null)
					writer_pedidos.println(Integer.toString(pedido.GetId()) + ";" + pedido.GetCliente().GetRut() + ";" + Integer.toString(pedido.GetOrigen().GetId()) + ";" + Integer.toString(pedido.GetDestino().GetId()) + ";" + pedido.GetCargadoEn().GetPatente() + ";" + Integer.toString(pedido.GetUrgencia()) + ";" + pedido.GetEstado().toString() + ";" + pedido.GetFecha().format(formatter) + ";" + pedido.GetCreadoPor().GetRut() + ";" + pedido.GetCaracteristicas());
				else
					writer_pedidos.println(Integer.toString(pedido.GetId()) + ";" + pedido.GetCliente().GetRut() + ";" + Integer.toString(pedido.GetOrigen().GetId()) + ";" + Integer.toString(pedido.GetDestino().GetId()) + ";" + "0" + ";" + Integer.toString(pedido.GetUrgencia()) + ";" + pedido.GetEstado().toString() + ";" + pedido.GetFecha().format(formatter) + ";" + pedido.GetCreadoPor().GetRut() + ";" + pedido.GetCaracteristicas());
				OrdenCompra orden = pedido.GetOrden();
				String estado = "deuda";
				String medio = "";
				if (orden != null) {
					if (orden.GetEstado()){
						estado = "pagado";
					}
					if (orden.GetMedio() != null) {
						medio = orden.GetMedio().toString();
					}
					writer_ordenes.println(Integer.toString(orden.GetMonto()) + ";" + medio + ";" + estado + ";" + Integer.toString(pedido.GetId()));
				}

				//recorrer listado de empleados
				for (Map.Entry<Integer, Encomienda> entry_encomienda : pedido.GetEncomiendas().entrySet())
				{
					Encomienda encomienda = entry_encomienda.getValue();
					writer_encomiendas.println(Integer.toString(encomienda.GetId()) + ";" + encomienda.GetNombre() + ";" + Integer.toString(encomienda.GetPeso()) + ";" + Integer.toString(encomienda.GetVolumen()) + ";" + Integer.toString(pedido.GetId()));
				}
			}

			writer_pedidos.close();
			writer_encomiendas.close();
			writer_ordenes.close();

			} catch (FileNotFoundException | UnsupportedEncodingException e2) {
				// Archivo no encontrado o enconding malo
			}

			//guardar archivo de camiones
			try {
				PrintWriter writer_camiones = new PrintWriter("archivos/camiones.data", "UTF-8");

				//Iterar sobre los camiones
				for (Map.Entry<String, MedioDeTransporte> entry : this.empresa.GetTransportes().entrySet())
				{
					Camion camion = (Camion)entry.getValue();

					String creador = "0";
					if (camion.GetEstado() == Estado.EnTransito) {
						creador = camion.GetConductor().GetRut();
					}
					if (camion.GetDestino() != null)
						writer_camiones.println(camion.GetPatente() + ";" + camion.GetMarca() + ";" + camion.GetModelo() + ";" + Integer.toString(camion.GetOrigen().GetId()) + ";" + Integer.toString(camion.GetDestino().GetId()) + ";" + Integer.toString(camion.GetCapacidadMax()) + ";" + Integer.toString(camion.GetKm()) + ";" + Integer.toString(Estado.valueOf(camion.estado.toString()).ordinal()) + ";" + camion.GetCualidades() + ";" + creador);
					else
						writer_camiones.println(camion.GetPatente() + ";" + camion.GetMarca() + ";" + camion.GetModelo() + ";" + Integer.toString(camion.GetOrigen().GetId()) + ";" + "0" + ";" + Integer.toString(camion.GetCapacidadMax()) + ";" + Integer.toString(camion.GetKm()) + ";" + Integer.toString(Estado.valueOf(camion.estado.toString()).ordinal()) + ";" + camion.GetCualidades() + ";" + creador);
				}
				writer_camiones.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e2) {
				// Archivo no encontrado o enconding malo
			}

				//guardar archivo de viajes
			try {
				PrintWriter writer_viajes = new PrintWriter("archivos/viajes.data", "UTF-8");

				//Iterar sobre los viajes
				for (Map.Entry<Integer, Viaje> entry : this.empresa.GetViajes().entrySet())
				{	
					Viaje viaje = entry.getValue();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					
					if(viaje.GetFechaLlegada() == null)
						writer_viajes.println(Integer.toString(viaje.GetId()) + ";" + viaje.GetPatente() + ";" + viaje.GetConductor().GetRut() + ";" + Integer.toString(viaje.GetOrigen().GetId()) + ";" + Integer.toString(viaje.GetDestino().GetId()) + ";" + viaje.GetFechaSalida().format(formatter));
					else
						writer_viajes.println(Integer.toString(viaje.GetId()) + ";" + viaje.GetPatente() + ";" + viaje.GetConductor().GetRut() + ";" + Integer.toString(viaje.GetOrigen().GetId()) + ";" + Integer.toString(viaje.GetDestino().GetId()) + ";" + viaje.GetFechaSalida().format(formatter) + ";" + viaje.GetFechaLlegada().format(formatter));

					}
				writer_viajes.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e2) {
					// Archivo no encontrado o enconding malo
			}

			//guardar archivo de errores
			try {
				PrintWriter writer_errores = new PrintWriter("archivos/errores.data", "UTF-8");

				//Iterar sobre los viajes
				for (Map.Entry<Integer, Error> entry : this.empresa.GetErrores().entrySet())
				{	
					Error error = entry.getValue();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

					writer_errores.println(Integer.toString(error.GetID()) + ";" + error.GetEmpleado().GetRut() + ";" + error.GetMensaje() + ";" + error.GetFecha().format(formatter));
					}
				writer_errores.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e2) {
					// Archivo no encontrado o enconding malo
			}
	}
}
