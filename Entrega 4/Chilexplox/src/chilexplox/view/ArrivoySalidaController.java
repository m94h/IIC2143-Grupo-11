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

	@FXML
	private Label sucursal;

	@FXML
	private ChoiceBox patenteArribo;

	@FXML
	private ChoiceBox patenteSalida;

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
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());

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


		// Lista medios listos para salir
		ArrayList<MedioDeTransporte> mediosDisponibles = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
		for (int i = 0; i < mediosDisponibles.size(); i++) {
			MedioDeTransporte medio = mediosDisponibles.get(i);
			this.patenteSalida.getItems().add(medio.GetPatente());
		}

		// Lista medios para arrivar
		ArrayList<MedioDeTransporte> mediosEnTransito = Sistema.GetInstance().GetMediosEnTransito();
		for(int i = 0; i < mediosEnTransito.size(); i++){
			MedioDeTransporte medio = mediosEnTransito.get(i);
			this.patenteArribo.getItems().add(medio.GetPatente());
		}
	}

	private void UpdateEnTransito() {
		//Get medio
		this.enTransitoData.clear();
		ArrayList<MedioDeTransporte> mediosEnTransito = Sistema.GetInstance().GetMediosEnTransito();
		if (mediosEnTransito.size() > 0) { //Si hay medios en transito
			for (int i = 0; i < mediosEnTransito.size(); i++) {
				MedioDeTransporte medio = mediosEnTransito.get(i);
				this.enTransitoData.add(new EnTransitoTableModel(medio.GetPatente(), medio.GetOrigen().GetDireccion(), medio.GetDestino().GetDireccion(), medio.GetEstado().toString()));
			}
		}
		this.tabla_enTransito.setItems(this.enTransitoData);
	}

	private void UpdateDisponibles() {
		//Get medio
		this.disponibleData.clear();
		ArrayList<MedioDeTransporte> mediosDisponibles = Sistema.GetInstance().GetSucursalLoged().GetMediosDisponibles();
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
		if (this.patenteArribo.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patenteArribo.getSelectionModel().getSelectedItem().toString());
		OperarioCamion operario = (OperarioCamion) Sistema.GetInstance().GetUsuarioLoged();
		operario.AvisarArriboMedio(medio, medio.GetDestino());
		ViewHelper.ShowMessage("El camion ha arribado", AlertType.INFORMATION);
	}

	@FXML
	public void handleAvisarSalida() {
		if (this.patenteSalida.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un medio de transporte", AlertType.WARNING);
			return;
		}
		MedioDeTransporte medio = Sistema.GetInstance().GetMedio(this.patenteSalida.getSelectionModel().getSelectedItem().toString());
		OperarioCamion operario = (OperarioCamion) Sistema.GetInstance().GetUsuarioLoged();
		operario.DespacharMedio(medio, medio.GetOrigen());
		ViewHelper.ShowMessage("El camion esta en tr�nsito", AlertType.INFORMATION);
	}

	@FXML
	private void handleVolverMenu() {
		this.mainApp.MostrarMenu();
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}

