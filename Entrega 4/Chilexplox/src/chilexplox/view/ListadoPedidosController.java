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

public class ListadoPedidosController {

	private MainApp mainApp;

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

    /*
     * Data de los pedidos para la tabla
     */
	private ObservableList<PedidoTableModel> pedidosData;

	@FXML
    private void initialize() {
		// Set las properties para que se actualice la tabla
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        //Mostrar pedidos
        this.UpdatePedidos();

        //Poner los valores de los choice box
		this.estado.getItems().addAll("Transito", "Origen", "Destino");
		this.origen.getItems().addAll(Sistema.GetInstance().GetSucursales().values());
		this.destino.getItems().addAll(Sistema.GetInstance().GetSucursales().values());
		this.urgencia.getItems().addAll("1", "2", "3");

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
				this.pedidosData.add(new PedidoTableModel(Integer.toString(pedido.GetId()), pedido.GetOrigen().GetDireccion(), pedido.GetDestino().GetDireccion(), pedido.GetEstado().toString()));
			}
		}
		this.tabla_pedidos.setItems(this.pedidosData);
	}

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
