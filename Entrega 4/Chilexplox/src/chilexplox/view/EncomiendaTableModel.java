package chilexplox.view;

import javafx.beans.property.*;

public class EncomiendaTableModel {
	private final StringProperty id;
	private final StringProperty nombre;
	private final StringProperty peso;
	private final StringProperty volumen;
	private final StringProperty precio;
	
	public EncomiendaTableModel(String id, String nombre, String peso, String volumen, String precio) {
		this.id = new SimpleStringProperty(id);
		this.nombre = new SimpleStringProperty(id);
		this.peso = new SimpleStringProperty(peso);
		this.volumen = new SimpleStringProperty(volumen);
		this.precio = new SimpleStringProperty(precio);
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
	public StringProperty nombreProperty() {
        return this.nombre;
    }
	public StringProperty pesoProperty() {
        return this.peso;
    }
	public StringProperty volumenProperty() {
        return this.volumen;
    }
	public StringProperty precioProperty() {
        return this.precio;
    }
}
