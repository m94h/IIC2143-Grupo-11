package chilexplox.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PedidoCamionTableModel {
	private final StringProperty id;
	private final StringProperty cliente;
	private final StringProperty urgencia;
	private final StringProperty peso;
	private final StringProperty volumen;
	
	public PedidoCamionTableModel(String id, String cliente, String urgencia, String peso, String volumen) {
		this.id = new SimpleStringProperty(id);
		this.cliente = new SimpleStringProperty(cliente);
		this.urgencia = new SimpleStringProperty(urgencia);
		this.peso = new SimpleStringProperty(peso);
		this.volumen = new SimpleStringProperty(volumen);
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
	public StringProperty clienteProperty() {
        return this.cliente;
    }
	public StringProperty urgenciaProperty() {
        return this.urgencia;
    }
	public StringProperty pesoProperty() {
        return this.peso;
    }
	public StringProperty volumenProperty() {
        return this.volumen;
    }

}
