
public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
	}

	public void CargarCamion(Camion camion){
		// deberíamos verificar la cap del camion en algun punto y ver si se puede cargar
	}

	public void DescargarCamion(Camion camion){
		for(Pedido item:camion.listaPedidos)
		{
			item.estado = Estado.EnSucursalDestino;
		}
		this.sucursal.AgregarCamionListo(camion);
	}

	public Mensaje CrearMensaje(String mensaje, Sucursal destino){
		return new Mensaje(mensaje, destino, this);
	}
}
