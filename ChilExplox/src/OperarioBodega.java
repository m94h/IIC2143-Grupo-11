
public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
		this.tipo_empleado = "bodega";
	}

	public void CargarCamion(Camion camion, Pedido pedido){
		camion.CargarPedido(pedido);
	}

	public void DescargarCamion(Camion camion){
		for(int i = 0; i < camion.listaPedidos.size(); i++)
		{
			item.estado = Estado.EnSucursalDestino;
		}
		this.sucursal.AgregarCamionListo(camion);
	}

	public Mensaje CrearMensaje(String mensaje, Sucursal destino){
		return new Mensaje(mensaje, destino, this);
	}
}
