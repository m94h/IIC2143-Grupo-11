import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Sucursal {
  private int id;
	private String direccion;
	private int telefono;
	private int capacidad;
	private Map<String, Empleado> empleados;
	private ArrayList<Camion> camionesEsperando;
  private ArrayList<Camion> camionesListos;

	public Sucursal(String direccion, int telefono, int capacidad) {
    this.id = Sistema.GetInstance().Get_id_sucursal();
    this.Initialize(direccion, telefono, capacidad);
  }

	public Sucursal(int id, String direccion, int telefono, int capacidad) {
    this.id = id;
    this.Initialize(direccion, telefono, capacidad);
  }

  private void Initialize (String direccion, int telefono, int capacidad) {
    this.direccion = direccion;
    this.telefono = telefono;
    this.capacidad = capacidad;
    this.empleados = new HashMap<String, Empleado>();
    this.camionesEsperando = new ArrayList<Camion>();
    this.camionesListos = new ArrayList<Camion>();
  }

  public void AgregarEmpleado(String id_empleado, Empleado empleado) {
    this.empleados.put(id_empleado, empleado);
  }
  
  public void CambiarDireccion(String nuevaDireccion){
	  this.direccion = nuevaDireccion;
  }
  
  public void AgrandarSucursal(int nueva_capacidad){
	  this.capacidad = this.capacidad + nueva_capacidad;
  }
  
  public void AgregarCamionEsperando(Camion camion){
	  this.camionesEsperando.add(camion);
  }

  public void AgregarCamionListo(Camion camion){
    this.camionesEsperando.remove(camion);
    this.camionesListos.add(camion);
  }
  
  public void EnviarCamion(Camion camion){
	  this.camionesListos.remove(camion);
  }
	
}
