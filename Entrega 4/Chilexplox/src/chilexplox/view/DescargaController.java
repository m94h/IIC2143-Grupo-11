package chilexplox.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import backend.*;
import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DescargaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private ChoiceBox patente_descarga;

	@FXML
	private TableView<DescargaTableModel> tabla_descargas;

	@FXML
    private TableColumn<DescargaTableModel, String> id_pedidoColumn;
    @FXML
    private TableColumn<DescargaTableModel, String> origenColumn;
    @FXML
    private TableColumn<DescargaTableModel, String> prioridadColumn;
    @FXML
    private TableColumn<DescargaTableModel, String> volumenColumn;

    private ObservableList<DescargaTableModel> descargasData;

	@FXML
    private void initialize() {
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());
		
		// Set las properties para que se actualize la tabla disponibles
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
		this.prioridadColumn.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
		this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
		
		//Inicializar observablearraylist
		this.descargasData = FXCollections.observableArrayList();

		//actualizar medios arrivados
		this.UpdateMediosArrivados();
		
	}
	
	private void UpdateMediosArrivados() {
		this.patente_descarga.getItems().clear();
		//medios disponibles para decargar
		ArrayList<MedioDeTransporte> medioPorDescargar = Sistema.GetInstance().GetSucursalLoged().GetMediosArrivados();
		for (int i = 0; i < medioPorDescargar.size(); i++) {
			MedioDeTransporte medio = medioPorDescargar.get(i);
			this.patente_descarga.getItems().add(medio.GetPatente());
		}
	}
	
	@FXML
	public void handleCargarDatosCamion() {
		if (this.patente_descarga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		
		this.ActualizarTablaPedidos();
		
		ViewHelper.ShowMessage("Pedidos del camion cargados", AlertType.INFORMATION);
		
	}
	
	@FXML
	private void handleDescargarCamionCompleto() {
		if (this.patente_descarga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}

		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_descarga.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		
		operario.DescargarMedioCompleto(medio);
		
		ViewHelper.ShowMessage("Camion descargado completamente.", AlertType.INFORMATION);
		this.UpdateMediosArrivados();
		
	}
	
	private void ActualizarTablaPedidos() {
		//Listar pedidos en el camion
		this.descargasData.clear();
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_descarga.getSelectionModel().getSelectedItem().toString());
		
		if (medio.GetPedidos().size() > 0) { //Si hay medios en transito
			for (int i = 0; i < medio.GetPedidos().size(); i++) {
				Pedido pedido = medio.GetPedidos().get(i);
				this.descargasData.add(new DescargaTableModel(Integer.toString(pedido.GetId()), pedido.GetOrigen().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen())));
			}
		}
		this.tabla_descargas.setItems(this.descargasData);
	}

	@FXML
	public void handleDescargarPedido() {
		if (this.patente_descarga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}

		if (this.tabla_descargas.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un pedido a descargar", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_descarga.getSelectionModel().getSelectedItem().toString());
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.tabla_descargas.getSelectionModel().getSelectedItem().getId()));
		
		if (pedido.GetDestino() != medio.GetDestino()) {
			//error en el envio
			//se debe enviar de vuelta.
			operario.EnviarPedidoDeVuelta(medio, pedido); //este metodo lo devuelve y envia un mensaje
		}
		
		//descargar pedido
		operario.DescargarPedido(medio, pedido);
		//actualizar la tabla
		this.ActualizarTablaPedidos();
		
		if (medio.GetPedidos().size() == 0) {
			operario.MedioDescargado(medio);
			ViewHelper.ShowMessage("Ya no quedan mas pedidos. El camion ha sido descargado completamente.", AlertType.INFORMATION);
			this.UpdateMediosArrivados();
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
