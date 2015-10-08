public class Efectivo implements IMedioPago {
  public boolean Pagar (int monto) {
    return true;
  }
  
  public int CalcularVuelto () {
	  return 0;
  }
}