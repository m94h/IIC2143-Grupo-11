package backend;

public class Camion extends MedioDeTransporte {
	protected String marca;
	protected String modelo;
	protected int km;


	public Camion(String patente, String marca, String modelo,  Sucursal origen, Sucursal destino, int capacidadMax){
		super(patente, capacidadMax, origen, destino);
		this.modelo = modelo;
		this.marca = marca;
		this.km = 0;

	}

	public Camion(String patente, String marca, String modelo,  Sucursal origen, Sucursal destino, int capacidadMax, int km){
		super(patente, capacidadMax, origen, destino);
		this.modelo = modelo;
		this.marca = marca;
		this.km = km;

	}

	public boolean Disponible(){
		return desocupado;
	}

	public String GetMarca() {return marca;}

	public String GetModelo() {return modelo;}

	public int GetKm() {return km;}

}