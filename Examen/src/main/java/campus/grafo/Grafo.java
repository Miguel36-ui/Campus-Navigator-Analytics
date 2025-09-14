package main.java.campus.grafo;

import java.util.*;

/**
 * Representación de un grafo ponderado no dirigido mediante lista de adyacencia.
 */
public class Grafo {

    /**
     * Elimina un nodo y todas sus aristas del grafo.
     */
    /**
     * Elimina un nodo y todas sus aristas del grafo. Devuelve true si existía.
     */
    public boolean eliminarNodo(String nodo) {
        if (!adyacencia.containsKey(nodo)) return false;
        adyacencia.remove(nodo);
        for (List<Arista> lista : adyacencia.values()) {
            lista.removeIf(a -> a.destino.equals(nodo));
        }
        return true;
    }

    /**
     * Devuelve la matriz de adyacencia del grafo.
     * Si no hay conexión, el valor es Integer.MAX_VALUE (infinito).
     */
    public int[][] getMatrizAdyacencia() {
        int n = adyacencia.size();
        String[] nodos = adyacencia.keySet().toArray(new String[0]);
        Map<String, Integer> idx = new HashMap<>();
        for (int i = 0; i < n; i++) idx.put(nodos[i], i);
        int[][] matriz = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(matriz[i], Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) matriz[i][i] = 0;
        for (int i = 0; i < n; i++) {
            String u = nodos[i];
            for (Arista a : adyacencia.get(u)) {
                int j = idx.get(a.destino);
                matriz[i][j] = a.peso;
            }
        }
        return matriz;
    }

    /**
     * Imprime la matriz de adyacencia en consola, alineada y con opción de modo binario (1/0).
     * Si modoBinario es true, muestra 1 si hay conexión, 0 si no; si es false, muestra pesos/INF.
     */
    public void imprimirMatrizAdyacencia() {
        imprimirMatrizAdyacencia(false);
    }

    public void imprimirMatrizAdyacencia(boolean modoBinario) {
        int[][] matriz = getMatrizAdyacencia();
        String[] nodos = adyacencia.keySet().toArray(new String[0]);
        // Calcular ancho máximo de columna
        int maxLen = 3; // para "INF" o "  1"
        for (String nodo : nodos) maxLen = Math.max(maxLen, nodo.length());
        String formatoCab = "%" + maxLen + "s ";
        String formatoCelda = "%" + maxLen + "s ";

        // Encabezado
        System.out.printf(formatoCab, "");
        for (String nodo : nodos) System.out.printf(formatoCab, nodo);
        System.out.println();
        for (int i = 0; i < matriz.length; i++) {
            System.out.printf(formatoCab, nodos[i]);
            for (int j = 0; j < matriz.length; j++) {
                String val;
                if (modoBinario) {
                    val = (matriz[i][j] != Integer.MAX_VALUE && i != j) ? "1" : "0";
                } else {
                    if (matriz[i][j] == Integer.MAX_VALUE)
                        val = "INF";
                    else
                        val = String.valueOf(matriz[i][j]);
                }
                System.out.printf(formatoCelda, val);
            }
            System.out.println();
        }
        if (modoBinario) {
            System.out.println("(1 = hay conexión, 0 = no hay conexión)");
        } else {
            System.out.println("(INF = no hay conexión, diagonal = 0)");
        }
    }

    /**
     * Verifica si el grafo es conexo usando BFS.
     */
    public boolean esConexo() {
        if (adyacencia.isEmpty()) return true;
        Set<String> visitados = new HashSet<>();
        String inicio = adyacencia.keySet().iterator().next();
        Queue<String> cola = new LinkedList<>();
        cola.add(inicio);
        visitados.add(inicio);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Arista a : adyacencia.getOrDefault(actual, new ArrayList<>())) {
                if (!visitados.contains(a.destino)) {
                    cola.add(a.destino);
                    visitados.add(a.destino);
                }
            }
        }
        return visitados.size() == adyacencia.size();
    }

    private Map<String, List<Arista>> adyacencia;

    public Grafo() {
        this.adyacencia = new HashMap<>();
    }

    public void agregarArista(String origen, String destino, int peso) {
        adyacencia.putIfAbsent(origen, new ArrayList<>());
        adyacencia.putIfAbsent(destino, new ArrayList<>());
        adyacencia.get(origen).add(new Arista(destino, peso));
        adyacencia.get(destino).add(new Arista(origen, peso)); // grafo no dirigido
    }

    public void bfs(String inicio) {
        if (!adyacencia.containsKey(inicio)) {
            System.out.println("El nodo no existe.");
            return;
        }
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(inicio);
        visitados.add(inicio);

        System.out.print("BFS: ");
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            System.out.print(actual + " ");
            for (Arista a : adyacencia.getOrDefault(actual, new ArrayList<>())) {
                if (!visitados.contains(a.destino)) {
                    cola.add(a.destino);
                    visitados.add(a.destino);
                }
            }
        }
        System.out.println();
    }

    public void dfs(String inicio) {
        if (!adyacencia.containsKey(inicio)) {
            System.out.println("El nodo no existe.");
            return;
        }
        Set<String> visitados = new HashSet<>();
        System.out.print("DFS: ");
        dfsRecursivo(inicio, visitados);
        System.out.println();
    }

    private void dfsRecursivo(String nodo, Set<String> visitados) {
        visitados.add(nodo);
        System.out.print(nodo + " ");
        for (Arista a : adyacencia.getOrDefault(nodo, new ArrayList<>())) {
            if (!visitados.contains(a.destino)) {
                dfsRecursivo(a.destino, visitados);
            }
        }
    }

    public Map<String, List<Arista>> getAdyacencia() {
        return adyacencia;
    }

    public static class Arista {
        public String destino;
        public int peso;
        public Arista(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }


}