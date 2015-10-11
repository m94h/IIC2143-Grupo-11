
public class Encomienda {
	private int id;
	private int peso;
	private int volumen;
	
	public Encomienda (int peso, int volumen) {
		this.id = Sistema.GetInstance().Get_id_encomienda();
		this.peso = peso;
		this.volumen = volumen;
	}

	public int GetId() {return id;}

	public int GenerarPresupuesto() {
		int valor;
		valor = Sistema.GetInstance().GetPrecioPorKilo() * peso;
		return valor;
	}
}
