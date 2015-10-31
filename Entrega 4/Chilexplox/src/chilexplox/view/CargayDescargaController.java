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

public class CargayDescargaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private TextField id_pedido;

	@FXML
	private ChoiceBox patente_carga;

	@FXML
	private ChoiceBox patente_descarga;

	@FXML
    private void initialize() {
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		//poner los valores
		//medios disponibles para cargar
		ArrayList<MedioDeTransporte> medioPorDescargar = Sistema.GetInstance().GetSucursalLoged().GetMediosArrivados();
		for (int i = 0; i < medioPorDescargar.size(); i++) {
			MedioDeTransporte medio = medioPorDescargar.get(i);
			this.patente_descarga.getItems().add(medio.GetPatente());
		}

		//medios disponibles para descargar
		ArrayList<MedioDeTransporte> medioPorCargar = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
		for (int i = 0; i < medioPorCargar.size(); i++) {
			MedioDeTransporte medio = medioPorCargar.get(i);
			this.patente_carga.getItems().add(medio.GetPatente());
		}


	}

	public CargayDescargaController() {

	}

	/*
	 * Handle boton cargar
	 */
	@FXML
	public void handleCargarPedido() {
		if (this.id_pedido.getText().isEmpty() || this.patente_carga.getSelectionModel().isEmpty()) {
			Helper.GetInstance().ShowMessage("Ingrese un id de pedido y seleccione un camion", AlertType.WARNING);
			return;
		}

		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.id_pedido.getText()));
		if (pedido != null) {
			if (pedido.GetCargadoEn() != null) {
				Helper.GetInstance().ShowMessage("Este pedido ya fue cargado a un camion", AlertType.ERROR);
				return;
			}
			MedioDeTransporte medio = Sistema.GetInstance().GetMedio((this.patente_carga.getSelectionModel().getSelectedItem().toString()));
			OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
			if (operario.CargarMedio(medio, pedido)) {
				Helper.GetInstance().ShowMessage("Pedido cargado correctamente al medio de transporte", AlertType.INFORMATION);
			} else {
				Helper.GetInstance().ShowMessage("El medio de transporte seleccionado no tiene capacidad para este pedido", AlertType.ERROR);
			}
		}
	}

	@FXML
	public void handleDescargarMedio() {
		if (this.patente_descarga.getSelectionModel().isEmpty()) {
			Helper.GetInstance().ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		operario.DescargarMedio(medio);
		Helper.GetInstance().ShowMessage("El camion seleccionado ha sido descargado", AlertType.INFORMATION);
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
