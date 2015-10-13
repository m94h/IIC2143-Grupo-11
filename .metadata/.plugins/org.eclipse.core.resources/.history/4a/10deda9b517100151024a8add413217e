
public class OperarioVenta extends Empleado {

	public OperarioVenta(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
	}
	
  public int CrearPedido (Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
    Pedido p = new Pedido (cliente, origen, destino, urgencia);
    Sistema.GetInstance().AgregarPedido (p);
    return p.GetId();
  }

  public int CrearEncomienda (Pedido pedido, int peso, int volumen) {
  	Encomienda e = new Encomienda(peso, volumen);
  	Sistema.GetInstance().AgregarEncomienda (e);
  	return e.GetId();
  }
}
