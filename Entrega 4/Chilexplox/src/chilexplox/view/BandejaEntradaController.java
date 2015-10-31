package chilexplox.view;

import java.util.HashMap;
import java.util.Map;

import backend.Mensaje;
import backend.Pedido;
import backend.Sistema;
import backend.Sucursal;
import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class BandejaEntradaController {

	private MainApp mainApp;
	
	@FXML
	private TableView<MensajeTableModel> tabla_mensajes;

	@FXML
    private TableColumn<MensajeTableModel, String> emisarioColumn;
    @FXML
    private TableColumn<MensajeTableModel, String> sucursalOrigenColumn;
    
    private ObservableList<MensajeTableModel> mensajesData;

	@FXML
	private TextArea mensaje;
	
	private Sucursal sucursal;
	
	@FXML
    private void initialize() {
		this.sucursal = Sistema.GetInstance().GetSucursalLoged();
		
		this.emisarioColumn.setCellValueFactory(cellData -> cellData.getValue().emisarioProperty());
        this.sucursalOrigenColumn.setCellValueFactory(cellData -> cellData.getValue().sucursalOrigenProperty());
        
        this.mensajesData = FXCollections.observableArrayList();
		
        this.UpdateMensajes();
        
        tabla_mensajes.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> MostrarMensaje(newValue));
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	private void UpdateMensajes() {
		this.mensajesData.clear();
		Mensaje mensaje = sucursal.GetUltimoMensaje();
		while (mensaje != null) {
			this.mensajesData.add(new MensajeTableModel(mensaje.GetCreador(), mensaje.GetOrigen().GetDireccion()));
			mensaje = sucursal.GetUltimoMensaje();
		}
		this.tabla_mensajes.setItems(this.mensajesData);
	}
	
	private void MostrarMensaje(MensajeTableModel mensaje) {
		
	}
}
