package chilexplox.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private ChoiceBox sucursal_destino;

	@FXML
	private TableView<CargaTableModel> tablaPedidosCargados;

	@FXML
    private TableColumn<CargaTableModel, String> id_pedidoColumn;
    @FXML
    private TableColumn<CargaTableModel, String> destinoColumn;
    @FXML
    private TableColumn<CargaTableModel, String> prioridadColumn;
    @FXML
    private TableColumn<CargaTableModel, String> volumenColumn;

    private ObservableList<CargaTableModel> pedidosCargadosData;
    
    @FXML
	private TableView<CargaTableModel> tablaPedidosPosibles;
    
    @FXML
    private TableColumn<CargaTableModel, String> id_pedidoColumn2;
    @FXML
    private TableColumn<CargaTableModel, String> destinoColumn2;
    @FXML
    private TableColumn<CargaTableModel, String> prioridadColumn2;
    @FXML
    private TableColumn<CargaTableModel, String> volumenColumn2;

    private ObservableList<CargaTableModel> pedidosPosiblesData;

	@FXML
    private void initialize() {
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		// Set las properties para que se actualize la tabla
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.prioridadColumn.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
        this.id_pedidoColumn2.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.destinoColumn2.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.prioridadColumn2.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        this.volumenColumn2.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());

		//medios disponibles para cargar
		ArrayList<MedioDeTransporte> medioPorCargar = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
		for (int i = 0; i < medioPorCargar.size(); i++) {
			MedioDeTransporte medio = medioPorCargar.get(i);
			this.patente_carga.getItems().add(medio.GetPatente());
		}

		//sucursales donde se puede enviar
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			if(!this.sucursal.getText().equals(entry.getValue().GetDireccion())){
				this.sucursal_destino.getItems().add(entry.getValue().GetDireccion());
			}
		}

		//Inicializar observablearraylist
        this.pedidosCargadosData = FXCollections.observableArrayList();
        this.pedidosPosiblesData = FXCollections.observableArrayList();

	}
	
	@FXML
	private void handleCargaIndividual() {
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}

		if (this.sucursal_destino.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un sucursal", AlertType.WARNING);
			return;
		}
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());
		
		this.pedidosPosiblesData.clear();
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				// Muestra solo los pedidos que salen de esa sucursal y que van a la sucursal seleccionada
				if(pedido.GetOrigen().GetDireccion().equals(Sistema.GetInstance().GetSucursalLoged().GetDireccion()) && pedido.GetDestino().GetDireccion().equals(this.sucursal_destino.getSelectionModel().getSelectedItem().toString()) && pedido.GetCargadoEn() == null){ 
					this.pedidosPosiblesData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen())));
				}
			}
		}
		this.tablaPedidosPosibles.setItems(this.pedidosPosiblesData);
		
	}
	
	@FXML
	private void handleCargaOptima(){
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());

		HashMap<Integer, List<Integer>> opt = Optimizador.Optimizar(medio);
		
		int sucursal_opt = opt.keySet().iterator().next();
		List<Integer> pedidos_opt = opt.get(sucursal_opt);
		
		this.pedidosPosiblesData.clear();
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				if (pedidos_opt.contains(pedido.GetId())) {
					this.pedidosCargadosData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen())));
				}
			}
		}
		this.tablaPedidosCargados.setItems(this.pedidosCargadosData);
		
		ViewHelper.ShowMessage("Pedidos optimos cargados correctamente. El camion debe dirigirse a la sucursal " + Sistema.GetInstance().GetSucursal(sucursal_opt).GetDireccion(), AlertType.WARNING);
		
	}
	
	@FXML
	private void handleCargaPedidoIndividual() {
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}

		if (this.sucursal_destino.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un sucursal", AlertType.WARNING);
			return;
		}
		if (this.tablaPedidosPosibles.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un pedido a cargar", AlertType.WARNING);
			return;
		}
		
		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.tablaPedidosPosibles.getSelectionModel().getSelectedItem().getId()));
		
		this.pedidosCargadosData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen())));
		this.tablaPedidosCargados.setItems(this.pedidosCargadosData);
		
		//sacar de la tabla de posibles
		this.pedidosCargadosData.remove(this.tablaPedidosPosibles.getSelectionModel().getSelectedItem());
		this.tablaPedidosPosibles.setItems(this.pedidosCargadosData);
		
		//Notificar
		ViewHelper.ShowMessage("Pedido cargado correctamente", AlertType.WARNING);
		
	}
	
	@FXML
	private void handleGuardarCambios() {
		if (this.pedidosCargadosData.size() > 0) {
			
			MedioDeTransporte medio = Sistema.GetInstance().GetMedio((this.patente_carga.getSelectionModel().getSelectedItem().toString()));
			OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
			
			for (int i = 0; i < this.pedidosCargadosData.size(); i++) {
				Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.pedidosCargadosData.get(i).getId()));
				
				operario.CargarMedio(medio, pedido);				
			}
		}
	}
	

	/*
	 * Para volver al menu principal
	 */
	@FXML
	private void handleVolverMenu() {
		if (ViewHelper.ShowConfirm("Guarde los cambios antes de salir. Esta seguro de querer salir?"))
			this.mainApp.MostrarMenu();
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
