package backend;

public class Tarjeta implements IMedioDePago {
  public boolean Pagar (int monto) {
    return true;
  }
  
  public int CalcularVuelto () {
	  return 0;
  }

  public void IngresarMonto(){

  }
  
}