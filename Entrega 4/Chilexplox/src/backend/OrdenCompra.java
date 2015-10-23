package backend;

public class OrdenCompra {

  private int monto;
  private MedioPago medioPago;
  private boolean estado;

  public OrdenCompra(int monto) {
    //this.id = Sistema.GetInstance().Get_id_orden_compra();
    this.monto = monto;
    this.estado = false;
  }

  public OrdenCompra(int monto, MedioPago medioPago) {
    //this.id = id;
    this.monto = monto;
    this.medioPago = medioPago;
    this.estado = false;
  }

  public void Pagar(MedioPago medioPago) {
    this.medioPago = medioPago;
    this.estado = true;
  }

  /*
  protected void CalcularTotal() {
    //calcular monto total aplicando impuesto
    this.monto_total = (double)this.monto_neto * 1.19;
  }
  */

}