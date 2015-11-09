package chilexplox.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PedidosCargadosTableModel {
	private final StringProperty id;
	private final StringProperty destino;
	private final StringProperty urgencia;
	private final StringProperty volumen;

	public PedidosCargadosTableModel(String id, String destino, String urgencia, String volumen) {
		this.id = new SimpleStringProperty(id);
		this.destino = new SimpleStringProperty(destino);
		this.urgencia = new SimpleStringProperty(urgencia);
		this.volumen = new SimpleStringProperty(volumen);
	}

	public String getId() {
		return id.get();
	}

	public StringProperty idCargadoProperty() {
        return this.id;
    }

	public StringProperty destinoCargadoProperty() {
        return this.destino;
    }

	public StringProperty urgenciaCargadoProperty() {
        return this.urgencia;
    }

	public StringProperty volumenCargadoProperty() {
        return this.volumen;
    }

}
