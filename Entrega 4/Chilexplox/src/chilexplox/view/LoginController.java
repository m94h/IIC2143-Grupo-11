package chilexplox.view;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

//Fachada del backend
import backend.Sistema;


//Import mainapp
import chilexplox.MainApp;

public class LoginController {
	
	@FXML
	private TextField rut;
	
	@FXML
	private PasswordField clave;
	
	private MainApp mainApp;

	@FXML
    private void initialize() {
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void handleIngresar() {
		if (Sistema.GetInstance().LogIn(rut.getText(), clave.getText())) {
			
			//Login correcto
			
			//Mostrar menu con opciones
			this.mainApp.IngresoCorrecto();
		} else {
			//Login incorrecto
			//Avisar 
			ViewHelper.ShowMessage("Has ingresado datos incorrectos. Intentalo nuevamente.", AlertType.ERROR);
			//Limpiar campos
			this.rut.setText("");
			this.clave.setText("");
		}
	}
}
