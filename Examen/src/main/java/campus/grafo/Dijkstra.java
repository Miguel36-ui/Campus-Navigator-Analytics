package main.java.campus.grafo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** Implementación de Dijkstra para rutas de costo mínimo desde un origen */
public class Dijkstra {


        public static class DijkstraResultado {
            public List<String> ruta = new ArrayList<>();
            public int costo = Integer.MAX_VALUE;
        }

        public static DijkstraResultado dijkstra(Grafo grafo, String inicio, String destino) {
            Map<String, Integer> dist = new HashMap<>();
            Map<String, String> previo = new HashMap<>();
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());

            for (String nodo : grafo.getAdyacencia().keySet()) {
                dist.put(nodo, Integer.MAX_VALUE);
            }
            dist.put(inicio, 0);
            pq.add(new AbstractMap.SimpleEntry<>(inicio, 0));

            while (!pq.isEmpty()) {
                String actual = pq.poll().getKey();
                for (Grafo.Arista a : grafo.getAdyacencia().get(actual)) {
                    int nuevaDist = dist.get(actual) + a.peso;
                    if (nuevaDist < dist.get(a.destino)) {
                        dist.put(a.destino, nuevaDist);
                        previo.put(a.destino, actual);
                        pq.add(new AbstractMap.SimpleEntry<>(a.destino, nuevaDist));
                    }
                }
            }

            DijkstraResultado res = new DijkstraResultado();
            if (dist.get(destino) == Integer.MAX_VALUE) {
                System.out.println("No hay ruta entre " + inicio + " y " + destino);
                return res;
            }

            // Construir la ruta desde destino hasta inicio
            for (String at = destino; at != null; at = previo.get(at)) {
                res.ruta.add(0, at);
            }
            res.costo = dist.get(destino);

            // Imprimir resultado en consola
            System.out.println("Ruta más corta: " + res.ruta + " (costo " + res.costo + ")");
            return res;
        }

    }
