package chilexplox.view;

import javafx.beans.property.*;

public class PedidoTableModel {
	private final StringProperty id;
	private final StringProperty origen;
	private final StringProperty destino;
	private final StringProperty estado;
	
	public PedidoTableModel(String id, String origen, String destino, String estado) {
		this.id = new SimpleStringProperty(id);
		this.origen = new SimpleStringProperty(origen);
		this.destino = new SimpleStringProperty(destino);
		this.estado = new SimpleStringProperty(estado);
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
