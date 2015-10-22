package backend;

public class Camion extends MedioDeTransporte {
	protected String marca;
	protected String modelo;
	protected int km;
	protected boolean desocupado;

	public Camion(String patente, String marca, String modelo, int capacidadMax){
		super(patente, capacidadMax);
		this.modelo = modelo;
		this.marca = marca;
		this.km = 0;
		this.desocupado = true;
	}

	public Camion(String patente, String marca, String modelo, int capacidadMax, int km){
		super(patente, capacidadMax);
		this.modelo = modelo;
		this.marca = marca;
		this.km = km;
		this.desocupado = true;
	}

	public boolean Disponible(){
		return desocupado;
	}
	
}