
public class Camion extends MedioDeTransporte {

	protected String marca;
	protected String modelo;
	protected int km;
	protected String patente;

	public Camion(int capacidadMax, String patente, String marca, String modelo){
		super(capacidadMax);
		this.patente = patente;
		this.modelo = modelo;
		this.marca = marca;
	}
	
	public String getPatente(){
		return this.patente;
	}
	
}