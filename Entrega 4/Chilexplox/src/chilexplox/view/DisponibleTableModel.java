package chilexplox.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DisponibleTableModel {
	private final StringProperty patente;
	private final StringProperty origen;
	private final StringProperty destino;
	private final StringProperty estado;

	public DisponibleTableModel(String patente, String origen, String destino, String estado) {
		this.patente = new SimpleStringProperty(patente);
		this.origen = new SimpleStringProperty(origen);
		this.destino = new SimpleStringProperty(destino);
		this.estado = new SimpleStringProperty(estado);
	}

	public String getId() {
		return patente.get();
	}

	public StringProperty patenteProperty() {
        return this.patente;
    }

	public StringProperty origenProperty() {
        return this.origen;
    }

	public StringProperty destinoProperty() {
        return this.destino;
    }

	public StringProperty estadoProperty() {
        return this.estado;
    }

}
