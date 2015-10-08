import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public abstract class Empleado extends Persona {
	protected int sueldo;
	protected List<Date> horarios_ingreso;
	protected List<Date> horarios_salida;

  public void MarcarIngresoTrabajo (Date hora) {
    this.horarios_ingreso.add(hora);
  }

  public void MarcarSalidaTrabajo (Date hora) {
    this.horarios_salida.add(hora);
  }
}
