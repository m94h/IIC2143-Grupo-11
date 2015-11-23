package chilexplox.view;

import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.EventQueue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

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
	private TextField nombreEncomienda;
	
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
	private CheckBox radioactivo;
	
	@FXML
	private CheckBox refrigerado;
	
	@FXML
	private CheckBox fragil;
	
	
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
    private TableColumn<EncomiendaTableModel, String> nombreEncomiendaColumn;
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
        this.nombreEncomiendaColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        this.pesoColumn.setCellValueFactory(cellData -> cellData.getValue().pesoProperty());
        this.volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty());
        this.precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
        
        //Inicializar observablearraylist
        this.pedidosData = FXCollections.observableArrayList();
        this.encomiendasData = FXCollections.observableArrayList();
        
        //Mostrar pedidos
        
        this.UpdatePedidos();

        //Poner los valores de los choice box
		this.estado.getItems().addAll(Estado.values());
		
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			this.origen.getItems().add(entry.getValue().GetDireccion());
			this.destino.getItems().add(entry.getValue().GetDireccion());
		}
		
		//Agregar urgencias
		this.urgencia.getItems().addAll("1", "2", "3");
		
		//Agregar choices del pago
		this.estadoPago.getItems().addAll("No pagado", "Pagado");
		this.medioPago.getItems().addAll("CREDITO","DEBITO","EFECTIVO","CHEQUE");
		
		//Ordenar tabla por urgencia por defecto
		this.tabla_pedidos.getSortOrder().add(this.urgenciaColumn);
		this.tabla_pedidos.sort();
		
		// Escuchar cambios en la seleccion de la tabla
        tabla_pedidos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> MostrarDetallesPedido(newValue));
    }

	private void UpdatePedidos() {
		//Get pedidos
		this.pedidosData.clear();
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				//Mostrar solo si es de la sucursal correspondiente
				if (pedido.GetOrigen().equals(Sistema.GetInstance().GetSucursalLoged()) || pedido.GetDestino().equals(Sistema.GetInstance().GetSucursalLoged())) {
					this.pedidosData.add(new PedidoTableModel(Integer.toString(pedido.GetId()), pedido.GetOrigen().GetDireccion(), pedido.GetDestino().GetDireccion(), pedido.GetEstado().toString(), Integer.toString(pedido.GetUrgencia())));
				}
			}
		}
		this.tabla_pedidos.setItems(this.pedidosData);
	}

	
	/*
	 * Handle para nuevo pedido
	 */
	@FXML
	private void handleNuevo() {
		
		if (this.id_pedido.getText() != null && ViewHelper.ShowConfirm("Asegurese de guardar sus cambios antes de crear un nuevo pedido. Esta seguro de querer crear uno nuevo?")) {
				
			this.id_pedido.setText("nuevo");
			this.origen.setDisable(false);
			this.destino.setDisable(false);
			//Fecha sigue inactiva, se guarda la fecha actual
			this.fecha.setDisable(true);
			this.fecha.setValue(LocalDate.now());
			this.urgencia.setDisable(false);
	
			// Estado debe partir en origen
			this.estado.setDisable(true);
			this.estado.getSelectionModel().select(Estado.EnSucursalOrigen.ordinal());
			
			//Borrar selecciones antiguas
			this.urgencia.getSelectionModel().clearSelection();
			this.origen.getSelectionModel().clearSelection();
			this.destino.getSelectionModel().clearSelection();
	
			this.rut.setDisable(false);
			this.nombre.setDisable(false);
			this.telefono.setDisable(false);
			this.direccion.setDisable(false);
			this.rut.setText("");
			this.nombre.setText("");
			this.telefono.setText("");
			this.direccion.setText("");
			
			//borrar especiales
			this.refrigerado.setDisable(false);
			this.fragil.setDisable(false);
			this.radioactivo.setDisable(false);
			this.refrigerado.setSelected(false);
			this.fragil.setSelected(false);
			this.radioactivo.setSelected(false);
			
			//limpiar encomiendas
			this.encomiendasData.clear();
			this.tabla_encomiendas.setItems(this.encomiendasData);
			
			this.nombreEncomienda.setDisable(false);
			this.peso.setDisable(false);
			this.volumen.setDisable(false);
			this.nombreEncomienda.setText("");
			this.peso.setText("");
			this.volumen.setText("");
			
			this.medioPago.setDisable(false);
			this.estadoPago.setDisable(false);
			this.montoEncomienda.setText("$");
			this.montoTotal.setText("$");
			this.medioPago.getSelectionModel().clearSelection();
			this.estadoPago.getSelectionModel().clearSelection();
		}
	}
	
	/*
	 * Handle para generar presupuesto
	 */
	@FXML
	private void handleGenerarPresupuesto() {
		if (!this.id_pedido.getText().isEmpty()) {
			if (Auxiliar.isInt(this.peso.getText()) && Auxiliar.isInt(this.volumen.getText())) {
				int valor = Encomienda.Presupuesto(Integer.parseInt(this.peso.getText()), Integer.parseInt(this.volumen.getText()));
				this.montoEncomienda.setText("$ " + Integer.toString(valor));
			} else {
				ViewHelper.ShowMessage("Ingrese un peso y un volumen. Ambos deben ser enteros.", AlertType.ERROR);
				this.montoEncomienda.setText("$");
			}
		}
	}
	
	
	/*
	 * Handle Para buscar cliente con el rut
	 */
	@FXML
	private void handleBuscarCliente() {
		if (!this.id_pedido.getText().isEmpty()) {
			
			if (!this.rut.getText().equals("")) {
				Cliente cliente = Sistema.GetInstance().GetCliente(this.rut.getText());
				if (cliente != null) {
					this.nombre.setText(cliente.GetNombre());
					this.telefono.setText(Integer.toString(cliente.GetTelefono()));
					this.direccion.setText(cliente.GetDireccion());
					ViewHelper.ShowMessage("Cliente encontrado. Sus datos han sido cargados.", AlertType.INFORMATION);
					return;
				} else {
					ViewHelper.ShowMessage("Cliente no encontrado, verifique el rut o ingrese manualmente.", AlertType.INFORMATION);
				}
			} else {
				ViewHelper.ShowMessage("Ingrese un Rut para buscar.", AlertType.WARNING);
				return;
			}
		}
	}
	
	/*
	 * Handle para ingresar nueva encomienda
	 */
	@FXML
	private void handleAgregarEncomienda() {
		//get id del pedido
		String id = this.id_pedido.getText();
		
		//Si hay un pedido agregar
		if (!id.equals("")) {
			if (Auxiliar.isInt(this.peso.getText()) && Auxiliar.isInt(this.volumen.getText()) && !this.nombreEncomienda.getText().isEmpty()) {
				
				if (this.id_pedido.getText().equals("nuevo")) {
					//Se debe grabar la orden antes, para poder generar una id
					ViewHelper.ShowMessage("Grabe la orden antes de agregar encomiendas", AlertType.WARNING);
					return;
				}
							
				Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(id));
				int id_encomienda = Sistema.GetInstance().CrearEncomienda((OperarioVenta) Sistema.GetInstance().GetUsuarioLoged(), pedido, this.nombreEncomienda.getText(), Integer.parseInt(this.peso.getText()), Integer.parseInt(this.volumen.getText()));
				Encomienda encomienda = Sistema.GetInstance().GetEncomienda(id_encomienda);
				pedido.AgregarEncomienda(encomienda);
				
				this.encomiendasData.add(new EncomiendaTableModel(Integer.toString(encomienda.GetId()), encomienda.GetNombre(), Integer.toString(encomienda.GetPeso()), Integer.toString(encomienda.GetVolumen()), Integer.toString(encomienda.GenerarPresupuesto())));
				this.tabla_encomiendas.setItems(this.encomiendasData);
				
				// Actualizar monto total
				this.montoTotal.setText("$ " + Integer.toString(pedido.CalcularMonto()));
				
				//Borrar los input
				this.nombreEncomienda.setText("");
				this.peso.setText("");
				this.volumen.setText("");
				this.montoEncomienda.setText("$ ");

			} else {
				ViewHelper.ShowMessage("Debe ingresar un nombre, peso y volumen. El peso y volumen deben ser enteros", AlertType.ERROR);
			}
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
			this.nombreEncomienda.setDisable(false);
			this.peso.setDisable(false);
			this.volumen.setDisable(false);
			this.medioPago.setDisable(false);
			this.estadoPago.setDisable(false);
			//borrar especiales
			this.refrigerado.setDisable(false);
			this.fragil.setDisable(false);
			this.radioactivo.setDisable(false);
			
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
			this.encomiendasData.clear();
			Map<Integer, Encomienda> encomiendas = pedido_b.GetEncomiendas();
			if (encomiendas != null) {
				for (Map.Entry<Integer, Encomienda> entry : encomiendas.entrySet()) {
					Encomienda encomienda = entry.getValue();
					this.encomiendasData.add(new EncomiendaTableModel(Integer.toString(encomienda.GetId()), encomienda.GetNombre(), Integer.toString(encomienda.GetPeso()), Integer.toString(encomienda.GetVolumen()), Integer.toString(encomienda.GenerarPresupuesto())));
				}
			}
			this.tabla_encomiendas.setItems(this.encomiendasData);
			
			// pagos (ordenes)
			// actualizar monto total
			this.montoTotal.setText("$" + Integer.toString(pedido_b.CalcularMonto()));
			
			OrdenCompra orden = pedido_b.GetOrden();
			if (orden != null) {
				if (orden.GetMedio() != null) {
					this.medioPago.getSelectionModel().select(orden.GetMedio().toString());
				} else {
					this.medioPago.getSelectionModel().clearSelection();
				}
				if (orden.GetEstado()){
					this.estadoPago.getSelectionModel().select(1);
				} else {
					this.estadoPago.getSelectionModel().select(0);
				}
			}
			
			// especiales
			if (pedido_b.EsFragil())
				this.fragil.setSelected(true);
			else
				this.fragil.setSelected(false);
			if (pedido_b.EsRefrigerado())
				this.refrigerado.setSelected(true);
			else
				this.refrigerado.setSelected(false);
			if (pedido_b.EsRadioactivo())
				this.radioactivo.setSelected(true);
			else
				this.radioactivo.setSelected(false);
			
		}
	}


	@FXML
	public void handleGuardarCambios() {
		
		//Verificar que haya ingresado datos
		if (this.id_pedido.getText().isEmpty()) {
			ViewHelper.ShowMessage("No se esta editando o agregando ningun pedido que se pueda guardar.", AlertType.ERROR);
			return;
		}
		if (this.estado.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un estado.", AlertType.ERROR);
			return;
		}
		if (this.origen.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione una sucursal de origen.", AlertType.ERROR);
			return;
		}
		if (this.destino.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione una sucursal de destino.", AlertType.ERROR);
			return;
		}
		//origen distinto de destino
		if (this.destino.getSelectionModel().getSelectedItem().equals(this.origen.getSelectionModel().getSelectedItem())) {
			ViewHelper.ShowMessage("La Sucursal de origen debe ser distinta a la sucursal de destino.", AlertType.ERROR);
			return;
		}
		if (this.urgencia.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione una urgencia.", AlertType.ERROR);
			return;
		}
		if (this.rut.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingrese un rut de cliente.", AlertType.ERROR);
			return;
		}
		if (this.nombre.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingrese un nombre de Cliente.", AlertType.ERROR);
			return;
		}
		if (this.telefono.getText().isEmpty()) {
			ViewHelper.ShowMessage("Ingrese un telefono de Cliente.", AlertType.ERROR);
			return;
		}
		if (!Auxiliar.isInt(this.telefono.getText())){
			ViewHelper.ShowMessage("Ingrese una telefono de Cliente valido (solo numeros).", AlertType.ERROR);
			return;
		}
		if (this.direccion.getText().isEmpty()){
			ViewHelper.ShowMessage("Ingrese una direccion de Cliente.", AlertType.ERROR);
			return;
		}
		if (this.estadoPago.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione un estado del pago.", AlertType.ERROR);
			return;
		}
		if (this.estadoPago.getSelectionModel().getSelectedItem().toString() == "Pagado" && this.medioPago.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("La orden no puede estar pagada sin un medio de pago seleccionado.", AlertType.ERROR);
			return;
		}
		
		//chequear cliente
		if (Sistema.GetInstance().GetCliente(this.rut.getText()) == null) {
			//ya existe, se borra para actualizarlo con uno nuevo
			Sistema.GetInstance().BorrarCliente(this.rut.getText());
		}
		//crear cliente con datos nuevos
		Sistema.GetInstance().AgregarCliente(this.rut.getText(), this.nombre.getText(), Integer.parseInt(this.telefono.getText()), this.direccion.getText());

		Cliente cliente = Sistema.GetInstance().GetCliente(this.rut.getText());

		//otros parametros
		Sucursal origen = (new ArrayList<Sucursal>(Sistema.GetInstance().GetSucursales().values())).get(this.origen.getSelectionModel().getSelectedIndex());
		Sucursal destino = (new ArrayList<Sucursal>(Sistema.GetInstance().GetSucursales().values())).get(this.destino.getSelectionModel().getSelectedIndex());
		int urgencia = Integer.parseInt(this.urgencia.getSelectionModel().getSelectedItem().toString());
		Estado estado = Estado.valueOf(this.estado.getValue().toString());
		
		//calcular
		int especialPedido = 0;
		if (this.radioactivo.isSelected())
			especialPedido += 1;
		if (this.fragil.isSelected())
			especialPedido += 2;
		if (this.refrigerado.isSelected())
			especialPedido += 4;
				
		if (this.id_pedido.getText().equals("nuevo")) {
			
			int id_nuevo = Sistema.GetInstance().CrearPedido((OperarioVenta)Sistema.GetInstance().GetUsuarioLoged(), cliente, origen, destino, urgencia, estado, fecha.getValue(), Integer.toString(especialPedido));
			Pedido pedido = Sistema.GetInstance().GetPedido(id_nuevo);
			PedidoTableModel pedidoModel = new PedidoTableModel(Integer.toString(pedido.GetId()), pedido.GetOrigen().GetDireccion(), pedido.GetDestino().GetDireccion(), pedido.GetEstado().toString(), Integer.toString(pedido.GetUrgencia()));
			this.pedidosData.add(pedidoModel);
			this.tabla_pedidos.setItems(this.pedidosData);
			this.tabla_pedidos.sort(); //sort
			this.tabla_pedidos.getSelectionModel().select(pedidoModel); //seleccionar para que se actualice
			
			OrdenCompra orden = pedido.GetOrden();
			if (orden == null) {
				pedido.GenerarOrden();
				orden = pedido.GetOrden();
			}
			
			if (this.estado.getSelectionModel().getSelectedItem().equals("Pagado")) {
				orden.Pagar(MedioPago.valueOf(this.medioPago.getSelectionModel().getSelectedItem().toString()));
			}
			
			ViewHelper.ShowMessage("Nuevo Pedido guardado correctamente", AlertType.INFORMATION);
			
		} else {
			
			Pedido pedido = Sistema.GetInstance().GetPedido(Integer.parseInt(this.id_pedido.getText()));
			pedido.Actualizar(cliente, origen, destino, urgencia, estado, fecha.getValue(), Integer.toString(especialPedido));
			
			OrdenCompra orden = pedido.GetOrden();
			if (orden == null) {
				pedido.GenerarOrden();
				orden = pedido.GetOrden();
			}
			
			if (this.estado.getSelectionModel().getSelectedItem().equals("Pagado")) {
				orden.Pagar(MedioPago.valueOf(this.medioPago.getSelectionModel().getSelectedItem().toString()));
			}
			
			ViewHelper.ShowMessage("Pedido actualizado correctamente", AlertType.INFORMATION);
		}
	}
	
	/*
	 * Para volver al menu principal
	 */
	@FXML
	private void handleVolverMenu() {
		if (ViewHelper.ShowConfirm("Guarde los cambios antes de salir. Esta seguro de querer salir?"))
			this.mainApp.MostrarMenu();
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
