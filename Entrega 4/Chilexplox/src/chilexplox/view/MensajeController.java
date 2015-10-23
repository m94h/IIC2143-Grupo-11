package chilexplox.view;

import chilexplox.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//Fachada del backend
import backend.Sistema;
import backend.Sucursal;
import backend.Mensaje;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import backend.Empleado;
import backend.OperarioBodega;

public class MensajeController {
	
	private MainApp mainApp;
	
	@FXML
	private TextArea mensaje;
	
	@FXML
	private ChoiceBox destino;
	
	private Map<String, Sucursal> sucursales;
		
	@FXML
    private void initialize() {
		sucursales = new HashMap<String, Sucursal>(); 
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			Sucursal s = entry.getValue();
			this.destino.getItems().add(s.GetDireccion());
			this.sucursales.put(s.GetDireccion(), s);
		}
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
    private void Enviar() {
		Sistema sistema = Sistema.GetInstance();
		Empleado usuario = sistema.GetUsuarioLoged();
		String direccion = destino.getSelectionModel().getSelectedItem().toString();
		
		if (usuario.GetTipo().equals("bodega")) {
			sistema.EnviarMensaje((OperarioBodega)usuario, mensaje.getPromptText(), sucursales.get(direccion));        
			this.mensaje.setAccessibleText("");
		}
		else {
			this.ShowMessage("Solo un operario de bodega puede enviar mensajes");
		}
	}
}
