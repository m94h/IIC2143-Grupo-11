package chilexplox.view;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

//Fachada del backend
import backend.Sistema;


//Import mainapp
import chilexplox.MainApp;

public class LoginController {
	
	@FXML
	private TextField rut;
	
	@FXML
	private TextField clave;
	
	private MainApp mainApp;

	@FXML
    private void initialize() {
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	private void ShowMessage(String message) {
	    EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            JOptionPane.showMessageDialog(null, message);
	        }
	    });
	}
	
	@FXML
	private void handleIngresar() {
		if (Sistema.GetInstance().LogIn(rut.getText(), clave.getText())) {
			
			//Login correcto
			//Avisar 
			this.ShowMessage("Has ingresado correctamente al sistema");
			
			//Mostrar menu con opciones
			this.mainApp.IngresoCorrecto();
		} else {
			//Login incorrecto
			//Avisar 
			this.ShowMessage("Has ingresado datos incorrectos. Intentalo nuevamente.");
			//Limpiar campos
			this.rut.setText("");
			this.clave.setText("");
		}
	}
}
