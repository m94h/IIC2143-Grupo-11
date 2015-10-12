import java.util.ArrayList;

public abstract class MedioDeTransporte {
	protected int capacidadMax;
	protected int capacidadActual;
	protected int enUso;
	protected Sucursal origen;
	protected Sucursal destino;
	protected Estado estado;
	protected ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();  
	
	public MedioDeTransporte (int capacidadMax) {
		this.capacidadMax = capacidadMax;
	}	
	
	public void Viajar(){
		this.estado = Viajando;
	}

	public int GetCapacidadDisponible(){
		return this.capacidad;
	}

	public void CargarPedido(Pedido pedido){
		if(this.capacidad <= this.capacidadMax){
			listaPedidos.add(pedido);
			this.capacidad += 1;	
		}
		else{
			// Mensaje que ya esta lleno
		}
	}
}