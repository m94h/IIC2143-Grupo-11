package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

public class ListadoPedidosController {

	private MainApp mainApp;
	
	@FXML
	private Label sucursal;

	@FXML
	private TextField id_pedido;

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
	private TextField peso;
	
	@FXML
	private TextField volumen;
	
	@FXML
	private Label montoEncomienda;
	
	@FXML
	private Label montoTotal;
	
	@FXML
	private ChoiceBox medioPago;
	
	@FXML
	private ChoiceBox estadoPago;
	
	@FXML
	private TextField montoRecibido;
	

	/*
	 * Tabla pedidos y sus columnas
	 */
	@FXML
	private TableView<PedidoTableModel> tabla_pedidos;

	@FXML
    private TableColumn<PedidoTableModel, String> id_pedidoColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> origenColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> destinoColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> estadoColumn;
    @FXML
    private TableColumn<PedidoTableModel, String> urgenciaColumn;
	
    /*
     * Data de los pedidos para la tabla
     */
	private ObservableList<PedidoTableModel> pedidosData;
	
	/*
	 * Tabla Encomiendas y sus columnas
	 */
	@FXML
	private TableView<EncomiendaTableModel> tabla_encomiendas;
	
	@FXML
    private TableColumn<EncomiendaTableModel, String> id_encomiendaColumn;
    @FXML
    private TableColumn<EncomiendaTableModel, String> pesoColumn;
    @FXML
    private TableColumn<EncomiendaTableModel, String> volumenColumn;
    @FXML
    private TableColumn<EncomiendaTableModel, String> precioColumn;
    
    /*
     * Data de las encomiendas para la tabla
     */
	private ObservableList<EncomiendaTableModel> encomiendasData;
    
    
	@FXML
    private void initialize() {
		
		// Poner la sucursal actual
		this.sucursal.setText(Sistema.GetInstance().GetSucursalLoged().GetDireccion());
		
		// Set las properties para que se actualice la tabla pedidos
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        this.urgenciaColumn.setCellValueFactory(cellData -> cellData.getValue().urgenciaProperty());
        
        //Set properties de la tabla encomiendas
        this.id_encomiendaColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.pesoColumn.setCellValueFactory(cellData -> cellData.getValue().pesoProperty());
        this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
        this.precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
        
        //Mostrar pedidos
        this.UpdatePedidos();

        //Poner los valores de los choice box
		this.estado.getItems().addAll("Transito", "Origen", "Destino");
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			this.origen.getItems().add(entry.getValue().GetDireccion());
			this.destino.getItems().add(entry.getValue().GetDireccion());
		}
		
		//Agregar urgencias
		this.urgencia.getItems().addAll("1", "2", "3");
		
		//Ordenar tabla por urgencia por defecto
		this.tabla_pedidos.getSortOrder().add(this.urgenciaColumn);
		this.tabla_pedidos.sort();
		
