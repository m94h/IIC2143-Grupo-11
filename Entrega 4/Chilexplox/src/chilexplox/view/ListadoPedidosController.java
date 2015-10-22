package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

//Fachada del backend
import backend.Sistema;

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

	@FXML
    private void initialize() {
        
    }
	
	@FXML
	private void handleNuevo() {
		this.id.setText( Integer.toString(Sistema.GetInstance().Get_id_pedido()) );
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
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
}
