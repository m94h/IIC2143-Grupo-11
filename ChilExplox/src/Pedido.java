import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int id;
	private Cliente cliente;
	private Sucursal origen;
  	private Sucursal destino;
  	public Estado estado;
  	private PrioridadPedido prioridad;
    private int urgencia;

  	private OrdenCompra orden_compra;
  	private List<Encomienda> encomiendas;

  	public Pedido(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
    	this.id = Sistema.GetInstance().Get_id_pedido();
    	this.cliente = cliente;
    	this.origen = origen;
    	this.destino = destino;
    	this.urgencia = urgencia;
    	this.estado = Estado.EnSucursalOrigen;
    	this.encomiendas = new ArrayList<Encomienda>();
      if(this.urgencia == 1) {this.prioridad = PrioridadPedido.Baja;}
      else if(this.urgencia == 2) {this.prioridad = PrioridadPedido.Media;}
      else{this.prioridad = PrioridadPedido.Alta}
  	}

  	public int GetId() {return id;}

  	public void AgregarEncomienda (Encomienda encomienda) {
    	this.encomiendas.add(encomienda);
  	}

  	public int CalcularMonto() {
  		int monto = 0;
  		for (Encomienda e : encomiendas) {
  			monto += e.GenerarPresupuesto();
  		}
  		return monto;
  	}

 	public void GenerarOrden() {
    	this.orden_compra = new OrdenCompra(CalcularMonto());
  	}
}
