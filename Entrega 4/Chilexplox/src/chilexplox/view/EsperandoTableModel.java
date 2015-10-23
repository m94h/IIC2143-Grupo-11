package chilexplox.view;

import javafx.beans.property.*;

public class EsperandoTableModel {
	private final StringProperty patenteE;
	private final StringProperty estadoE;
	private final StringProperty capacidadE;
	private final StringProperty origen;

	public EsperandoTableModel(String patente, String estado, String capacidad, String destino){
		this.patenteE = new SimpleStringProperty(patente);
		this.estadoE = new SimpleStringProperty(estado);
		this.capacidadE = new SimpleStringProperty(capacidad);
		this.origen = new SimpleStringProperty(destino);
	}

	//get de la patente
		public String getPatente() {
			return patenteE.get();
		}

		/*
		 * Properties
		 */
	public StringProperty patenteEProperty() {
        return this.patenteE;
    }
	public StringProperty estadoEProperty() {
        return this.estadoE;
    }
	public StringProperty capacidadEProperty() {
        return this.capacidadE;
    }
	public StringProperty origenProperty() {
        return this.origen;
    }

}