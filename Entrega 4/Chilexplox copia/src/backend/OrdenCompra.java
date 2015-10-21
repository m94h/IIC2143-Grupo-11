package backend;

public class OrdenCompra {

  private int id;
  private int monto_neto;
  private double monto_total;
  private boolean estado;

  public OrdenCompra(int monto_neto) {
    //Generate id
    this.id = 1;
    this.monto_neto = monto_neto;
    this.estado = false;
  }
  
  protected void CalcularTotal() {
    //calcular monto total aplicando impuesto
    this.monto_total = (double)this.monto_neto * 1.19;
  }

}