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
	private int prioridad;
	private LocalDate fechaCreacion;
	private LocalDate fechaEnvio;
	private LocalDate fechaLlegada;
	private MedioDeTransporte cargadoEn;
	private Empleado conductor;
	private Empleado creadoPor;
	private Empleado cargadoPor;
	private int pesoTotal;
	private int volumenTotal;

  	private OrdenCompra orden_compra;
  	private Map<Integer, Encomienda> encomiendas;

  	public Pedido(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha) {
    	this.id = Sistema.GetInstance().Get_id_pedido();
    	this.pesoTotal = 0;
    	this.volumenTotal = 0;
    	this.creadoPor = Sistema.GetInstance().GetUsuarioLoged();
      Initialize(cliente, origen, destino, urgencia, estado, fecha);
  	}

    public Pedido(int id, Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha, Empleado creador) {
    	this.id = id;
      	this.pesoTotal = 0;
  		this.volumenTotal = 0;
  		this.creadoPor = creador;
  		Initialize(cliente, origen, destino, urgencia, estado, fecha);
    }

    private void Initialize(Cliente cliente, Sucursal origen, Sucursal destino, int urgencia, Estado estado, LocalDate fecha) {
      this.cliente = cliente;
      this.origen = origen;
      this.destino = destino;
      this.urgencia = urgencia;
      this.estado = estado;
      this.encomiendas = new HashMap<Integer, Encomienda>();
      this.fechaCreacion = fecha;
      this.CalcularPrioridad();
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
        this.fechaCreacion = fecha;
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
    	return this.fechaCreacion;
    }

    public LocalDate GetFechaEnvio() {
      return this.fechaEnvio;
    }

    public LocalDate GetFechaLlegada() {
      return this.fechaLlegada;
    }

    public int GetPeso(){
    	return this.pesoTotal;
    }

    public int GetVolumen(){
    	this.volumenTotal = 0;
    	for (Map.Entry<Integer, Encomienda> entry : this.encomiendas.entrySet()) {
  			this.volumenTotal += entry.getValue().GetVolumen();
  			}
    	return this.volumenTotal;
    }

    public Map<Integer, Encomienda> GetEncomiendas() {
    	return this.encomiendas;
    }

    public Encomienda GetEncomienda(int id) {
    	return this.encomiendas.get(id);
    }

    public MedioDeTransporte GetCargadoEn() {
    	return this.cargadoEn;
    }

    public void SetConductor(Empleado conductor) {
    	this.conductor = conductor;
    }

    public Empleado GetConductor() {
    	return this.conductor;
    }
    
    public void SetCargadoEn(MedioDeTransporte medio) {
    	this.cargadoEn = medio;
    }
    
    public void SetCargadoPor(Empleado empleado) {
    	this.cargadoPor = empleado;
    }
    
    public Empleado GetCargadoPor() {
    	return this.cargadoPor;
    }
    
    public Empleado GetCreadoPor() {
    	return this.creadoPor;
    }

    public void Enviado() {
    	fechaEnvio = LocalDate.now();
    }

    public void Arrivado() {
    	this.estado = Estado.EnSucursalDestino;
    	fechaLlegada = LocalDate.now();
      this.cargadoEn = null;
    }
    
    public void DeVuelta() {
    	this.estado = Estado.EnSucursalOrigen;
    	this.cargadoEn = null;
    }
 
    /*
     * Otras operaciones
     */
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

 	public void SetEnTransito(){
 		this.estado = Estado.EnTransito;
 	}

  public void AumentarUrgencia() {
    if (this.urgencia < 3) {
      this.urgencia++;
      this.CalcularPrioridad();
    }
  }

  private void CalcularPrioridad() {
    this.prioridad = (this.urgencia * 7) + (this.volumenTotal * 3);
  }

  public int GetPrioridad() {
    return this.prioridad;
  }
}
