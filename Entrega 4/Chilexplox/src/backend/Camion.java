package backend;
import java.util.ArrayList;

public class Camion extends MedioDeTransporte {
	protected String marca;
	protected String modelo;
	protected int km;


	public Camion(String patente, String marca, String modelo,  Sucursal origen, Sucursal destino, int capacidadMax, Estado estado, String cualidadesString){
		super(patente, capacidadMax, origen, destino, estado, cualidadesString);
		this.modelo = modelo;
		this.marca = marca;
		this.km = 0;

	}

	public Camion(String patente, String marca, String modelo,  Sucursal origen, Sucursal destino, int capacidadMax, int km, Estado estado, String cualidadesString){
		super(patente, capacidadMax, origen, destino, estado, cualidadesString);
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