package chilexplox.view;

import backend.Sistema;
import chilexplox.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

// Backend
import backend.Pedido;
import backend.MedioDeTransporte;;



public class ViajeController {
	
	private MainApp mainApp;
	
	private String patente;
	
	private MedioDeTransporte medio;
	
	@FXML
	private Label origen;
	
	@FXML
	private Label destino;
	
	@FXML
	private Label conductor;
	
	@FXML
	private Label vehiculo;
	
	@FXML
	private Label peso;
	
	@FXML
	private TableView<PedidoCamionTableModel> tabla_pedidos;

	@FXML
    private TableColumn<PedidoCamionTableModel, String> id_pedidoColumn;
    @FXML
    private TableColumn<PedidoCamionTableModel, String> clienteColumn;
    @FXML
    private TableColumn<PedidoCamionTableModel, String> urgenciaColumn;
    @FXML
    private TableColumn<PedidoCamionTableModel, String> pesoColumn;
    @FXML
    private TableColumn<PedidoCamionTableModel, String> volumenColumn;
	
    /*
     * Data de los pedidos para la tabla
     */
	private ObservableList<PedidoCamionTableModel> pedidosData;
	
	public void setMainApp(MainApp mainApp, String patente) {
        this.mainApp = mainApp;
        this.patente = patente;
        this.MostrarDetalle();
    }
	
	private void MostrarDetalle() {
		this.medio = Sistema.GetInstance().GetMedio(this.patente);
		if (this.medio != null) {
			String[] detalles = Sistema.GetInstance().GetDetalleViaje(this.patente);
			origen.setText(detalles[0]);
			destino.setText(detalles[1]);
			conductor.setText(detalles[2]);
			vehiculo.setText(detalles[3]);
			peso.setText(detalles[4]);
			
			this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
			this.clienteColumn.setCellValueFactory(cellData -> cellData.getValue().clienteProperty());
	        this.urgenciaColumn.setCellValueFactory(cellData -> cellData.getValue().urgenciaProperty());
	        this.pesoColumn.setCellValueFactory(cellData -> cellData.getValue().pesoProperty());
	        this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
	        
	        this.pedidosData = FXCollections.observableArrayList();
			
	        this.UpdatePedidos();
	    }
		else {
			ViewHelper.ShowMessage("No existe un medio con patente: " + patente, AlertType.ERROR);
			this.mainApp.MostrarMenu();
		}
	}
		
	private void UpdatePedidos() {
		this.pedidosData.clear();
		ArrayList<Pedido> pedidos = this.medio.GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Pedido pedido : pedidos) {
				this.pedidosData.add(new PedidoCamionTableModel(Integer.toString(pedido.GetId()) , pedido.GetCliente().GetNombre(), Integer.toString(pedido.GetUrgencia()), Integer.toString(pedido.GetPeso()), Integer.toString(pedido.GetVolumen())));
			}
		}
		if (this.tabla_pedidos != null)
			this.tabla_pedidos.setItems(this.pedidosData);		
	}
	
	@FXML
    private void handleVolver() {
		this.mainApp.mostrarDetalleCamion(this.patente);
	}
}
