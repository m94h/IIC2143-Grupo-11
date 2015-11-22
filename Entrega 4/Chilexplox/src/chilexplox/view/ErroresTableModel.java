package chilexplox.view;

import javafx.beans.property.*;

public class ErroresTableModel {
	private final StringProperty id;
	private final StringProperty fecha;
	private final StringProperty empleado;
	private final StringProperty mensaje;
	
	public ErroresTableModel(String id, String fecha, String empleado, String mensaje) {
		this.id = new SimpleStringProperty(id);
		this.fecha = new SimpleStringProperty(fecha);
		this.empleado = new SimpleStringProperty(empleado);
		this.mensaje = new SimpleStringProperty(mensaje);
	}
	
	//get del id
	public String getId() {
		return id.get();
	}
	
	/*
	 * Properties
	 */
	public StringProperty idProperty() {
        return this.id;
    }
	public StringProperty fechaProperty() {
        return this.fecha;
    }
	public StringProperty empleadoProperty() {
        return this.empleado;
    }
	public StringProperty mensajeProperty() {
        return this.mensaje;
    }
	
}
