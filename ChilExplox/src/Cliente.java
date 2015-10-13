
public class Cliente extends Persona {
	protected String direccion;

	public Cliente(String rut, String nombre, int telefono, String direccion) {
		super(nombre, rut, telefono);
    this.direccion = direccion;
	}

}
