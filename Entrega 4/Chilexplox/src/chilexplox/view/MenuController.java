package chilexplox.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


//Fachada del backend
import backend.Sistema;
import backend.Sucursal;

import java.util.Map;

import backend.Empleado;
import chilexplox.MainApp;

public class MenuController {

	@FXML
	private ChoiceBox sucursales;

	@FXML
	private Label nombre;

	@FXML
	private Label rol;

	@FXML
	private Button btnPedidos;

	@FXML
	private Button btnArrivoSalida;

	@FXML
	private Button btnCarga;

	@FXML
	private Button btnDescarga;

	@FXML
	private Button btnEnviarMensaje;

	@FXML
	private Button btnBandejaEntrada;

	@FXML
	private Button btnDetallePedido;

	@FXML
	private Button btnDetalleCamion;


	private MainApp mainApp;

	@FXML
    private void initialize() {

		//get sucursales
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			this.sucursales.getItems().add(entry.getValue().GetDireccion());
		}
		//Seleccionar la actual
		this.sucursales.getSelectionModel().select(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		Empleado usuario = Sistema.GetInstance().GetUsuarioLoged();
		this.nombre.setText(usuario.GetNombre());
		this.rol.setText(usuario.GetTipo());

		//set permisos
		this.setPermisos(usuario);

		//cambio de sucursal listener
		this.sucursales.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
	      @Override
	      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
	    	  Sistema.GetInstance().CambiarSucursal(sucursales.getItems().get((Integer) number2).toString());
	    	  System.out.println("Cambiado sucursal");
	      }
	    });
    }

	/*
	 * Para setear permisos de usuario, es decir que botones puede usar
	 */
	private void setPermisos(Empleado usuario) {

		//Por defecto, todos desactivados
		this.btnPedidos.setDisable(true);
		this.btnCarga.setDisable(true);
		this.btnDescarga.setDisable(true);
		this.btnEnviarMensaje.setDisable(true);
		this.btnArrivoSalida.setDisable(true);
		this.btnBandejaEntrada.setDisable(true);
		this.btnDetalleCamion.setDisable(false);  // cambiar

		if (usuario.GetTipo().equals("bodega")) {
			this.btnCarga.setDisable(false);
			this.btnDescarga.setDisable(false);
			this.btnEnviarMensaje.setDisable(false);
		}
		if (usuario.GetTipo().equals("venta")) {
			this.btnPedidos.setDisable(false);
			this.btnBandejaEntrada.setDisable(false);
		}
		if (usuario.GetTipo().equals("camion")) {
			this.btnArrivoSalida.setDisable(false);
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

	@FXML
	public void handleArrivoSalida() {
		this.mainApp.mostrarArrivoSalida();
	}

	@FXML
	public void handleCarga() {
		this.mainApp.mostrarCarga();
	}

	@FXML
	public void handleDescarga() {
		this.mainApp.mostrarDescarga();
	}

	@FXML
	public void handleBandejaDeEntrada() {
		this.mainApp.mostrarBandejaDeEntrada();
	}

	@FXML
	public void handleDetallePedidos() {
		String id_pedido = ViewHelper.PromptText("Ingrese el id del pedido que desea ver");
		if (id_pedido != null) {
			this.mainApp.mostrarDetallePedido(Integer.parseInt((id_pedido)));
		}
	}
	
	@FXML
	public void handleDetalleCamion() {
		String patente = ViewHelper.PromptText("Ingrese la patente del camion que desea ver");
		if (patente != null) {
			this.mainApp.mostrarDetalleCamion(patente);
		}
	}

	@FXML
	public void handleCambiarSusursal() {
		Sistema.GetInstance().CambiarSucursal(this.sucursales.getSelectionModel().getSelectedItem().toString());
	}


}
