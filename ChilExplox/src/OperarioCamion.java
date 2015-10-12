
public class OperarioCamion extends Empleado {

	public OperarioCamion(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
	}

	public void DespacharCamion(Camion camion, Sucursal origen){

	}

	public void AvisarArrivoCamion(Camion camion, Sucursal destino){
		destino.AgregarCamionEsperando(camion);
		camion.estado = Estado.EnSucursalDestino;
	}
  
}
