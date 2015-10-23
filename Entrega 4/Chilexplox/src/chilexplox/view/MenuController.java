package chilexplox.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	
	@FXML
	private Button btnPedidos;
	
	@FXML
	private Button btnEnviarMensaje;
		
	@FXML
	private Button btnBandejaEntrada;
	
	
	private MainApp mainApp;
	
	@FXML
    private void initialize() {
		Sucursal sucursal = Sistema.GetInstance().GetSucursalLoged();		
		Empleado usuario = Sistema.GetInstance().GetUsuarioLoged();
		this.sucursal.setText(sucursal.GetDireccion());
		this.nombre.setText(usuario.GetNombre());
		
		
		//set permisos
		this.setPermisos(usuario);
    }
	
	/*
	 * Para setear permisos de usuario, es decir que botones puede usar
	 */
	private void setPermisos(Empleado usuario) {
		if (usuario.GetTipo().equals("bodega")) {
			this.btnEnviarMensaje.setDisable(false);
		}
		if (usuario.GetTipo().equals("venta")) {
			this.btnPedidos.setDisable(false);
			this.btnBandejaEntrada.setDisable(false);
		}
		
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
	public void handleMensaje () {
		this.mainApp.mostrarMensajes();
	}
	
	@FXML
	public void handleSalir () {
		this.mainApp.mostrarLogin();
	}
	
	
}
