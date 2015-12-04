package chilexplox.view;

import javafx.beans.property.*;

public class CargaTableModel {
	private final StringProperty id;
	private final StringProperty destino;
	private final StringProperty prioridad;
	private final StringProperty volumen;
	private final StringProperty fragil;
	private final StringProperty radioactivo;
	private final StringProperty refrigerado;

	public CargaTableModel(String id, String destino, String prioridad, String volumen,  String fragil,  String radioactivo,  String refrigerado) {
		this.id = new SimpleStringProperty(id);
		this.prioridad = new SimpleStringProperty(prioridad);
		this.destino = new SimpleStringProperty(destino);
		this.volumen = new SimpleStringProperty(volumen);
		this.fragil = new SimpleStringProperty(fragil);
		this.radioactivo = new SimpleStringProperty(radioactivo);
		this.refrigerado = new SimpleStringProperty(refrigerado);
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
	
	public StringProperty fragilProperty() {
        return this.fragil;
    }
	
	public StringProperty radioactivoProperty() {
        return this.radioactivo;
    }
	
	public StringProperty refrigeradoProperty() {
        return this.refrigerado;
    }

}
