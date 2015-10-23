package chilexplox.view;

import javafx.beans.property.*;

public class ListoTableModel {
	private final StringProperty patenteL;
	private final StringProperty estadoL;
	private final StringProperty capacidadL;
	private final StringProperty destino;

	public ListoTableModel(String patente, String estado, String capacidad, String origen){
		this.patenteL = new SimpleStringProperty(patente);
		this.estadoL = new SimpleStringProperty(estado);
		this.capacidadL = new SimpleStringProperty(capacidad);
		this.destino = new SimpleStringProperty(origen);
	}

	//get de la patente
		public String getPatente() {
			return patenteL.get();
		}

		/*
		 * Properties
		 */
	public StringProperty patenteLProperty() {
        return this.patenteL;
    }
	public StringProperty estadoLProperty() {
        return this.estadoL;
    }
	public StringProperty capacidadLProperty() {
        return this.capacidadL;
    }
	public StringProperty destinoProperty() {
        return this.destino;
    }

}