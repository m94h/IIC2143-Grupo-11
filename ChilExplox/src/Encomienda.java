
public class Encomienda {
	private int id;
	private int peso;
	private int volumen;
	
	public Encomienda (int peso, int volumen) {
		this.id = Sistema.Get_id_encomienda();
		this.peso = peso;
		this.volumen = volumen;
	}

	public int getId() {return id;}

	public int GenerarPresupuesto() {
		int valor;
		valor = Sistema.precioPorKilo * peso;
		return valor;
	}
}