		// Escuchar cambios en la seleccion de la tabla
        tabla_pedidos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> MostrarDetallesPedido(newValue));
    }

	private void UpdatePedidos() {
		//Get pedidos
		this.pedidosData = FXCollections.observableArrayList();

		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				this.pedidosData.add(new PedidoTableModel(Integer.toString(pedido.GetId()), pedido.GetOrigen().GetDireccion(), pedido.GetDestino().GetDireccion(), pedido.GetEstado().toString(), Integer.toString(pedido.GetUrgencia())));
			}
		}
		this.tabla_pedidos.setItems(this.pedidosData);
	}

	
	/*
	 * Handle para nuevo pedido
	 */
	@FXML
	private void handleNuevo() {
		this.id_pedido.setText("nuevo");
		this.estado.setDisable(false);
		this.origen.setDisable(false);
		this.destino.setDisable(false);
		//Fecha sigue inactiva, se guarda la fecha actual
		this.fecha.setValue(LocalDate.now());
		this.urgencia.setDisable(false);

		this.estado.getSelectionModel().clearSelection();
		this.origen.getSelectionModel().clearSelection();
		this.destino.getSelectionModel().clearSelection();

		this.rut.setDisable(false);
		this.rut.setText("");
		this.nombre.setText("");
		this.telefono.setText("");
		this.direccion.setText("");
		
		this.peso.setDisable(false);
		this.volumen.setDisable(false);
		this.peso.setText("");
		this.volumen.setText("");
		
		this.medioPago.setDisable(false);
		this.estado.setDisable(false);
		this.montoRecibido.setDisable(false);
		this.montoEncomienda.setText("$");
		this.montoTotal.setText("$");
		this.medioPago.getSelectionModel().clearSelection();
		this.estadoPago.getSelectionModel().clearSelection();
		this.montoRecibido.setText("");
		
	}
	
	/*
	 * Handle para generar presupuesto
	 */
	@FXML
	private void handleGenerarPresupuesto() {
		if (this.id_pedido.getText() != null) {
			if (Auxiliar.isInt(this.peso.getText()) && Auxiliar.isInt(this.volumen.getText())) {
				int valor = Encomienda.Presupuesto(Integer.parseInt(this.peso.getText()), Integer.parseInt(this.volumen.getText()));
				this.montoEncomienda.setText("$" + Integer.toString(valor));
			}
		}
	}
	
	/*
	 * Handle para ingresar nueva encomienda
	 */
	@FXML
	private void handleAgregarEncomienda() {
		//get id del pedido
		int id = Integer.parseInt(this.id_pedido.getText());
		
		//Si hay un pedido agregar
		if (this.id_pedido.getText() != null) {
			Pedido pedido = Sistema.GetInstance().GetPedido(id);
			int id_encomienda = Sistema.GetInstance().CrearEncomienda((OperarioVenta) Sistema.GetInstance().GetUsuarioLoged(), pedido, Integer.parseInt(this.peso.getText()), Integer.parseInt(this.volumen.getText()));
			Encomienda encomienda = Sistema.GetInstance().GetEncomienda(id_encomienda);
			pedido.AgregarEncomienda(encomienda);
			
			this.encomiendasData.add(new EncomiendaTableModel(Integer.toString(encomienda.GetId()), Integer.toString(encomienda.GetPeso()), Integer.toString(encomienda.GetVolumen()), Integer.toString(encomienda.GenerarPresupuesto())));
			this.tabla_encomiendas.setItems(this.encomiendasData);
			
			//Borrar los input
			this.peso.setText("");
			this.volumen.setText("");
		}

	}

	/*
	 * Para mostrar detalles del pedido al seleccionar uno
	 */
	private void MostrarDetallesPedido(PedidoTableModel pedido){
		if (pedido != null) {
			//Desbloquear componentes formulario
			this.estado.setDisable(false);
			this.origen.setDisable(false);
			this.destino.setDisable(false);
			this.fecha.setDisable(false);
			this.urgencia.setDisable(false);
			this.rut.setDisable(false);
			this.nombre.setDisable(false);
			this.telefono.setDisable(false);
			this.direccion.setDisable(false);
			this.peso.setDisable(false);
			this.volumen.setDisable(false);
			
			//Get los datos del pedido backend
			Pedido pedido_b = Sistema.GetInstance().GetPedido(Integer.parseInt(pedido.getId()));

			//agregar datos
			this.id_pedido.setText(Integer.toString(pedido_b.GetId()));
			//Get el index dado un value de enum
			//http://stackoverflow.com/questions/15436721/get-index-of-enum-from-sting
			this.estado.getSelectionModel().select(Arrays.asList(Estado.values()).indexOf(pedido_b.estado));

			this.destino.getSelectionModel().select(pedido_b.GetDestino().GetDireccion());
			this.origen.getSelectionModel().select(pedido_b.GetOrigen().GetDireccion());
			this.urgencia.getSelectionModel().select(pedido_b.GetUrgencia() - 1);
			this.fecha.setValue(pedido_b.GetFecha());
			
			//datos cliente
			Cliente cliente = pedido_b.GetCliente();
			this.rut.setText(cliente.GetRut());
			this.nombre.setText(cliente.GetNombre());
			this.telefono.setText(Integer.toString(cliente.GetTelefono()));
			this.direccion.setText(cliente.GetDireccion());
			
			//encomienda
			this.encomiendasData = FXCollections.observableArrayList();
			Map<Integer, Encomienda> encomiendas = pedido_b.GetEncomiendas();
			if (encomiendas != null) {
				for (Map.Entry<Integer, Encomienda> entry : encomiendas.entrySet()) {
					Encomienda encomienda = entry.getValue();
					this.encomiendasData.add(new EncomiendaTableModel(Integer.toString(encomienda.GetId()), Integer.toString(encomienda.GetPeso()), Integer.toString(encomienda.GetVolumen()), Integer.toString(encomienda.GenerarPresupuesto())));
				}
			}
			this.tabla_encomiendas.setItems(this.encomiendasData);
			
			
		}
	}

	@FXML
	public void handleGuardarCambios() {

		//chequear cliente
		if (Sistema.GetInstance().GetCliente(this.rut.getText()) != null) {
			//ya existe, se borra para actualizarlo con uno nuevo
			Sistema.GetInstance().BorrarCliente(this.rut.getText());
		}
		//crear cliente con datos nuevos
		Sistema.GetInstance().AgregarCliente(this.rut.getText(), this.nombre.getText(), Integer.parseInt(this.telefono.getText()), this.direccion.getText());

		Cliente cliente = Sistema.GetInstance().GetCliente(this.rut.getText());

		//Ver si es nuevo el pedido o si se actualiza
		String nuevo_id = Integer.toString(Sistema.GetInstance().Get_id_pedido());

		//otros parametros
		Sucursal origen = Sistema.GetInstance().GetSucursal(this.origen.getSelectionModel().getSelectedIndex());
		Sucursal destino = Sistema.GetInstance().GetSucursal(this.destino.getSelectionModel().getSelectedIndex());
		int urgencia = this.urgencia.getSelectionModel().getSelectedIndex();
		Sistema.GetInstance().CrearPedido((OperarioVenta)Sistema.GetInstance().GetUsuarioLoged(), cliente, origen, destino, urgencia);
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
