package chilexplox.view;

import java.time.LocalDate;
import java.util.Map;

import backend.*;
import backend.Error;
import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegistroErroresController {

	private MainApp mainApp;
	
	@FXML
	private DatePicker fechaDesde;
	
	@FXML
	private DatePicker fechaHasta;
	
	@FXML
	private TextField agregar_idEmpleado;
	
	@FXML
	private TextArea agregar_mensaje;
	
	@FXML
	private TextArea mensajeCompleto;
	
	/*
	 * Tabla pedidos y sus columnas
	 */
	@FXML
	private TableView<ErroresTableModel> tablaErrores;

	@FXML
    private TableColumn<ErroresTableModel, String> id_errorColumn;
    @FXML
    private TableColumn<ErroresTableModel, String> fechaColumn;
    @FXML
    private TableColumn<ErroresTableModel, String> empleadoColumn;
    @FXML
    private TableColumn<ErroresTableModel, String> mensajeColumn;
	
	/*
     * Data de los pedidos para la tabla
     */
	private ObservableList<ErroresTableModel> erroresData;
	
	
	@FXML
    private void initialize() {
		// Set las properties para que se actualice la tabla pedidos
		this.id_errorColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        this.empleadoColumn.setCellValueFactory(cellData -> cellData.getValue().empleadoProperty());
        this.mensajeColumn.setCellValueFactory(cellData -> cellData.getValue().mensajeProperty());
        
        //Inicializar observablearraylist
        this.erroresData = FXCollections.observableArrayList();
        
        // Escuchar cambios en la seleccion de la tabla
        tablaErrores.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> MostrarDetallesError());
        
        // Poner fechas desde hace 1 semana hoy
        this.fechaDesde.setValue(LocalDate.now().minusWeeks(1));
        this.fechaHasta.setValue(LocalDate.now());
        
        this.CargarErrores(false);
    }
	
	
	private void CargarErrores(boolean MostrarMensaje) {
		//Cargar errores
		
		if (fechaDesde.getValue() == null) {
			return;
		}
		
		if (fechaHasta.getValue() == null) {
			return;
		}
		
		if (fechaDesde.getValue().isAfter(fechaHasta.getValue())) {
			return;
		}
		
		this.erroresData.clear();
		
		Map<Integer, Error> errores = Sistema.GetInstance().GetErrores();
		
		if (errores != null) { //Si hay errores
			for (Map.Entry<Integer, Error> entry : errores.entrySet()) {
				Error error = entry.getValue();
				//Mostrar solo si es del periodo correspondiente
				if ( ( error.GetFecha().isAfter(fechaDesde.getValue()) || error.GetFecha().isEqual(fechaDesde.getValue()) ) && ( error.GetFecha().isBefore(fechaHasta.getValue()) || error.GetFecha().isEqual(fechaHasta.getValue()) ) ) {
					this.erroresData.add(new ErroresTableModel(Integer.toString(error.GetID()), error.GetFecha().toString(), error.GetEmpleado().GetNombre(), error.GetMensaje()));
				}
			}
		}
		this.tablaErrores.setItems(this.erroresData);
		
		// Limpiar la info mensaje completo
		this.mensajeCompleto.clear();
		
		if (MostrarMensaje)
			ViewHelper.ShowMessage("Se ha cargado la informacion de los errores.", AlertType.INFORMATION);
	}
	
	private void MostrarDetallesError() {
		if (!this.tablaErrores.getSelectionModel().isEmpty()) {
			this.mensajeCompleto.setText(Sistema.GetInstance().GetError(Integer.parseInt(this.tablaErrores.getSelectionModel().getSelectedItem().getId())).GetMensaje());

		}
		
	}
	
	@FXML
	private void handleConsultar() {
		
		//Validaciones de fechas
		if (fechaDesde.getValue() == null) {
			ViewHelper.ShowMessage("Seleccione una fecha inicial de periodo.", AlertType.ERROR);
			return;
		}
		
		if (fechaHasta.getValue() == null) {
			ViewHelper.ShowMessage("Seleccione una fecha de termino de periodo.", AlertType.ERROR);
			return;
		}
		
		if (fechaDesde.getValue().isAfter(fechaHasta.getValue())) {
			ViewHelper.ShowMessage("La fecha inicial no puede ser posterior a la final.", AlertType.ERROR);
			return;
		}
		
		this.CargarErrores(true);
		
	}
	
	
	@FXML
	private void AgregarError() {
		// Check si se ingreso empleado
		if (this.agregar_idEmpleado.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingrese un RUT de empleado", AlertType.WARNING);
			return;
		}
		
		// Check si se ingreso mensaje
		if (this.agregar_mensaje.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingrese un mensaje", AlertType.WARNING);
			return;
		}
				
		// Check si emplaedo existe
		Empleado empleado = Sistema.GetInstance().GetEmpleado(this.agregar_idEmpleado.getText());
		if (empleado == null) {
			ViewHelper.ShowMessage("El empleado ingresado no existe", AlertType.WARNING);
			return;
		}
		
		// Agregar error
		Sistema.GetInstance().AgregarError(empleado, this.agregar_mensaje.getText());
		
		
		// Informar
		ViewHelper.ShowMessage("Error agregado correctamente", AlertType.INFORMATION);
		
		// Limpiar form
		this.agregar_idEmpleado.clear();
		this.agregar_mensaje.clear();
		
		this.CargarErrores(false);
		
	}
	
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
    private void handleVolver() {
		this.mainApp.MostrarMenu();
	}
}
