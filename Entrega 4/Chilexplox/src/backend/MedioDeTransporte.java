package backend;

import java.util.ArrayList;

public abstract class MedioDeTransporte {
	protected String patente;
	protected int capacidadMax;
	protected int capacidadActual;
	protected int enUso;
	protected boolean desocupado;
	protected Sucursal origen;
	protected Sucursal destino;
	protected Estado estado;
	protected ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();

	public MedioDeTransporte (String patente, int capacidadMax) {
		this.patente = patente;
		this.capacidadMax = capacidadMax;
		this.origen = null;
		this.destino = null;
		this.desocupado = true;
	}

	public void setOrigen(Sucursal origen, Sucursal destino){
		this.origen = origen;
	}

	public void setDestino(Sucursal destino){
		this.destino = destino;
	}

	public void Viajar(){
		this.estado = Estado.EnTransito;
		for(int i = 0; i <= this.listaPedidos.size(); i++){
			this.listaPedidos.get(i).SetEnTransito();
		}
	}

	public int GetCapacidadDisponible(){
		return (this.capacidadMax - this.capacidadActual);
	}

	public Estado GetEstado() {return this.estado;}

	public Sucursal GetOrigen(){return this.origen;}

	public Sucursal GetDestino(){return this.destino;}

	public String GetPatente() {return this.patente;}

	public int GetCapacidadMax() {return this.capacidadMax;}

	public ArrayList<Pedido> GetPedidos(){
		return this.listaPedidos;
	}

	public boolean CargarPedido(Pedido pedido){
		if(this.capacidadActual < this.capacidadMax){
			listaPedidos.add(pedido);
			//avisar al pedido que fue cargado
			pedido.Cargado(this);
			this.capacidadActual += 1;
			return true;
		}
		else{
			this.desocupado = false;
			return false;
		}
	}

	public void Desocupar() {
		this.capacidadActual = 0;
		this.desocupado = true;
		this.listaPedidos.clear();
	}
}