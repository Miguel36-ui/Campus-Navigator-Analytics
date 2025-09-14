package main.java.campus.grafo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** Kruskal para obtener árbol de expansión mínima */
public class Kruskal {

    public static List<AristaK> mst(Grafo grafo) {
        List<AristaK> aristas = new ArrayList<>();
        for (String u : grafo.getAdyacencia().keySet()) {
            for (Grafo.Arista a : grafo.getAdyacencia().get(u)) {
                aristas.add(new AristaK(u, a.destino, a.peso));
            }
        }

        aristas.sort(Comparator.comparingInt(e -> e.peso));
        Map<String, String> padre = new HashMap<>();
        for (String nodo : grafo.getAdyacencia().keySet()) padre.put(nodo, nodo);

        List<AristaK> mst = new ArrayList<>();
        for (AristaK e : aristas) {
            if (!find(padre, e.origen).equals(find(padre, e.destino))) {
                mst.add(e);
                union(padre, e.origen, e.destino);
            }
        }
        return mst;
    }

    private static String find(Map<String, String> padre, String x) {
        if (!padre.get(x).equals(x)) padre.put(x, find(padre, padre.get(x)));
        return padre.get(x);
    }

    private static void union(Map<String, String> padre, String a, String b) {
        padre.put(find(padre, a), find(padre, b));
    }

    public static class AristaK {
        public String origen, destino;
        public int peso;
        public AristaK(String o, String d, int p) { origen=o; destino=d; peso=p; }
    }
   
    
}
