package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

//backend
import backend.*;

public class ArrivoySalidaController {

	private MainApp mainApp;

	/*@FXML
	private Label sucursal;*/

	@FXML
	private TableView<EnTransitoTableModel> tabla_enTransito;

	@FXML
    private TableColumn<EnTransitoTableModel, String> patenteColumn;
    @FXML
    private TableColumn<EnTransitoTableModel, String> origenColumn;
    @FXML
    private TableColumn<EnTransitoTableModel, String> destinoColumn;
    @FXML
    private TableColumn<EnTransitoTableModel, String> estadoColumn;

    private ObservableList<EnTransitoTableModel> enTransitoData;

    @FXML
	private TableView<DisponibleTableModel> tabla_disponible;

	@FXML
    private TableColumn<DisponibleTableModel, String> patente2Column;
    @FXML
    private TableColumn<DisponibleTableModel, String> origen2Column;
    @FXML
    private TableColumn<DisponibleTableModel, String> destino2Column;
    @FXML
    private TableColumn<DisponibleTableModel, String> estado2Column;

    private ObservableList<DisponibleTableModel> disponibleData;



	@FXML
	private void initialize() {
		//this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

		// Set las properties para que se actualize la tabla en transito
		this.patenteColumn.setCellValueFactory(cellData -> cellData.getValue().patenteProperty());
		this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
		this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
		this.estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

		// Set las properties para que se actualize la tabla disponibles
		this.patente2Column.setCellValueFactory(cellData -> cellData.getValue().patenteProperty());
		this.origen2Column.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
		this.destino2Column.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
		this.estado2Column.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());


		//Inicializar observablearraylist
		this.enTransitoData = FXCollections.observableArrayList();
		this.disponibleData = FXCollections.observableArrayList();

		this.UpdateEnTransito();
		this.UpdateDisponibles();

	}

	private void UpdateEnTransito() {

		//get medios en transito
		ArrayList<MedioDeTransporte> mediosEnTransito = Sistema.GetInstance().GetMediosEnTransito();

		//Listar medios para arrivar en la tabla
		this.enTransitoData.clear();
		if (mediosEnTransito.size() > 0) { //Si hay medios en transito
			for (int i = 0; i < mediosEnTransito.size(); i++) {
				MedioDeTransporte medio = mediosEnTransito.get(i);
				String destino = "No hay destino";
				if (medio.GetDestino() != null) {
					destino = medio.GetDestino().GetDireccion();
				}
				this.enTransitoData.add(new EnTransitoTableModel(medio.GetPatente(), medio.GetOrigen().GetDireccion(), destino, medio.GetEstado().toString()));
			}
		}
		this.tabla_enTransito.setItems(this.enTransitoData);
	}

	private void UpdateDisponibles() {

		//get medios disponibles
		ArrayList<MedioDeTransporte> mediosDisponibles = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();

		//Lista medios listos para salir en la tabla
		this.disponibleData.clear();
		if(mediosDisponibles.size() > 0){
			for (int i = 0; i < mediosDisponibles.size(); i++) {
				MedioDeTransporte medio = mediosDisponibles.get(i);
				String destino = "No hay destino";
				if (medio.GetDestino() != null) {
					destino = medio.GetDestino().GetDireccion();
				}
				this.disponibleData.add(new DisponibleTableModel(medio.GetPatente(), medio.GetOrigen().GetDireccion(), destino, medio.GetEstado().toString()));
			}
		}
		this.tabla_disponible.setItems(this.disponibleData);
	}


	@FXML
	public void handleAvisarArribo() {
		if (this.tabla_enTransito.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.tabla_enTransito.getSelectionModel().getSelectedItem().getId());
		OperarioCamion operario = (OperarioCamion) Sistema.GetInstance().GetUsuarioLoged();
		if(medio.GetDestino() != null){
			operario.AvisarArriboMedio(medio, medio.GetDestino());
		}

		//actualizar tabla y choicebox
		this.UpdateEnTransito();
		this.UpdateDisponibles();

		//notificar al usuario
		ViewHelper.ShowMessage("El camion ha arribado", AlertType.INFORMATION);
	}

	@FXML
	public void handleAvisarSalida() {
		if (this.tabla_disponible.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.tabla_disponible.getSelectionModel().getSelectedItem().getId());
		OperarioCamion operario = (OperarioCamion) Sistema.GetInstance().GetUsuarioLoged();
		operario.DespacharMedio(medio, medio.GetOrigen());

		//actualizar tabla y choicebox
		this.UpdateDisponibles();
		this.UpdateEnTransito();

		//notificar al usuario
		ViewHelper.ShowMessage("El camion esta en transito", AlertType.INFORMATION);
	}

	@FXML
	private void handleVolverMenu() {
		this.mainApp.MostrarMenu();
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}

