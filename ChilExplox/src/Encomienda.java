
public class Encomienda {
	private int id;
	private int peso;
	private int volumen;
	
	public Encomienda (int peso, int volumen) {
		this.id = Empresa.Get_id_encomienda();
		this.id = id
		this.peso = peso;
		this.volumen = volumen;
	}

	public int GenerarPresupuesto() {
		int valor;
		valor = Empresa.precioPorKilo * peso;
		return valor;
	}
}
