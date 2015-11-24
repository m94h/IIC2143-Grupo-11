package backend;

import java.time.LocalDate;

public class OperarioVenta extends Empleado {

	public OperarioVenta(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
    this.tipo_empleado = "venta";
	}
	
  public int CrearPedido (Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha, String caracteristicasString) {
    Pedido p = new Pedido (cliente, origen, destino, urgencia, estado, fecha, caracteristicasString);
    Sistema.GetInstance().AgregarPedido(p);
    return p.GetId();
  }

  public int CrearEncomienda (Pedido pedido, String nombre, int peso, int volumen) {
  	Encomienda e = new Encomienda(nombre, peso, volumen);
  	Sistema.GetInstance().AgregarEncomienda (e);
  	return e.GetId();
  }
}
