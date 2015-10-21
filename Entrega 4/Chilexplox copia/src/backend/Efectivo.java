package backend;

public class Efectivo implements IMedioDePago {
  public boolean Pagar (int monto) {
    return true;
  }
  
  public int CalcularVuelto () {
	  return 0;
  }
}