package chilexplox.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.MedioDeTransporte;
import backend.Pedido;
import backend.Sistema;
import backend.Sucursal;
import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public class CamionDetalleController {
	
	private MainApp mainApp;
		
	@FXML
	private Label Patente;
	
	@FXML
	private Label capacidadMax;
	
	@FXML
	private Label capacidadDisponible;
	
	@FXML
	private Label ubicacion;
	
	@FXML
	private Label radioctivo;	
	
	@FXML
	private Label fragil;
	
	@FXML
	private Label refrigerado;
	
	
	//viaje
	@FXML
	private TabPane infoViaje;
	
	@FXML
	private Text camionNoViajando;
	
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
	
	

	@FXML
	private ChoiceBox medios;
	
	private MedioDeTransporte medio;
	
	private boolean hayViaje;
	
	@FXML
    private void initialize() {
		for (Map.Entry<String, MedioDeTransporte> entry : Sistema.GetInstance().GetMedios().entrySet()) {
			MedioDeTransporte m = entry.getValue();
			this.medios.getItems().add(m.GetPatente());
		}
		
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		this.clienteColumn.setCellValueFactory(cellData -> cellData.getValue().clienteProperty());
        this.urgenciaColumn.setCellValueFactory(cellData -> cellData.getValue().urgenciaProperty());
        this.pesoColumn.setCellValueFactory(cellData -> cellData.getValue().pesoProperty());
        this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
        
        this.pedidosData = FXCollections.observableArrayList();
		
		this.hayViaje = false;
		this.infoViaje.setVisible(false);
		this.camionNoViajando.setVisible(true);
    }
	
	@FXML
	private void Consultar() {
		
		if (this.medios.getItems().isEmpty()) {
			ViewHelper.ShowMessage("Selecciona un medio de transporte", AlertType.ERROR);
			return;
		}
		
		this.medio = Sistema.GetInstance().GetMedio(this.medios.getSelectionModel().getSelectedItem().toString());
		
		if (this.medio != null) {
			String[] detalles = Sistema.GetInstance().GetDetalleMedio(medio.GetPatente());
			Patente.setText(detalles[0]);
			capacidadMax.setText(detalles[1]);
			capacidadDisponible.setText(detalles[2]);
			ubicacion.setText(detalles[3]);
			if (detalles[3].equals("En Transito"))
				//this.btnViaje.setDisable(false);
				this.hayViaje = true;
			radioctivo.setText(detalles[4]);
			fragil.setText(detalles[5]);
			refrigerado.setText(detalles[6]);
			
			// Cargar detalles viaje
			if (this.hayViaje) {
				this.DetalleViaje();
			}
		}
		else {
			ViewHelper.ShowMessage("No existe un medio con esa patente.", AlertType.ERROR);
		}
	}
	
	private void DetalleViaje() {
		String[] detalles = Sistema.GetInstance().GetDetalleViaje(medio.GetPatente());
		origen.setText(detalles[0]);
		destino.setText(detalles[1]);
		conductor.setText(detalles[2]);
		vehiculo.setText(detalles[3]);
		peso.setText(detalles[4]);
		
		this.infoViaje.setVisible(true);
		this.camionNoViajando.setVisible(false);

        this.UpdatePedidos();
		
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
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	
	@FXML
    private void handleVolver() {
		this.mainApp.MostrarMenu();
	}
}
