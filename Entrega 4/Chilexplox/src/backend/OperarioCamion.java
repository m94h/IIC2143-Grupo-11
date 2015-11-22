package backend;

public class OperarioCamion extends Empleado {

	public OperarioCamion(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "camion";
	}

	public void DespacharMedio(MedioDeTransporte medio, Sucursal origen){
		medio.setConductor((Empleado)this);
		origen.EnviarMedio(medio);
		medio.Viajar();
		Sistema.GetInstance().AgregarMediosEnTransito(medio);
	}

	public void AvisarArriboMedio(MedioDeTransporte medio, Sucursal destino){
		destino.AgregarMedioArrivado(medio);
		Sistema.GetInstance().EliminarMediosEnTransito(medio);
		//medio.estado = Estado.EnSucursalDestino;
		//cambiado a medio
		medio.ArribarMedio();
	}
	
	public void RetornarMedio(MedioDeTransporte medio, String mensaje){
		medio.origen.AgregarMedioDisponible(medio);
		Sistema.GetInstance().EliminarMediosEnTransito(medio);
		//enviar mensaje
		this.CrearMensaje("Se ha retornado el medio de transporte patente " + medio.GetPatente() + " a su sucursal de origen " + medio.GetOrigen().GetDireccion() + ". El operario le envia el siguiente mensaje: " + mensaje, medio.GetOrigen());

		String mensajeError = "Retorno medio patente " + medio.GetPatente() + " debido a " + mensaje;
		// Agregar error
		Sistema.GetInstance().AgregarError(Sistema.GetInstance().GetUsuarioLoged(), mensajeError);
		
		// Devolver medio
		medio.DevolverMedio();
	}

	public void CrearMensaje(String mensaje, Sucursal destino) {
		Mensaje m = new Mensaje(mensaje, destino, this);
		//Enviar
		destino.RecibirMensaje(m);
	}
}
