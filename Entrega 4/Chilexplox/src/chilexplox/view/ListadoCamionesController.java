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

public class ListadoCamionesController {

	private MainApp mainApp;

	@FXML
	private TextField patente;

	@FXML
	private TextField estado;

	@FXML
	private TextField capacidad;

	@FXML
	private TextField sucursalOrigen;

	@FXML
	private TextField sucursalDestino;

	@FXML
	private TextField Modelo;

	@FXML
	private TextField Marca;

	@FXML
	private TextField Kms;

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
		// Get camiones listos y esperando
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
