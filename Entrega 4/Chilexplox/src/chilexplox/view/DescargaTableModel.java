package chilexplox.view;

import javafx.beans.property.*;

public class DescargaTableModel {
	private final StringProperty id;
	private final StringProperty origen;
	private final StringProperty prioridad;
	private final StringProperty volumen;

	public DescargaTableModel(String id, String origen, String prioridad, String volumen) {
		this.id = new SimpleStringProperty(id);
		this.prioridad = new SimpleStringProperty(prioridad);
		this.origen = new SimpleStringProperty(origen);
		this.volumen = new SimpleStringProperty(volumen);
	}

	public String getId() {
		return id.get();
	}

	public StringProperty idProperty() {
        return this.id;
    }

	public StringProperty origenProperty() {
        return this.origen;
    }

	public StringProperty prioridadProperty() {
        return this.prioridad;
    }

	public StringProperty volumenProperty() {
        return this.volumen;
    }

}
