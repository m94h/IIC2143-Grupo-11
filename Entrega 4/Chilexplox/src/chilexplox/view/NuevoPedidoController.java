package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//backend
import backend.*;

public class NuevoPedidoController {

	private MainApp mainApp;

	@FXML
	private TextField rut;

	@FXML
	private ChoiceBox origen;

	@FXML
	private ChoiceBox destino;

	@FXML
	private ChoiceBox urgencia;

	@FXML
	private TextField peso;

	@FXML
	private TextField volumen;

	@FXML
	private TextField monto;

	@FXML
	private ChoiceBox medioPago;

	@FXML
	private TextField efectivoRecibido;

	/*
	 * Tabla encomiendas y sus columnas
	 */


}
