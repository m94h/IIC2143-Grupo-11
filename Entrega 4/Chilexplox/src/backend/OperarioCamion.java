package backend;

public class OperarioCamion extends Empleado {

	public OperarioCamion(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave){
		super(nombre, rut, telefono, sueldo, sucursal, clave);
		this.tipo_empleado = "camion";
	}

	public void DespacharMedio(MedioDeTransporte medio, Sucursal origen){
		origen.EnviarMedio(medio);
		medio.setConductor((Empleado)this);
	}

	public void AvisarArriboMedio(MedioDeTransporte medio, Sucursal destino){
		destino.AgregarMedioArrivado(medio);
		medio.estado = Estado.EnSucursalDestino;
	}

}
