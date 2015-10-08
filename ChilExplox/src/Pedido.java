import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int ID;
	private Cliente cliente;
  private Sucursal origen;
  private Sucursal destino;
  private Estado estado;
  private int urgencia;

  private OrdenCompra orden_compra;
  private List<Encomienda> encomiendas;

  public Pedido(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
    this.cliente = cliente;
    this.origen = origen;
    this.destino = destino;
    this.urgencia = urgencia;
    this.estado = EnSucursalOrigen;

    this.encomiendas = new List<Encomienda>();
  }

  public void AgregarEncomienda (Encomienda encomienda) {
    this.encomiendas.add(encomienda);
  }


  public void GenerarOrden() {
    this.orden_compra = new OrdenCompra();
  }
	
}
