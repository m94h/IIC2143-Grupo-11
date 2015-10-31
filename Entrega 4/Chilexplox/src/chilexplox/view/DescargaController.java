package chilexplox.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import backend.*;
import chilexplox.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DescargaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private TextField id_pedido;

	@FXML
	private ChoiceBox patente_descarga;

	@FXML
    private void initialize() {
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		//poner los valores
				//medios disponibles para decargar
				ArrayList<MedioDeTransporte> medioPorDescargar = Sistema.GetInstance().GetSucursalLoged().GetMediosArrivados();
				for (int i = 0; i < medioPorDescargar.size(); i++) {
					MedioDeTransporte medio = medioPorDescargar.get(i);
					this.patente_descarga.getItems().add(medio.GetPatente());
				}
	}

	@FXML
	public void handleDescargarMedio() {
		if (this.patente_descarga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_descarga.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		operario.DescargarMedio(medio);
		ViewHelper.ShowMessage("El camion seleccionado ha sido descargado", AlertType.INFORMATION);
	}

	/*
	 * Para volver al menu principal
	 */
	@FXML
	private void handleVolverMenu() {
		this.mainApp.MostrarMenu();
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
