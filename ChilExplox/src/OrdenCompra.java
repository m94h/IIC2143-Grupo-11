public class OrdenCompra {

  private int id;
  private int monto_neto;
  private int impuesto;
  private int monto_total;
  private boolean estado;

  public OrdenCompra(int monto_neto) {
    //Generate id
    this.id = 1;
    this.monto_neto = monto_neto;
    this.estado = false;
  }

  protected CalcularTotal() {
    //calcular impuesto y monto total
    this.impuesto = (int) this.monto_neto * 0.19;
    this.monto_total = this.monto_neto + this.impuesto;
  }

}