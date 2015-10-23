package backend;

public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "bodega";
	}

	public boolean CargarCamion(Camion camion, Pedido pedido){
		return camion.CargarPedido(pedido);
	}

	public void DescargarCamion(Camion camion){
		for(Pedido item:camion.listaPedidos)
		{
			item.estado = Estado.EnSucursalDestino;
		}
		camion.Desocupar();
		this.sucursal.AgregarCamionListo(camion);
	}

	public void CrearMensaje(String mensaje, Sucursal destino) {
		Mensaje m = new Mensaje(mensaje, destino, this);
		//Enviar
		destino.RecibirMensaje(m);
	}

	/*
	public Camion PedirCamion(String patente){


		return camion.GetPatente();
	}
	*/
}
