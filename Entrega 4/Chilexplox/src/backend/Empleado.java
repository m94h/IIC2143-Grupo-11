package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public abstract class Empleado extends Persona {
	protected int sueldo;
	protected List<Date> horarios_ingreso;
	protected List<Date> horarios_salida;
	protected Sucursal sucursal;
	protected String tipo_empleado;
	protected String clave;

  public Empleado(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal, String clave) {
    super(nombre, rut, telefono);
    this.sueldo = sueldo;
    this.horarios_ingreso = new ArrayList<Date>();
    this.horarios_salida = new ArrayList<Date>();
    this.sucursal = sucursal;
    this.clave = clave;
  }
 

  public String GetTipo() {
    return this.tipo_empleado;
  }

  public int GetSueldo() {
    return this.sueldo;
  }

  public Sucursal GetSucursal() {
    return this.sucursal;
  }

  public String GetClave() {
    return this.clave;
  }

  public void MarcarIngresoTrabajo (Date hora) {
    this.horarios_ingreso.add(hora);
  }

  public void MarcarSalidaTrabajo (Date hora) {
    this.horarios_salida.add(hora);
  }
  
  public boolean CheckLogin(String clave) {
		if (this.clave.equals(clave)) {
			return true;
		}
		return false;
	}
}
