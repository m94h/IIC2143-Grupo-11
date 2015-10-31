package chilexplox.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MensajeTableModel {
	private final StringProperty id;
	private final StringProperty emisario;
	private final StringProperty sucursalOrigen;
	
	public MensajeTableModel(String id, String emisario, String sucursalOrigen) {
		this.id = new SimpleStringProperty(id);
		this.emisario = new SimpleStringProperty(emisario);
		this.sucursalOrigen = new SimpleStringProperty(sucursalOrigen);
	}
	
	public StringProperty idProperty() {
        return this.emisario;
    }
	
	public StringProperty emisarioProperty() {
        return this.emisario;
    }
	public StringProperty sucursalOrigenProperty() {
        return this.sucursalOrigen;
    }
}
