import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Sucursal {
  private int id;
	private String direccion;
	private int telefono;
	private int capacidad;
	private Map<Integer, Empleado> empleados;
	private ArrayList<Camion> listaCamiones = new ArrayList<Camion>();

	
	public Sucursal(int id, String direccion, int telefono, int capacidad) {
    this.id = id;
    this.direccion = direccion;
    this.telefono = telefono;
    this.capacidad = capacidad;
    this.empleados = new HashMap<Integer, Empleado>();
  }

  public void AgregarEmpleado(int id_empleado, Empleado empleado) {
    this.empleados.put(id_empleado, empleado);
  }
  
  public void CambiarDireccion(String nuevaDireccion){
	  this.direccion = nuevaDireccion;
  }
  
  public void AgrandarSucursal(int nueva_capacidad){
	  this.capacidad = this.capacidad + nueva_capacidad;
  }
  
  public void AgregarCamion(Camion cam){
	  this.listaCamiones.add(cam);
  }
  
  public void EnviarCamion(Camion cam){
	  this.listaCamiones.remove(cam);
  }
	
}
