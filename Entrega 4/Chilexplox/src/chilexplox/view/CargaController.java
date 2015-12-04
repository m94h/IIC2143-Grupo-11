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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CargaController {

	private MainApp mainApp;

	@FXML
	private Label sucursal;

	@FXML
	private Label carga;
	
	private int carga_actual;
	
	@FXML
	private Label sucursalDestino;

	@FXML
	private TextField id_pedido;

	@FXML
	private ChoiceBox patente_carga;
	
	@FXML
	private Circle circleFragil;
	
	@FXML
	private Circle circleRadioactivo;
	
	@FXML
	private Circle circlerefrigerado;

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
    @FXML
    private TableColumn<CargaTableModel, String> fragilColumn;
    @FXML
    private TableColumn<CargaTableModel, String> radioactivoColumn;
    @FXML
    private TableColumn<CargaTableModel, String> refrigeradoColumn;

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
    @FXML
    private TableColumn<CargaTableModel, String> fragilColumn2;
    @FXML
    private TableColumn<CargaTableModel, String> radioactivoColumn2;
    @FXML
    private TableColumn<CargaTableModel, String> refrigeradoColumn2;

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
        this.fragilColumn.setCellValueFactory(cellData -> cellData.getValue().fragilProperty());
        this.radioactivoColumn.setCellValueFactory(cellData -> cellData.getValue().radioactivoProperty());
        this.refrigeradoColumn.setCellValueFactory(cellData -> cellData.getValue().refrigeradoProperty());
        this.id_pedidoColumn2.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.destinoColumn2.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.prioridadColumn2.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        this.volumenColumn2.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
        this.fragilColumn2.setCellValueFactory(cellData -> cellData.getValue().fragilProperty());
        this.radioactivoColumn2.setCellValueFactory(cellData -> cellData.getValue().radioactivoProperty());
        this.refrigeradoColumn2.setCellValueFactory(cellData -> cellData.getValue().refrigeradoProperty());
		
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
	private void handleVerPedidos() {
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}

		if (this.sucursal_destino.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un sucursal", AlertType.WARNING);
			return;
		}
		
		this.sucursalDestino.setText(this.sucursal_destino.getSelectionModel().getSelectedItem().toString());
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());
		
		if (medio.EsFragil()) 
			circleFragil.setFill(Color.GREEN);
		else
			circleFragil.setFill(Color.RED);
		if (medio.EsRadioactivo()) 
			circleRadioactivo.setFill(Color.GREEN);
		else
			circleRadioactivo.setFill(Color.RED);
		if (medio.EsRefrigerado()) 
			circlerefrigerado.setFill(Color.GREEN);
		else
			circlerefrigerado.setFill(Color.RED);
		
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		
		//Get pedidos posibles
		this.pedidosPosiblesData.clear();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				String fragil = "No";
				String radioactivo = "No";
				String refrigerado = "No";
			// Muestra solo los pedidos que salen de esa sucursal y que van a la sucursal seleccionada
				if(pedido.GetOrigen().GetDireccion().equals(Sistema.GetInstance().GetSucursalLoged().GetDireccion()) && pedido.GetDestino().GetDireccion().equals(this.sucursal_destino.getSelectionModel().getSelectedItem().toString()) && pedido.GetCargadoEn() == null){ 
					if (pedido.EsFragil())
						fragil = "Si";
					if (pedido.EsRadioactivo())
						radioactivo = "Si";
					if (pedido.EsRefrigerado())
						refrigerado = "Si";
					this.pedidosPosiblesData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen()), fragil, radioactivo, refrigerado));
				}
			}
		}
		this.tablaPedidosPosibles.setItems(this.pedidosPosiblesData);
		
		//Get pedidos ya cargados
		this.pedidosCargadosData.clear();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				String fragil = "No";
				String radioactivo = "No";
				String refrigerado = "No";
				// Muestra solo los pedidos que salen de esa sucursal y que van a la sucursal seleccionada
				if(pedido.GetCargadoEn() == medio){ 
					if (pedido.EsFragil())
						fragil = "Si";
					if (pedido.EsRadioactivo())
						radioactivo = "Si";
					if (pedido.EsRefrigerado())
						refrigerado = "Si";
					this.pedidosCargadosData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen()), fragil, radioactivo, refrigerado));
				}
			}
		}
		this.tablaPedidosCargados.setItems(this.pedidosCargadosData);
		
		//mostrar % carga
		this.carga_actual = medio.GetCapacidadActual();
		float porcentaje = (float) carga_actual / medio.GetCapacidadMax() * 100;
		this.carga.setText(Float.toString(porcentaje) + "%");

	}
	
	@FXML
	private void handleCargaOptima(){
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		
		if (!ViewHelper.ShowConfirm("El optimizador removera todos los pedidos que ya se habia ingresado al camion. Desea continuar?")) {
			return;
		}
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());

		HashMap<Integer, List<Integer>> opt = Optimizador.Optimizar(medio);
		
		int sucursal_opt = opt.keySet().iterator().next();
		List<Integer> pedidos_opt = opt.get(sucursal_opt);
		
		this.sucursalDestino.setText(Sistema.GetInstance().GetSucursal(sucursal_opt).GetDireccion());
		//borrar el contenido de sucursal (choicebox)
		this.sucursal_destino.getSelectionModel().clearSelection();
		
		//borrar el contenido de posibles
		this.pedidosPosiblesData.clear();
		this.tablaPedidosPosibles.setItems(this.pedidosPosiblesData);
		
		this.pedidosCargadosData.clear();
		
		carga_actual = 0;
		
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				String fragil = "No";
				String radioactivo = "No";
				String refrigerado = "No";
				if (pedidos_opt.contains(pedido.GetId())) {
					if (pedido.EsFragil())
						fragil = "Si";
					if (pedido.EsRadioactivo())
						radioactivo = "Si";
					if (pedido.EsRefrigerado())
						refrigerado = "Si";
					this.pedidosCargadosData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen()), fragil, radioactivo, refrigerado));
					carga_actual += pedido.GetVolumen();
				}
			}
		}
		this.tablaPedidosCargados.setItems(this.pedidosCargadosData);
		
		//mostrar % carga
		float porcentaje = (float) carga_actual / medio.GetCapacidadMax() * 100;
		this.carga.setText(Float.toString(porcentaje) + "%");
		
		ViewHelper.ShowMessage("Pedidos optimos cargados correctamente. El camion debe dirigirse a la sucursal " + Sistema.GetInstance().GetSucursal(sucursal_opt).GetDireccion(), AlertType.INFORMATION);
		
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
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());
		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.tablaPedidosPosibles.getSelectionModel().getSelectedItem().getId()));
		//ver si se puede meter
		if (carga_actual + pedido.GetVolumen() > medio.GetCapacidadMax() - carga_actual) {
			ViewHelper.ShowMessage("No hay suficiente espacio para cargar este pedido en el camion.", AlertType.WARNING);
			return;
		}
		
		if (pedido.EsFragil() && !medio.EsFragil() || pedido.EsRadioactivo() && !medio.EsRadioactivo() || pedido.EsRefrigerado() && !medio.EsRefrigerado()) {
			ViewHelper.ShowMessage("Este pedido no puede ser cargado en este camion.", AlertType.WARNING);
			return;
		}
		
		String fragil = "No";
		String radioactivo = "No";
		String refrigerado = "No";
		if (pedido.EsFragil())
			fragil = "Si";
		if (pedido.EsRadioactivo())
			radioactivo = "Si";
		if (pedido.EsRefrigerado())
			refrigerado = "Si";
		
		this.pedidosCargadosData.add(new CargaTableModel(Integer.toString(pedido.GetId()), pedido.GetDestino().GetDireccion(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetVolumen()), fragil, radioactivo, refrigerado));
		this.tablaPedidosCargados.setItems(this.pedidosCargadosData);
		
		//sacar de la tabla de posibles
		this.pedidosPosiblesData.remove(this.tablaPedidosPosibles.getSelectionModel().getSelectedIndex());
		this.tablaPedidosPosibles.setItems(this.pedidosPosiblesData);
		
		//aumentar % carga
		carga_actual += pedido.GetVolumen();
		float porcentaje = (float) carga_actual / medio.GetCapacidadMax() * 100;
		this.carga.setText(Float.toString(porcentaje) + "%");
		
		//Notificar
		ViewHelper.ShowMessage("Pedido cargado correctamente", AlertType.INFORMATION);
		
	}
	
	
	@FXML
	private void handleRemoverPedido() {
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		if (this.tablaPedidosCargados.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un pedido a remover", AlertType.WARNING);
			return;
		}
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patente_carga.getSelectionModel().getSelectedItem().toString());
		Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.tablaPedidosCargados.getSelectionModel().getSelectedItem().getId()));
		
		//Meter en posibles
		this.pedidosPosiblesData.add(this.tablaPedidosCargados.getSelectionModel().getSelectedItem());
		//Sacar de cargados
		this.pedidosCargadosData.remove(this.tablaPedidosCargados.getSelectionModel().getSelectedIndex());
		
		this.tablaPedidosPosibles.setItems(this.pedidosPosiblesData);
		this.tablaPedidosCargados.setItems(this.pedidosCargadosData);
		
		//disminuir % carga
		carga_actual -= pedido.GetVolumen();
		float porcentaje = (float) carga_actual / medio.GetCapacidadMax() * 100;
		this.carga.setText(Float.toString(porcentaje) + "%");
		
		//Notificar
		ViewHelper.ShowMessage("Pedido removido correctamente", AlertType.INFORMATION);
				
	}
	
	@FXML
	private void handleGuardarCambios() {
		if (this.patente_carga.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio((this.patente_carga.getSelectionModel().getSelectedItem().toString()));
		OperarioBodega operario = (OperarioBodega) Sistema.GetInstance().GetUsuarioLoged();
		
		//sacar todos los antiguos
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				if (pedido.GetCargadoEn() == medio) {
					operario.UndoCargarMedio(medio, pedido);
				}
			}
		}
		
		//meter todos los nuevos
		if (this.pedidosCargadosData.size() > 0) {
			for (int i = 0; i < this.pedidosCargadosData.size(); i++) {
				Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.pedidosCargadosData.get(i).getId()));
				operario.CargarMedio(medio, pedido);				
			}
		}
		
		//Notificar
		ViewHelper.ShowMessage("Cambios guardados correctamente.", AlertType.INFORMATION);
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
