package backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pedido {
	private int id;
	private Cliente cliente;
	private Sucursal origen;
  	private Sucursal destino;
  	public Estado estado;
    private int urgencia;
    private LocalDate fecha;

  	private OrdenCompra orden_compra;
  	private Map<Integer, Encomienda> encomiendas;

  	public Pedido(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha) {
    	this.id = Sistema.GetInstance().Get_id_pedido();
      Initialize(cliente, origen, destino, urgencia, estado, fecha);
  	}

    public Pedido(int id, Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha) {
      this.id = id;
      Initialize(cliente, origen, destino, urgencia, estado, fecha);
    }

    private void Initialize(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha) {
      this.cliente = cliente;
      this.origen = origen;
      this.destino = destino;
      this.urgencia = urgencia;
      this.estado = estado;
      this.encomiendas = new HashMap<Integer, Encomienda>();
      this.fecha = fecha;
    }
    
    /*
     * Actualizar pedido
     */
    public void Actualizar(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha) {
        this.cliente = cliente;
        this.origen = origen;
        this.destino = destino;
        this.urgencia = urgencia;
        this.estado = estado;
        this.encomiendas = new HashMap<Integer, Encomienda>();
        this.fecha = fecha;
    }

    /*
     * Getters
     */
  	public int GetId() {
  		return id;
  	}
  	
  	public Cliente GetCliente() {
  		return cliente;
  	}
  	
  	public Sucursal GetOrigen() {
  		return origen;
  	}
  	
  	public Sucursal GetDestino() {
  		return destino;
  	}
  	
    public int GetUrgencia () {
    	return urgencia;
    }
    
    public Estado GetEstado () {
    	return this.estado;
    }
    
    public LocalDate GetFecha() {
    	return this.fecha;
    }
    
    public Map<Integer, Encomienda> GetEncomiendas() {
    	return this.encomiendas;
    }
    
    public Encomienda GetEncomienda(int id) {
    	return this.encomiendas.get(id);
    }

  	public void AgregarEncomienda (Encomienda encomienda) {
    	this.encomiendas.put(encomienda.GetId(), encomienda);
  	}

  	public int CalcularMonto() {
  		int monto = 0;
  		for (Map.Entry<Integer, Encomienda> entry : this.encomiendas.entrySet()) {
  			monto += entry.getValue().GenerarPresupuesto();
  		}
  		return monto;
  	}
  	
    public OrdenCompra GetOrden() {
      return this.orden_compra;
    }

 	public void GenerarOrden() {
    	this.orden_compra = new OrdenCompra(CalcularMonto());
  	}
 	
 	public void AgregarOrden(OrdenCompra orden) {
 		this.orden_compra = orden;
 	}
}
