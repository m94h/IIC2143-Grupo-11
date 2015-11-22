package backend;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public abstract class MedioDeTransporte {
	protected String patente;
	protected int capacidadMax;  // Tiene que ser capacidad como cm3, hay que cambiar el valor del cami�n creado y los futuros camiones tienen que tener harta cap.
	protected int capacidadActual;
	// protected int pesoMax; Estamos analizando si ponerle el peso como variable, en gral no se env�an un monton de pedidos pesados, eso es mas para carga industrial, no de correos
	// protected int pesoDisponible;
	protected int enUso;
	protected boolean desocupado;
	protected Empleado conductor;
	protected Sucursal origen;
	protected Sucursal destino;
	protected Estado estado;
	protected Viaje viaje;
	protected ArrayList<Pedido> listaPedidos;
	protected ArrayList<Advertencia> cualidades;
	protected String cualidadesString;


	public MedioDeTransporte (String patente, int capacidadMax, Sucursal origen, Sucursal destino, Estado estado, String cualidadesString) {
		this.patente = patente;
		this.capacidadMax = capacidadMax;
		this.origen = origen;
		this.destino = destino;
		this.desocupado = false;
		this.estado = estado;
		this.cualidadesString = cualidadesString;
		//this.estado = Estado.EnSucursalOrigen;
		this.listaPedidos = new ArrayList<Pedido>();
		this.setCualidades();
	}

	public void setOrigen(Sucursal origen){
		this.origen = origen;
	}

	public void setDestino(Sucursal destino){
		this.destino = destino;
	}

	public void setConductor(Empleado conductor){
		this.conductor = conductor;
	}

	public void setViaje(Viaje viaje){
		this.viaje = viaje;
	}

	private void setCualidades(){
		int cualidadesInt = Integer.parseInt(this.cualidadesString);
		this.cualidades = new ArrayList<Advertencia>();
		if (cualidadesInt == 1 || cualidadesInt == 3 || cualidadesInt == 5 || cualidadesInt == 7 )
			this.cualidades.add(Advertencia.Radioactivo);
		if (cualidadesInt == 2 || cualidadesInt == 3 || cualidadesInt == 6 || cualidadesInt == 7 )
			this.cualidades.add(Advertencia.Fragil);
		if (cualidadesInt == 4 || cualidadesInt == 5 || cualidadesInt == 6 || cualidadesInt == 7 )
			this.cualidades.add(Advertencia.Refrigerado);
	}

	/*
	 * Para viajar, cambiamos todos los estados de los pedidos
	 * y el estado del medio a entransito
	 */
	public void Viajar(){
		this.estado = Estado.EnTransito;
		if(this.listaPedidos.size() > 0){
			for(int i = 0; i < this.listaPedidos.size(); i++){
				Pedido pedido = this.listaPedidos.get(i);
				pedido.SetEnTransito();
				pedido.Enviado();
				pedido.SetConductor(conductor);
			}
		}
		this.viaje = new Viaje(this.patente, this.conductor, this.origen, this.destino, LocalDate.now());
		Sistema.GetInstance().AgregarViaje(viaje);
	}
	
	/*
	 * Agregado para arrivar y cambiar el estado
	 */
	public void ArribarMedio() {
		this.estado = Estado.EnSucursalDestino;
		this.viaje.AvisarLlegada();
	}
	
	public void DevolverMedio() {
		this.estado = Estado.EnSucursalOrigen;
	}

	public int GetCapacidadDisponible(){
		return (this.capacidadMax - this.capacidadActual);
	}

	public Estado GetEstado() {return this.estado;}

	public Sucursal GetOrigen(){return this.origen;}

	public Sucursal GetDestino(){return this.destino;}

	public Empleado GetConductor(){return this.conductor;}

	public String GetPatente() {return this.patente;}

	public int GetCapacidadMax() {return this.capacidadMax;}

	public int GetCapacidadActual() {return this.capacidadActual;}

	public boolean GetDesocupado(){ return this.desocupado; }

	public String GetCualidades(){ return this.cualidadesString; }

	public ArrayList<Pedido> GetPedidos(){
		return this.listaPedidos;
	}

	public boolean EsRadioactivo() {
		if (this.cualidades.contains(Advertencia.Radioactivo))
			return true;
		return false;
	}
	
	public boolean EsFragil() {
		if (this.cualidades.contains(Advertencia.Fragil))
			return true;
		return false;
	}
	
	public boolean EsRefrigerado() {
		if (this.cualidades.contains(Advertencia.Refrigerado))
			return true;
		return false;
	}

	public boolean CargarPedido(Pedido pedido){
		if(this.capacidadActual < this.capacidadMax){
			listaPedidos.add(pedido);
			//avisar al pedido que fue cargado
			//pedido.Cargado(this); //Cambiado a operario
			this.capacidadActual += pedido.GetVolumen();
			return true;
		}
		else{
			this.desocupado = false;
			return false;
		}
	}
	/* No se usa...
	public void CargarPedidos(List<Integer> pedidos) {
		for (Integer id: pedidos) {
			Pedido pedido = Sistema.GetInstance().GetPedido(id);
			listaPedidos.add(pedido);
			//pedido.Cargado(this); //Cambiado a operario
		}
		this.desocupado = false;
	}
	*/

	public void Desocupar() {
		this.capacidadActual = 0;
		this.desocupado = true;
		this.listaPedidos.clear();
		
		//setear como disponible para partir
		this.estado = Estado.EnSucursalOrigen;
		this.origen = this.destino;
		this.destino = null;
	}
}