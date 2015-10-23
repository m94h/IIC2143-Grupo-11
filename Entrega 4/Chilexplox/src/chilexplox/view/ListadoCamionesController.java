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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//backend
import backend.*;

public class ListadoCamionesController {

	private MainApp mainApp;

	@FXML
	private TextField patente;

	@FXML
	private ChoiceBox estado;

	@FXML
	private TextField capacidad;

	@FXML
	private ChoiceBox sucursalOrigen;

	@FXML
	private ChoiceBox sucursalDestino;

	@FXML
	private TextField modelo;

	@FXML
	private TextField marca;

	@FXML
	private TextField kms;

	/*
	 * Tabla camiones esperando y sus columnas
	 */
	@FXML
	private TableView<EsperandoTableModel> tabla_esperando;

	@FXML
    private TableColumn<EsperandoTableModel, String> patenteColumn;
    @FXML
    private TableColumn<EsperandoTableModel, String> estadoColumn;
    @FXML
    private TableColumn<EsperandoTableModel, String> capacidadColumn;
    @FXML
    private TableColumn<EsperandoTableModel, String> origenColumn;

    /*
     * Data de los camiones para la tabla
     */
	private ObservableList<EsperandoTableModel> camionesData;

	/*
	 * Tabla camiones esperando y sus columnas
	 */
	@FXML
	private TableView<ListoTableModel> tabla_listo;

	@FXML
    private TableColumn<ListoTableModel, String> patente2Column;
    @FXML
    private TableColumn<ListoTableModel, String> estado2Column;
    @FXML
    private TableColumn<ListoTableModel, String> capacidad2Column;
    @FXML
    private TableColumn<ListoTableModel, String> destinoColumn;


	@FXML
	private void initialize() {
		this.patenteColumn.setCellValueFactory(cellData -> cellData.getValue().patenteEProperty());
        this.estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoEProperty());
        this.capacidadColumn.setCellValueFactory(cellData -> cellData.getValue().capacidadEProperty());
        this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());

        this.patente2Column.setCellValueFactory(cellData -> cellData.getValue().patenteLProperty());
        this.estado2Column.setCellValueFactory(cellData -> cellData.getValue().estadoLProperty());
        this.capacidad2Column.setCellValueFactory(cellData -> cellData.getValue().capacidadLProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	/*
	 * Para mostrar detalles del camion al seleccionar uno
	 */
	private void MostrarDetallesCamionEsperando(EsperandoTableModel camion){
		if (camion != null) {
			//Desbloquear componentes formulario
			this.patente.setDisable(false);
			this.estado.setDisable(false);
			this.capacidad.setDisable(false);
			this.sucursalOrigen.setDisable(false);
			this.sucursalDestino.setDisable(false);
			this.modelo.setDisable(false);
			this.marca.setDisable(false);
			this.kms.setDisable(false);

			//Get los datos del camion backend
			Camion camion_b = Sistema.GetInstance().GetCamion(camion.getPatente());

			//agregar datos
			this.patente.setText(camion_b.GetPatente());
			//Get el index dado un value de enum
			//http://stackoverflow.com/questions/15436721/get-index-of-enum-from-sting
			this.estado.getSelectionModel().select(Arrays.asList(Estado.values()).indexOf(camion_b.GetEstado()));

			this.capacidad.setText(Integer.toString(camion_b.GetCapacidadMax()));
			this.sucursalOrigen.getSelectionModel().select(camion_b.GetOrigen().GetDireccion());
			this.sucursalDestino.getSelectionModel().select(camion_b.GetDestino().GetDireccion());
			this.modelo.setText(camion_b.GetModelo());
			this.marca.setText(camion_b.GetMarca());
			this.kms.setText(Integer.toString(camion_b.GetKm()));
		}
	}
}

