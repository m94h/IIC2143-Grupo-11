package backend;

public class Auxiliar {
	/*
	 * Metodos auxiliares
	 */
	public static boolean isInt(String str)  
	{
	  try  
	  {
	    int i = Integer.parseInt(str);  
	  }
	  catch(NumberFormatException nfe)  
	  {
	    return false;
	  }
	  return true;
	}
}
