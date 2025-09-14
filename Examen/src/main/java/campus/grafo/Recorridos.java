/** Resultado de un recorrido BFS/DFS: nodos, aristas y costo total */
package main.java.campus.grafo;

import java.util.*;

/** Algoritmos de recorrido: BFS y DFS */
public class Recorridos {

    /** Resultado de un recorrido BFS/DFS: nodos, aristas y costo total */
    public static class RecorridoResultado {
        public List<String> ordenNodos = new ArrayList<>();
        public List<String> aristas = new ArrayList<>();
        public int costoTotal = 0;
    }

    public static RecorridoResultado bfs(Grafo g, String inicio) {
        RecorridoResultado res = new RecorridoResultado();
        Set<String> visitado = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(inicio);
        visitado.add(inicio);

        while (!cola.isEmpty()) {
            String u = cola.poll();
            res.ordenNodos.add(u);
            for (Grafo.Arista a : g.getAdyacencia().getOrDefault(u, new ArrayList<>())) {
                if (!visitado.contains(a.destino)) {
                    visitado.add(a.destino);
                    cola.add(a.destino);
                    res.aristas.add(u + " - " + a.destino + " (" + a.peso + ")");
                    res.costoTotal += a.peso;
                }
            }
        }
        return res;
    }

    public static RecorridoResultado dfs(Grafo g, String inicio) {
        RecorridoResultado res = new RecorridoResultado();
        Set<String> visitado = new HashSet<>();
        Deque<String> pila = new ArrayDeque<>();
        pila.push(inicio);

        while (!pila.isEmpty()) {
            String u = pila.pop();
            if (visitado.contains(u)) continue;
            visitado.add(u);
            res.ordenNodos.add(u);

            List<Grafo.Arista> vecinos = g.getAdyacencia().getOrDefault(u, new ArrayList<>());
            for (int i = vecinos.size() - 1; i >= 0; i--) {
                String v = vecinos.get(i).destino;
                if (!visitado.contains(v)) {
                    pila.push(v);
                    res.aristas.add(u + " - " + v + " (" + vecinos.get(i).peso + ")");
                    res.costoTotal += vecinos.get(i).peso;
                }
            }
        }
        return res;
    }
}
