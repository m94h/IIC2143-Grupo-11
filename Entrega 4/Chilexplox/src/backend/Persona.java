package backend;

public abstract class Persona {
	protected String nombre;
	protected String RUT;
	protected int telefono;

	public Persona(String nombre, String rut, int telefono) {
		this.nombre = nombre;
		this.RUT = rut;
		this.telefono = telefono;
	}

	public String GetRut() {
		return RUT;
	}

  public String GetNombre() {
    return this.nombre;
  }

  public int GetTelefono() {
    return this.telefono;
  }

}
