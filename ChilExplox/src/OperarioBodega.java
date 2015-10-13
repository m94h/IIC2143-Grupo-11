
public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
		this.tipo_empleado = "bodega";
	}

	public void CargarCamion(Camion camion, Pedido pedido){
		camion.CargarPedido(pedido);
	}

	public void DescargarCamion(Camion camion){
		for(Pedido item:camion.listaPedidos)
		{
			item.estado = Estado.EnSucursalDestino;
		}
		this.sucursal.AgregarCamionListo(camion);
	}

	public void CrearMensaje(String mensaje, Sucursal destino) {
		Mensaje m = new Mensaje(mensaje, destino, this);
		//Enviar
		destino.RecibirMensaje(m);
	}

	public String PedirCamion(){
		// solicitar a la empresa un camion, retorna la patente de este

		return camion.GetPatente();
	}
}
