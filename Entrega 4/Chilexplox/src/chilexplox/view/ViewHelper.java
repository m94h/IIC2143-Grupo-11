package chilexplox.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

//Helper
public class ViewHelper {
			
	/*
	 * Para mostrar Mensajes Informativos
	 */
	public static void ShowMessage(String mensaje, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle("Informacion");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		
		alert.showAndWait();
	}
	
	/*
	 * Para confirmar
	 */
	public static boolean ShowConfirm(String mensaje) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmar");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true;
		}
		return false;
	}
	
	public static String PromptText(String mensaje) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Ingrese");
		dialog.setHeaderText(null);
		dialog.setContentText(mensaje);
		
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return result.get();
		}
		return null;
	}
	
}
