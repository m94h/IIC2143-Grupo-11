package backend;

public class Encomienda {
	private int id;
	private String nombre;
	private int peso;
	private int volumen;
	
	public Encomienda (String nombre, int peso, int volumen) {
		this.id = Sistema.GetInstance().Get_id_encomienda();
		Initialize(nombre, peso, volumen);
	}

	public Encomienda (int id, String nombre, int peso, int volumen) {
		this.id = id;
		Initialize(nombre, peso, volumen);
	}

	private void Initialize(String nombre, int peso, int volumen) {
		this.nombre = nombre;
		this.peso = peso;
		this.volumen = volumen;
	}

	public int GetId() {return id;}
	
	public String GetNombre() {return nombre;}

	public int GetPeso() {return peso;}

	public int GetVolumen() {return volumen;}

	public int GenerarPresupuesto() {
		int valor;
		valor = Sistema.GetInstance().GetPrecioPorGr() * this.peso + Sistema.GetInstance().GetPrecioPorCc() * this.volumen;
		return valor;
	}
	
	/*
	 * Static para calcular presupuesto de encomienda sin que se cree
	 */
	public static int Presupuesto(int peso, int volumen) {
		int valor;
		valor = Sistema.GetInstance().GetPrecioPorGr() * peso + Sistema.GetInstance().GetPrecioPorCc() * volumen;
		return valor;
	}
	
}
