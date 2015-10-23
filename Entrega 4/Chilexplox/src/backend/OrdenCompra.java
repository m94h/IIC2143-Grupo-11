package backend;

public class OrdenCompra {

  private int id;
  private int monto;
  private boolean estado;

  public OrdenCompra(int monto) {
    this.id = Sistema.GetInstance().Get_id_orden_compra();
    this.monto = monto;
    this.estado = false;
  }

  public OrdenCompra(int id, int monto) {
    this.id = id;
    this.monto = monto;
    this.estado = false;
  }

  /*
  protected void CalcularTotal() {
    //calcular monto total aplicando impuesto
    this.monto_total = (double)this.monto_neto * 1.19;
  }
  */

}