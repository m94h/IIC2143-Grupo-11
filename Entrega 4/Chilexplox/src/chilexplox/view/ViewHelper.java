package chilexplox.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

//Helper
public class ViewHelper {
			
	/*
	 * Para mostrar Mensajes Informativos
	 */
	public static void ShowMessage(String mensaje, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		
		alert.showAndWait();
	}
	
	/*
	 * Para confirmar
	 */
	public static boolean ShowConfirm(String mensaje) {
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
