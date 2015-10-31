package chilexplox.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MensajeTableModel {
	private final StringProperty emisario;
	private final StringProperty sucursalOrigen;
	
	public MensajeTableModel(String emisario, String sucursalOrigen) {
		this.emisario = new SimpleStringProperty(emisario);
		this.sucursalOrigen = new SimpleStringProperty(sucursalOrigen);
	}
	
	public StringProperty emisarioProperty() {
        return this.emisario;
    }
	public StringProperty sucursalOrigenProperty() {
        return this.sucursalOrigen;
    }
}
