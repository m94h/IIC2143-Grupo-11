package backend;

public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "bodega";
	}

	/*
	 * Cargar Pedido en medio
	 */
	public boolean CargarMedio(MedioDeTransporte medio, Pedido pedido){
		if(medio.GetPedidos().size() == 0){
			medio.setDestino(pedido.GetDestino()); // El primer pedido que se cargue en el medio determina su destino
		}
		if (medio.CargarPedido(pedido)) {
			pedido.SetCargadoEn(medio);
			pedido.SetCargadoPor(this);
			return true;
		}
		return false;
	}
	
	/*
	 * UndoCargarMedio (sacar pedido metido en camion)
	 */
	public void UndoCargarMedio(MedioDeTransporte medio, Pedido pedido){
		if (medio.GetPedidos().contains(pedido)) {
			medio.GetPedidos().remove(pedido);
		}
	}

	/*
	 * DescargarMedioCompleto para descargar todos los pedidos
	 */
	
	public void DescargarMedioCompleto(MedioDeTransporte medio){
		for(Pedido item:medio.listaPedidos)
		{
			item.Arrivado();
		}
		//Desocupar medio
		medio.Desocupar();

		//Poner el medio como disponible
		this.sucursal.SetMedioDisponible(medio);
	}
	
	/*
	 * Descargar Pedido de medio 
	 */
	public void DescargarPedido(MedioDeTransporte medio, Pedido pedido) {
		if (medio.GetPedidos().contains(pedido)) {
			pedido.Arrivado();
			medio.GetPedidos().remove(pedido);
		}
	}
	
	/*
	 * Para cuando no hay mas pedidos en el medio, se avisa como descargado
	 */
	public void MedioDescargado(MedioDeTransporte medio) {
		//Desocupar medio
		medio.Desocupar();
	
		//Poner el medio como disponible
		this.sucursal.SetMedioDisponible(medio);
	}
	
	/*
	 * Enviar pedido de vuelta (ERROR)
	 */
	public void EnviarPedidoDeVuelta(MedioDeTransporte medio, Pedido pedido, String mensaje) {
		if (medio.GetPedidos().contains(pedido)) {
			medio.GetPedidos().remove(pedido);
			pedido.DeVuelta();
			//enviar mensaje
			this.CrearMensaje("El pedido ID " + Integer.toString(pedido.GetId()) + " fue marcado como erroneo. El Operario de bodega escribio el siguiente mensaje: " + mensaje, pedido.GetOrigen());
			
			//mensaje error
			String mensajeError = "Pedido ID " + Integer.toString(pedido.GetId()) + " fue enviado de manera incorrecta a sucursal " + medio.GetDestino().GetDireccion();
			
			//agregar error
			Sistema.GetInstance().AgregarError(pedido.GetCreadoPor(), mensajeError);
			Sistema.GetInstance().AgregarError(pedido.GetCargadoPor(), mensajeError);
		}
	}

	public void CrearMensaje(String mensaje, Sucursal destino) {
		Mensaje m = new Mensaje(mensaje, destino, this);
		//Enviar
		destino.RecibirMensaje(m);
	}

}
