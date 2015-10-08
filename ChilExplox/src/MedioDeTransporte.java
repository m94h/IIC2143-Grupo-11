
public abstract class MedioDeTransporte {
	protected int capacidad;
	protected int enUso;
	protected Sucursal origen;
	protected Sucursal destino;
	protected Estado estado;
	
	public MedioDeTransporte (int capacidad)
	{
		this.capacidad = capacidad;
	}
	
}
