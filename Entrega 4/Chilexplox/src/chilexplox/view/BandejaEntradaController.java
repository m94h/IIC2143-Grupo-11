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
		Map<Integer, Mensaje> mensajes = sucursal.GetMensajes();
		if (mensajes != null) { //Si hay mensajes
			for (Map.Entry<Integer, Mensaje> entry : mensajes.entrySet()) {
				Mensaje mensaje = entry.getValue();
				this.mensajesData.add(new MensajeTableModel(Integer.toString(mensaje.GetId()) , mensaje.GetCreador(), mensaje.GetOrigen().GetDireccion()));
			}
		}
		this.tabla_mensajes.setItems(this.mensajesData);		
	}
	
	private 	void MostrarMensaje(MensajeTableModel mensaje) {
		Mensaje mensaje_b = sucursal.GetMensaje(Integer.parseInt(mensaje.idProperty().getValue().toString()));
		System.out.println(mensaje_b.GetTexto());
		this.mensaje.setText(mensaje_b.GetTexto());
	}
}
