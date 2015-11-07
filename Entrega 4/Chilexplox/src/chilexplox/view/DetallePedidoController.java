package chilexplox.view;

import backend.Empleado;
import backend.Sistema;
import backend.Sucursal;
import chilexplox.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//Fachada del backend
import backend.Sistema;

public class DetallePedidoController {
	
	private MainApp mainApp;

	private int id_pedido;
	
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
		String[] detalles = Sistema.GetInstance().GetDetallePedido(0);
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
 }

	public void setMainApp(MainApp mainApp) {
  this.mainApp = mainApp;
 }
	
	/*
	public void HandleVerDetalle() {
		int pedido_id = Integer.parseInt(id_pedido.getText());
		String[] detalles = Sistema.GetInstance().GetDetallePedido(pedido_id);
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
	}
	*/
	

}
