package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

//backend
import backend.*;

public class ArriboySalidaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private ChoiceBox patenteArribo;

	@FXML
	private ChoiceBox patenteSalida;

	@FXML
	private void initialize() {
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());
		// Lista medios listos para salir
		ArrayList<MedioDeTransporte> mediosDisponibles = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
		for (int i = 0; i < mediosDisponibles.size(); i++) {
			MedioDeTransporte medio = mediosDisponibles.get(i);
			this.patenteSalida.getItems().add(medio.GetPatente());
		}

		// Lista medios para arrivar
		ArrayList<MedioDeTransporte> mediosEnTransito = Sistema.GetInstance().GetMediosEnTransito();
		for(int i = 0; i < mediosEnTransito.size(); i++){
			MedioDeTransporte medio = mediosEnTransito.get(i);
			this.patenteArribo.getItems().add(medio.GetPatente());
		}
	}

	@FXML
	public void handleAvisarArribo() {
		if (this.patenteArribo.getSelectionModel().isEmpty()) {
			this.ShowMessage("Seleccione un medio de transporte");
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patenteArribo.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		operario.AvisarArriboMedio(medio);
		this.ShowMessage("El camion ha arribado");
	}

	@FXML
	public void handleAvisarSalida() {
		if (this.patenteSalida.getSelectionModel().isEmpty()) {
			this.ShowMessage("Seleccione un medio de transporte");
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patenteSalida.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		operario.AvisarSalidaMedio(medio);
		this.ShowMessage("El camion esta en tránsito");
	}


	/*
	 * Para mostrar alertas
	 */
	private void ShowMessage(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje);
	}

	@FXML
	private void handleVolverMenu() {
		this.mainApp.MostrarMenu();
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}

