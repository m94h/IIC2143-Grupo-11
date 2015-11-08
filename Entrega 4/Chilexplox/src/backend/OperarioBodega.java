package backend;

public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "bodega";
	}

	public boolean CargarMedio(MedioDeTransporte medio, Pedido pedido){
		if(medio.GetPedidos().size() == 0){
			medio.setDestino(pedido.GetDestino()); // El primer pedido que se cargue en el medio determina su destino
		}
		return medio.CargarPedido(pedido);
	}

	public void DescargarMedio(MedioDeTransporte medio){
		for(Pedido item:medio.listaPedidos)
		{
			item.estado = Estado.EnSucursalDestino;
			item.Arrivado();
		}
		//Desocupar medio
		medio.Desocupar();

		//Poner el medio como disponible
		this.sucursal.SetMedioDisponible(medio);
	}

	public void CrearMensaje(String mensaje, Sucursal destino) {
		Mensaje m = new Mensaje(mensaje, destino, this);
		//Enviar
		destino.RecibirMensaje(m);
	}

}
