package backend;

public class OperarioVenta extends Empleado {

	public OperarioVenta(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
    this.tipo_empleado = "venta";
	}
	
  public int CrearPedido (Cliente cliente, Sucursal origen, Sucursal destino, PrioridadPedido urgencia) {
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
