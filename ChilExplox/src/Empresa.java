import java.util.ArrayList;
import java.util.List;

public class Empresa {
	private String nombre;
	private String RUT;
	public List<Sucursal> sucursales;
	public List<MedioDeTransporte> flota;
	
	public Empresa (String nombre, String rut)
	{
		this.nombre = nombre;
		this.RUT = rut;
		this.sucursales = new ArrayList<Sucursal>();
		this.flota = new ArrayList<MedioDeTransporte>();
	}
	
	public void AgregarSucursal (Sucursal newSucursal)
	{
		this.sucursales.add(newSucursal);
	}
}
