package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//backend
import backend.*;

public class ListadoPedidosController {
	
	private MainApp mainApp;
	
	@FXML
	private TextField id;
	
	@FXML
	private ChoiceBox estado;
	
	@FXML
	private ChoiceBox origen;
	
	@FXML
	private ChoiceBox destino;
	
	@FXML
	private DatePicker fecha;
	
	@FXML
	private ChoiceBox urgencia;
	
	@FXML
	private TextField rut;
	
	@FXML
	private TextField nombre;
	
	@FXML
	private TextField telefono;
	
	@FXML
	private TextField direccion;
	
	/*
	 * Tabla
	 */
	@FXML
	private TableView<PedidoTableModel> tabla_pedidos;
	
	@FXML
    private TableColumn<PedidoTableModel, String> idColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> origenColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> destinoColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> estadoColumn;
	
	private ObservableList<PedidoTableModel> pedidosData;

	@FXML
    private void initialize() {
		// Set las properties para que se actualice la tabla
		this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        
        this.UpdatePedidos();
    }
	
	private void UpdatePedidos() {
		//Get pedidos
		pedidosData = FXCollections.observableArrayList();
		
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				pedidosData.add(new PedidoTableModel(Integer.toString(pedido.GetId()), "a", "b", "c"));
			}
		}
		tabla_pedidos.setItems(this.pedidosData);
	}
	
	@FXML
	private void handleNuevo() {
		this.id.setText("nuevo");
		this.estado.setDisable(false);
		this.origen.setDisable(false);
		this.destino.setDisable(false);
		this.fecha.setDisable(false);
		this.urgencia.setDisable(false);
		
		this.estado.getItems().clear();
		this.estado.getItems().addAll("Transito", "Origen", "Destino");
		
		this.origen.getItems().clear();
		this.origen.getItems().addAll(Sistema.GetInstance().GetSucursales().values());
		
		this.destino.getItems().clear();
		this.destino.getItems().addAll(Sistema.GetInstance().GetSucursales().values());
		
		this.fecha.setValue(null);
		
		this.estado.getItems().clear();
		this.estado.getItems().addAll("1", "2", "3");
		
		this.rut.setDisable(false);
		this.rut.setText("");
		this.nombre.setText("");
		this.telefono.setText("");
		this.direccion.setText("");
		
	}
	
	@FXML
	public void handleGuardarCambios() {
		
		//chequear cliente
		if (Sistema.GetInstance().GetCliente(this.rut.getText()) != null) {
			//ya existe, se borra para actualizarlo con uno nuevo
			Sistema.GetInstance().BorrarCliente(this.rut.getText());
		}
		//crear cliente con datos nuevos
		Sistema.GetInstance().AgregarCliente(this.rut.getText(), this.nombre.getText(), Integer.parseInt(this.telefono.getText()), this.direccion.getText());
		
		Cliente cliente = Sistema.GetInstance().GetCliente(this.rut.getText());
		
		//Ver si es nuevo el pedido o si se actualiza
		String nuevo_id = Integer.toString(Sistema.GetInstance().Get_id_pedido());
		
		//otros parametros
		Sucursal origen = Sistema.GetInstance().GetSucursal(this.origen.getSelectionModel().getSelectedIndex());
		Sucursal destino = Sistema.GetInstance().GetSucursal(this.destino.getSelectionModel().getSelectedIndex());
		int urgencia = this.urgencia.getSelectionModel().getSelectedIndex();
		Sistema.GetInstance().CrearPedido((OperarioVenta)Sistema.GetInstance().GetUsuarioLoged(), cliente, origen, destino, urgencia);
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
}
