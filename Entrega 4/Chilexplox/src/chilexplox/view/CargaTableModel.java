package chilexplox.view;

import javafx.beans.property.*;

public class CargaTableModel {
	private final StringProperty id;
	private final StringProperty destino;
	private final StringProperty prioridad;
	private final StringProperty volumen;

	public CargaTableModel(String id, String destino, String prioridad, String volumen) {
		this.id = new SimpleStringProperty(id);
		this.prioridad = new SimpleStringProperty(prioridad);
		this.destino = new SimpleStringProperty(destino);
		this.volumen = new SimpleStringProperty(volumen);
	}

	public String getId() {
		return id.get();
	}

	public StringProperty idProperty() {
        return this.id;
    }

	public StringProperty destinoProperty() {
        return this.destino;
    }

	public StringProperty prioridadProperty() {
        return this.prioridad;
    }

	public StringProperty volumenProperty() {
        return this.volumen;
    }

}
