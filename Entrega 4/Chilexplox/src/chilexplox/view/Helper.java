package chilexplox.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

//Helper singleton
public class Helper {
	//Se define la instancia para que sea singleton
	private static Helper INSTANCE = new Helper();
	
	/*
	 * Constructor privado (Singleton)
	 */
	private Helper() {
	}
	
	/*
	 * Get Instance
	 */
	public static Helper GetInstance() {
		return INSTANCE;
	}
		
	/*
	 * Para mostrar Mensajes Informativos
	 */
	public void ShowMessage(String mensaje, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		
		alert.showAndWait();
	}
	
	/*
	 * Para confirmar
	 */
	public boolean ShowConfirm(String mensaje) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirme");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true;
		}
		return false;
	}
	
}
