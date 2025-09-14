package main.java.campus.incidencias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxHeapIncidencias {

    private List<Incidencia> heap = new ArrayList<>();
    private Map<Integer, Integer> mapaPos = new HashMap<>(); // id -> índice en heap

    // Comparar prioridades y timestamp
    private int comparar(Incidencia a, Incidencia b) {
        return a.compareTo(b); // prioridad y timestamp
    }

    // Intercambiar elementos y actualizar mapa
    private void swap(int i, int j) {
        Incidencia temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        mapaPos.put(heap.get(i).getId(), i);
        mapaPos.put(heap.get(j).getId(), j);
    }

    // Subir elemento
    private void subir(int index) {
        while (index > 0) {
            int padre = (index - 1) / 2;
            if (comparar(heap.get(index), heap.get(padre)) <= 0) break;
            swap(index, padre);
            index = padre;
        }
    }

    // Bajar elemento
    private void bajar(int index) {
        int n = heap.size();
        while (true) {
            int izq = 2 * index + 1;
            int der = 2 * index + 2;
            int mayor = index;

            if (izq < n && comparar(heap.get(izq), heap.get(mayor)) > 0) mayor = izq;
            if (der < n && comparar(heap.get(der), heap.get(mayor)) > 0) mayor = der;
            if (mayor == index) break;

            swap(index, mayor);
            index = mayor;
        }
    }

    // ---------------- MÉTODOS REQUERIDOS ----------------

    // Push: insertar incidencia
    public void push(Incidencia inc) {
        if (mapaPos.containsKey(inc.getId())) {
            System.out.println("Error: La incidencia con ID " + inc.getId() + " ya existe.");
            return;
        }
        heap.add(inc);
        int index = heap.size() - 1;
        mapaPos.put(inc.getId(), index);
        subir(index);
    }

    // Pop: eliminar máxima prioridad
    public Incidencia pop() {
        if (heap.isEmpty()) {
            System.out.println("Error: No hay incidencias para eliminar.");
            return null;
        }

        Incidencia max = heap.get(0);
        Incidencia ultimo = heap.remove(heap.size() - 1);
        mapaPos.remove(max.getId());

        if (!heap.isEmpty()) {
            heap.set(0, ultimo);
            mapaPos.put(ultimo.getId(), 0);
            bajar(0);
        }

        return max;
    }

    // Peek: ver máxima prioridad sin eliminar
    public Incidencia peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    // Cambiar prioridad y re-heapify
    public boolean cambiarPrioridad(int id, int nuevaPrioridad) {
        if (!mapaPos.containsKey(id)) {
            System.out.println("Error: No existe la incidencia con ID " + id);
            return false;
        }
        if (nuevaPrioridad < 1 || nuevaPrioridad > 3) {
            System.out.println("Error: Prioridad inválida, debe ser 1, 2 o 3.");
            return false;
        }

        int index = mapaPos.get(id);
        int viejaPrioridad = heap.get(index).getPrioridad();
        heap.get(index).setPrioridad(nuevaPrioridad);

        if (nuevaPrioridad > viejaPrioridad) subir(index);
        else bajar(index);

        return true;
    }

    // BuildHeap: reconstruir heap desde lista
    public void buildHeap(List<Incidencia> lista) {
        heap.clear();
        mapaPos.clear();
        for (Incidencia inc : lista) {
            heap.add(inc);
            mapaPos.put(inc.getId(), heap.size() - 1);
        }
        // Re-heapify O(n)
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            bajar(i);
        }
    }

    // Mostrar heap (opcional)
    public void mostrarHeap() {
        System.out.println("Heap de incidencias:");
        for (Incidencia inc : heap) {
            System.out.println(inc);
        }
    }

    // Obtener lista de incidencias (para buildHeap u otras operaciones)
    public List<Incidencia> aLista() {
        return new ArrayList<>(heap);
    }
}
