package backend;

public class Encomienda {
	private int id;
	private int peso;
	private int volumen;
	
	public Encomienda (int peso, int volumen) {
		this.id = Sistema.GetInstance().Get_id_encomienda();
		Initialize(peso, volumen);
	}

	public Encomienda (int id, int peso, int volumen) {
		this.id = id;
		Initialize(peso, volumen);
	}

	private void Initialize(int peso, int volumen) {
		this.peso = peso;
		this.volumen = volumen;
	}

	public int GetId() {return id;}

	public int GetPeso() {return peso;}

	public int GetVolumen() {return volumen;}

	public int GenerarPresupuesto() {
		int valor;
		valor = Sistema.GetInstance().GetPrecioPorDensidad() * this.peso * this.volumen;
		return valor;
	}
	
	/*
	 * Static para calcular presupuesto de encomienda sin que se cree
	 */
	public static int Presupuesto(int peso, int volumen) {
		int valor;
		valor = Sistema.GetInstance().GetPrecioPorDensidad() * peso * volumen;
		return valor;
	}
	
}
