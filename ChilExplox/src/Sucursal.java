
public class Sucursal {
  private int id;
	private String direccion;
	private int telefono;
	private int capacidad;
	private Map<Integer, Empleado> empleados;

	
	public Sucursal(int id, String sucursal, int telefono, int capacidad) {
    this.id = id;
    this.direccion = direccion;
    this.telefono = telefono;
    this.capacidad = capacidad;
    this.empleados = new HashMap<Integer, Empleado>();
  }

  public void AgregarEmpleado(int id_empleado, Empleado empleado) {
    this.empleados.put(id_empleado, empleado);
  }
	
}
