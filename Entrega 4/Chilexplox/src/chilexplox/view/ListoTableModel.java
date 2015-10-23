package chilexplox.view;

import javafx.beans.property.*;

public class ListoTableModel {
	private final StringProperty patente;
	private final StringProperty estado;
	private final StringProperty capacidad;
	private final StringProperty origen;

	public ListoTableModel(String patente, String estado, String capacidad, String origen){
		this.patente = new SimpleStringProperty(patente);
		this.estado = new SimpleStringProperty(estado);
		this.capacidad = new SimpleStringProperty(capacidad);
		this.origen = new SimpleStringProperty(origen);
	}

	//get de la patente
		public String getPatente() {
			return patente.get();
		}

		/*
		 * Properties
		 */
	public StringProperty patenteProperty() {
        return this.patente;
    }
	public StringProperty estadoProperty() {
        return this.estado;
    }
	public StringProperty capacidadProperty() {
        return this.capacidad;
    }
	public StringProperty origenProperty() {
        return this.origen;
    }

}
