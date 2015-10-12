
public class Camion extends MedioDeTransporte {
	protected String marca;
	protected String modelo;
	protected int km;

	public Camion(int capacidadMax, String patente, String marca, String modelo){
		super(patente, capacidadMax);
		this.modelo = modelo;
		this.marca = marca;
	}
	
}