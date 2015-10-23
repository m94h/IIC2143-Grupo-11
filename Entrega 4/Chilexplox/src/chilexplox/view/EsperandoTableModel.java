package chilexplox.view;

import javafx.beans.property.*;

public class EsperandoTableModel {
	private final StringProperty patente;
	private final StringProperty estado;
	private final StringProperty capacidad;
	private final StringProperty destino;

	public EsperandoTableModel(String patente, String estado, String capacidad, String destino){
		this.patente = new SimpleStringProperty(patente);
		this.estado = new SimpleStringProperty(estado);
		this.capacidad = new SimpleStringProperty(capacidad);
		this.destino = new SimpleStringProperty(destino);
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
	public StringProperty destinoProperty() {
        return this.destino;
    }

}
