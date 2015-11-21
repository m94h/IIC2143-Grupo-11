package chilexplox.view;

import backend.Sistema;
import chilexplox.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class CamionDetalleController {
	
	private MainApp mainApp;
	
	private String patente;
	
	@FXML
	private Label Patente;
	
	@FXML
	private Label capacidadMax;
	
	@FXML
	private Label capacidadDisponible;
	
	@FXML
	private Label ubicacion;
	
	@FXML
	private Label radioctivo;	
	
	@FXML
	private Label fragil;
	
	@FXML
	private Label refrigerado;
	
	@FXML
	private Button btnViaje;
	
	public void setMainApp(MainApp mainApp, String patente) {
        this.mainApp = mainApp;
        this.patente = patente;
        this.MostrarDetalle();
    }
	
	public void MostrarDetalle() {
		if (Sistema.GetInstance().GetMedio(this.patente) != null) {
			String[] detalles = Sistema.GetInstance().GetDetalleMedio(this.patente);
			Patente.setText(detalles[0]);
			capacidadMax.setText(detalles[1]);
			capacidadDisponible.setText(detalles[2]);
			ubicacion.setText(detalles[3]);
			radioctivo.setText(detalles[4]);
			fragil.setText(detalles[5]);
			refrigerado.setText(detalles[6]);
		}
		else {
			ViewHelper.ShowMessage("No existe un medio con patente: " + patente, AlertType.ERROR);
			this.mainApp.MostrarMenu();
		}
	}
	
	@FXML
	public void handleVerViaje() {
		this.mainApp.mostrarDetalleViaje(patente);;
	}
	
	@FXML
    private void handleVolver() {
		this.mainApp.MostrarMenu();
	}
}
