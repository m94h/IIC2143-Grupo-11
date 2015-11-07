package chilexplox.view;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import backend.Auxiliar;
//Fachada del backend
import backend.Sistema;


//Import mainapp
import chilexplox.MainApp;

public class LoginController {
	
	@FXML
	private TextField rutCliente;
	
	@FXML
	private TextField codigoPedido;
	
	@FXML
	private TextField rutEmpleado;
	
	@FXML
	private PasswordField claveEmpleado;
	
	private MainApp mainApp;

	@FXML
    private void initialize() {
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void handleIngresarClientes() {
		
		if (codigoPedido.getText().isEmpty() || !Auxiliar.isInt(codigoPedido.getText())) {
			ViewHelper.ShowMessage("Ingresa un codigo correcto. Debe contener solo numeros.", AlertType.ERROR);
			return;
		}
		
		if (rutCliente.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingresa tu rut.", AlertType.ERROR);
			return;
		}
		
		if (Sistema.GetInstance().LogInCliente(rutCliente.getText(), Integer.parseInt(codigoPedido.getText()))) {
			
			//Login correcto
			
			this.mainApp.mostrarDetallePedido(Integer.parseInt(codigoPedido.getText()));

		} else {
			//Login incorrecto
			//Avisar 
			ViewHelper.ShowMessage("Has ingresado datos incorrectos. Intentalo nuevamente.", AlertType.ERROR);
			//Limpiar campos
			this.rutCliente.setText("");
			this.codigoPedido.setText("");
		}
	}
	
	@FXML
	private void handleIngresarEmplaedos() {
		if (rutEmpleado.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingresa tu rut.", AlertType.ERROR);
			return;
		}
		
		if (claveEmpleado.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingresa tu contrasena.", AlertType.ERROR);
			return;
		}
		
		
		if (Sistema.GetInstance().LogIn(rutEmpleado.getText(), claveEmpleado.getText())) {
			
			//Login correcto
			
			//Mostrar menu con opciones
			this.mainApp.IngresoCorrecto();
		} else {
			//Login incorrecto
			//Avisar 
			ViewHelper.ShowMessage("Has ingresado datos incorrectos. Intentalo nuevamente.", AlertType.ERROR);
			//Limpiar campos
			this.rutEmpleado.setText("");
			this.claveEmpleado.setText("");
		}
	}
}
