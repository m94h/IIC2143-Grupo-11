package backend;

public class OperarioCamion extends Empleado {

	public OperarioCamion(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "camion";
	}

	public void DespacharCamion(Camion camion, Sucursal origen){
		origen.EnviarMedio(camion);
	}

	public void AvisarArrivoCamion(Camion camion, Sucursal destino){
		destino.AgregarMedioArrivado(camion);
		camion.estado = Estado.EnSucursalDestino;
	}

	public void AvisarSalidaMedio(MedioDeTransporte medio){
		medio.GetOrigen().EnviarMedio(medio);
		medio.Viajar();
		Sistema.GetInstance().AgregarMedioEnTransito(medio);
	}

	public void AvisarArriboMedio(MedioDeTransporte medio){
		medio.estado = Estado.EnSucursalDestino;
		medio.GetDestino().AgregarMedioArrivado(medio);
		Sistema.GetInstance().EliminarMedioEnTransito(medio);
	}

}
