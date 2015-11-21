package chilexplox.view;

import javafx.beans.property.*;

public class ReportePedidoTableModel {
	private final StringProperty id;
	private final StringProperty fecha;
	private final StringProperty origen;
	private final StringProperty destino;
	private final StringProperty monto;
	
	public ReportePedidoTableModel(String id, String fecha, String origen, String destino, String monto) {
		this.id = new SimpleStringProperty(id);
		this.fecha = new SimpleStringProperty(fecha);
		this.origen = new SimpleStringProperty(origen);
		this.destino = new SimpleStringProperty(destino);
		this.monto = new SimpleStringProperty(monto);
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
	public StringProperty fechaProperty() {
        return this.fecha;
    }
	public StringProperty montoProperty() {
        return this.monto;
    }
}
