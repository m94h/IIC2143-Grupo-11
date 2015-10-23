package backend;

public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "bodega";
	}

	public boolean CargarMedio(MedioDeTransporte medio, Pedido pedido){
		return medio.CargarPedido(pedido);
	}

	public void DescargarMedio(MedioDeTransporte medio){
		for(Pedido item:medio.listaPedidos)
		{
			item.estado = Estado.EnSucursalDestino;
		}
		//Desocupar medio
		medio.Desocupar();

		//Poner el medio como disponible
		this.sucursal.SetMedioDisponible(medio);
	}

	public void AvisarSalidaMedio(MedioDeTransporte medio){
		this.sucursal.EnviarMedio(medio);
		medio.Viajar();
		Sistema.GetInstance().AgregarMedioEnTransito(medio);
	}

	public void AvisarArriboMedio(MedioDeTransporte medio){
		medio.estado = Estado.EnSucursalDestino;
		this.sucursal.AgregarMedioArrivado(medio);
		Sistema.GetInstance().EliminarMedioEnTransito(medio);
	}

	public void CrearMensaje(String mensaje, Sucursal destino) {
		Mensaje m = new Mensaje(mensaje, destino, this);
		//Enviar
		destino.RecibirMensaje(m);
	}

}
