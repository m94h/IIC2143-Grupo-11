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

public class CargaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private TextField id_pedido;

	@FXML
	private ChoiceBox patente_carga;

	@FXML
    private void initialize() {
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		//medios disponibles para cargar
		ArrayList<MedioDeTransporte> medioPorCargar = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
		for (int i = 0; i < medioPorCargar.size(); i++) {
			MedioDeTransporte medio = medioPorCargar.get(i);
			this.patente_carga.getItems().add(medio.GetPatente());
		}
	}

	public CargaController() {

	}

	/*
	 * Handle boton cargar
	 */
	@FXML
	public void handleCargarPedido() {
		if (this.id_pedido.getText().isEmpty() || this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Ingrese un id de pedido y seleccione un camion", AlertType.WARNING);
			return;
		}

		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.id_pedido.getText()));
		if (pedido != null) {
			if (pedido.GetCargadoEn() != null) {
				ViewHelper.ShowMessage("Este pedido ya fue cargado a un camion", AlertType.ERROR);
				return;
			}
			MedioDeTransporte medio = Sistema.GetInstance().GetMedio((this.patente_carga.getSelectionModel().getSelectedItem().toString()));
			OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
			if (operario.CargarMedio(medio, pedido)) {
				ViewHelper.ShowMessage("Pedido cargado correctamente al medio de transporte", AlertType.INFORMATION);
			} else {
				ViewHelper.ShowMessage("El medio de transporte seleccionado no tiene capacidad para este pedido", AlertType.ERROR);
			}
		}
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
