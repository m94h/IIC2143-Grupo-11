package chilexplox.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import backend.*;
import chilexplox.MainApp;
import javafx.fxml.FXML;
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
			this.ShowMessage("Ingrese un id de pedido y seleccione un camion");
			return;
		}
		
		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.id_pedido.getText()));
		if (pedido != null) {
			if (pedido.GetCargadoEn() != null) {
				this.ShowMessage("Este pedido ya fue cargado a un camion");
				return;
			}
			MedioDeTransporte medio = Sistema.GetInstance().GetTransporte((this.patente_carga.getSelectionModel().getSelectedItem().toString()));
			OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
			if (operario.CargarMedio(medio, pedido)) {
				this.ShowMessage("Pedido cargado correctamente al medio de transporte");
			} else {
				this.ShowMessage("El medio de transporte seleccionado no tiene capacidad para este pedido");
			}
		}
	}
	
	@FXML
	public void handleDescargarMedio() {
		if (this.patente_descarga.getSelectionModel().isEmpty()) {
			this.ShowMessage("Seleccione un medio de transporte");
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetTransporte(this.patente_carga.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		operario.DescargarMedio(medio);
		this.ShowMessage("El camion seleccionado ha sido descargado");
	}
	
	/*
	 * Para mostrar alertas
	 */
	private void ShowMessage(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje);
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
