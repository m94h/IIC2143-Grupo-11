package chilexplox.view;

import backend.Empleado;
import backend.Sistema;
import backend.Sucursal;
import chilexplox.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//Fachada del backend
import backend.Sistema;

public class DetallePedidoController {
	
	private MainApp mainApp;
	
	@FXML
	private Label header;
	
	@FXML
	private Label nombreDueno;
	
	@FXML
	private Label volumen;
	
	@FXML
	private Label peso;
	
	@FXML
	private Label urgencia;	
	
	@FXML
	private Label estado;
	
	@FXML
	private Label fechaCreacion;
	
	@FXML
	private Label sucursalOrigen;
	
	@FXML
	private Label nombreVendedor;
	
	@FXML
	private Label fechaEnvio;
	
	@FXML
	private Label patente;
	
	@FXML
	private Label conductor;
	
	@FXML
	private Label fechaLlegada;
	
	@FXML
	private Label sucursalDestino;
	
	@FXML
	private Label estadoLlegada;
	
	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp, int id_pedido) {
		this.mainApp = mainApp;
		this.MostrarDetalle(id_pedido);
	}
	
	
	public void MostrarDetalle(int id_pedido) {
		if (Sistema.GetInstance().GetPedido(id_pedido) != null) {
			String[] detalles = Sistema.GetInstance().GetDetallePedido(id_pedido);
			nombreDueno.setText(detalles[0]);
			volumen.setText(detalles[1]);
			peso.setText(detalles[2]);
			urgencia.setText(detalles[3]);
			estado.setText(detalles[4]);
			fechaCreacion.setText(detalles[5]);
			sucursalOrigen.setText(detalles[6]);
			nombreVendedor.setText(detalles[7]);
			fechaEnvio.setText(detalles[8]);
			patente.setText(detalles[9]);
			conductor.setText(detalles[10]);
			fechaLlegada.setText(detalles[11]);
			sucursalDestino.setText(detalles[12]);
			estadoLlegada.setText(detalles[13]);
			header.setText("Mostrando pedido Id: " + id_pedido);
		}
		else {
			ViewHelper.ShowMessage("No existe un pedido con Id: " + id_pedido, AlertType.ERROR);
			this.mainApp.MostrarMenu();
		}
	}
	
	@FXML
	private void handleVolverMenu() {
		this.mainApp.MostrarMenu();
	}
}
