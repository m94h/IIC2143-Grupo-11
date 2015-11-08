package chilexplox.view;

import java.util.ArrayList;
import java.util.Map;

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

public class CargaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private Label volumen;

	@FXML
	private TextField id_pedido;

	@FXML
	private ChoiceBox patente_carga;

	@FXML
	private TableView<CargaTableModel> tabla_cargas;

	@FXML
    private TableColumn<CargaTableModel, String> id_pedidoColumn;
    @FXML
    private TableColumn<CargaTableModel, String> destinoColumn;
    @FXML
    private TableColumn<CargaTableModel, String> prioridadColumn;
    @FXML
    private TableColumn<CargaTableModel, String> volumenColumn;

    private ObservableList<CargaTableModel> cargasData;

	@FXML
    private void initialize() {
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		// Set las properties para que se actualize la tabla
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.prioridadColumn.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());

		//medios disponibles para cargar
		ArrayList<MedioDeTransporte> medioPorCargar = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
		for (int i = 0; i < medioPorCargar.size(); i++) {
			MedioDeTransporte medio = medioPorCargar.get(i);
			this.patente_carga.getItems().add(medio.GetPatente());
		}

		//Inicializar observablearraylist
        this.cargasData = FXCollections.observableArrayList();

        this.UpdateCargas();
	}

	private void UpdateCargas() {
		//Get pedidos
		this.cargasData.clear();
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				if(pedido.GetOrigen().GetDireccion() == Sistema.GetInstance().GetSucursalLoged().GetDireccion()){ // Muestra solo los pedidos que salen de esa sucursal
					this.cargasData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen())));
				}
			}
		}
		this.tabla_cargas.setItems(this.cargasData);
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
	
	public void handlePatenteCargaAction(){
		
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
