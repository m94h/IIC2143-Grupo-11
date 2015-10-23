package chilexplox.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


//Fachada del backend
import backend.Sistema;
import backend.Sucursal;
import backend.Empleado;
import chilexplox.MainApp;

public class MenuController {
	
	@FXML
	private Label sucursal;
	
	@FXML
	private Label nombre;
	
	private MainApp mainApp;
	
	@FXML
    private void initialize() {
		Sucursal sucursal = Sistema.GetInstance().GetSucursalLoged();		
		Empleado usuario = Sistema.GetInstance().GetUsuarioLoged();
		this.sucursal.setText(sucursal.GetDireccion());
		this.nombre.setText(usuario.GetNombre());
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public MenuController() {
		
	}
	
	@FXML
	public void handlePedidos () {
		this.mainApp.MostrarPedidos();
	}
	
	@FXML
	public void handleClientes () {
		this.mainApp.MostrarClientes();
	}
	
	@FXML
	public void handleMensaje () {
		this.mainApp.mostrarMensajes();
	}
	
	@FXML
	public void handleSalir () {
		this.mainApp.mostrarLogin();
	}
	
	
}
