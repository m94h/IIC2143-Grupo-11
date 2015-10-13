
public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
	}

	public void CargarCamion(Camion camion, Pedido pedido){
		camion.CargarPedido(pedido);
	}

	public void DescargarCamion(Camion camion){
		for(Pedido item:listaPedidos)
		{
			item.estado = Estado.EnSucursalDestino;
		}
		this.sucursal.AgregarCamionListo(camion);
	}

	public Mensaje CrearMensaje(String mensaje, Sucursal destino){
		return new Mensaje(mensaje, destino, this);
	}

	public String PedirCamion(){
		// solicitar a la empresa un camion, retorna la patente de este

		return camion.GetPatente();
	}
}
