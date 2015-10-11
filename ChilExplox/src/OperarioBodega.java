
public class OperarioBodega extends Empleado {

	public OperarioBodega(String nombre, String rut, int telefono, int sueldo){
		super(nombre, rut, telefono, sueldo);
	}

	public void CargarCamion(Camion camion){
		// deberíamos verificar la cap del camion en algun punto y ver si se puede cargar
	}

	public void DescargarCamion(Camion camion){

	}

	public void EnviarMensaje(String mensaje){
		
	}
}
