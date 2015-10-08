
public class OperarioVenta extends Empleado {
  public int Crear_Pedido (Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
    Pedido p = new Pedido (cliente, origen, destino, urgencia);
    Empresa.AgregarPedido (p);
    return p.id;
  }

  public int CrearEncomienda (Pedido pedido, int peso, int volumen) {
  	Encomienda e = new Encomienda(peso, volumen);
  	Empresa.AgregarEncomienda (e);
  	return e.id;
  }
}
