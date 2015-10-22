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
	
	//gets and sets
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id.set(id);
	}
	public String getOrigen() {
		return origen.get();
	}
	public void setOrigen(String origen) {
		this.origen.set(origen);
	}
	public String getDestino() {
		return destino.get();
	}
	public void setDestino(String destino) {
		this.destino.set(destino);
	}
	public String getEstado() {
		return estado.get();
	}
	public void setEstado(String estado) {
		this.estado.set(estado);
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
