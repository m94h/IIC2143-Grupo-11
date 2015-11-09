package backend;
import java.util.Map;
import java.util.Map.Entry;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Optimizador {
	
	public static HashMap<Integer, List<Integer>> Optimizar(MedioDeTransporte medio) {
		Sucursal origen = medio.GetOrigen();
		// pedidosPorSucursal tiene como llave el id de la sucursal y la lista de pedidos
		Map<Integer,List<int[]>> pedidosPorSucursal = new HashMap<Integer,List<int[]>>(); 
		
		// Se busca agrupar todos los pedidos que van a la misma sucursal dentro de un HashMap
		for(Entry<Integer, Pedido> entry : Sistema.GetInstance().GetPedidos().entrySet()) {
		    Pedido pedido = entry.getValue();
		    if (pedido.GetOrigen() == origen) {
		    	if (pedidosPorSucursal.get(pedido.GetDestino().GetId()) == null) {
		    		List<int[]> pedidos = new ArrayList<int[]>();
		    		pedidosPorSucursal.put(pedido.GetDestino().GetId(), pedidos);
		    	}
	    		List<int[]> pedidos = pedidosPorSucursal.get(pedido.GetDestino().GetId());
	    		int[] datosPedido = {pedido.GetId(), pedido.GetUrgencia(), pedido.GetVolumen()};
	    		pedidos.add(datosPedido);		    	
		    }
		}
		
		// Optimizamos para cada sucursal
		Map<Integer, Integer> urgenciaSucursal = new HashMap<Integer,Integer>();
		Map<Integer, List<Integer>> optimosSucursal = new HashMap<Integer,List<Integer>>();
		
		for(Entry<Integer, List<int[]>> entry : pedidosPorSucursal.entrySet()) {
			List<int[]> pedidos = entry.getValue();
			List<Integer> pedidosSeleccionados = new ArrayList<>();
			int capacidadActual = medio.capacidadMax;
			int urgencia = 0;
			boolean iterando = true;
			int[] pedido_posible = {0, 0, 0};
			while (iterando) {
				for (int[] pedido: pedidos) {
					if (pedido[2] <= medio.capacidadActual){
						if (pedido_posible[0] == 0) {
							pedido_posible = pedido;
						}
						else {
							if (pedido[1] > pedido_posible[1]){
								pedido_posible = pedido;
							}
							else if (pedido[1] == pedido_posible[1]) {
								if (pedido[2] < pedido_posible[2]) {
									pedido_posible = pedido;
								}
							}
						}
					}
				}
				pedidosSeleccionados.add(pedido_posible[0]);
				capacidadActual -= pedido_posible[2];
				urgencia += pedido_posible[1];
				pedidos.remove(pedido_posible);
				if (pedidos.size() == 0 || pedido_posible[0] == 0)
					iterando = false;
			}
			urgenciaSucursal.put(entry.getKey(), urgencia);	
			optimosSucursal.put(entry.getKey(), pedidosSeleccionados);
		}
		
		int id_sucursal_seleccionada = 0;
		int urgencia_sucursal_seleccionada = 0;
		for(Entry<Integer, Integer> entry : urgenciaSucursal.entrySet()) {
			if (entry.getValue() > urgencia_sucursal_seleccionada) {
				urgencia_sucursal_seleccionada = entry.getValue();
				id_sucursal_seleccionada = entry.getKey();
			}
		}
		
		HashMap<Integer, List<Integer>> rt = new HashMap<>();
		rt.put(id_sucursal_seleccionada, optimosSucursal.get(id_sucursal_seleccionada));
				
		return rt;
	}
}
