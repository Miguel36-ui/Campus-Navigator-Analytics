package main.java.campus.estudiantes.ordenamientos;

import java.util.List;

import main.java.campus.estudiantes.Estudiante;

/** Heapsort O(n log n) - implementado sobre array/list */
public class HeapSort implements EstrategiaOrdenamiento {

     @Override
    public void ordenar(List<Estudiante> lista, int atributo) {
        int n = lista.size();
        // construir heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) hundir(lista, n, i, atributo);
        for (int i = n - 1; i >= 0; i--) {
            // swap raiz con elemento i
            Estudiante tmp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, tmp);
            hundir(lista, i, 0, atributo); // reducir tamaño del heap
        }
        // resultado en orden ascendente si la comparación considera menor -> actual implementacion deja asc
    }

    private void hundir(List<Estudiante> a, int n, int i, int campo) {
        int mayor = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && comparar(a.get(l), a.get(mayor), campo) > 0) mayor = l;
        if (r < n && comparar(a.get(r), a.get(mayor), campo) > 0) mayor = r;
        if (mayor != i) {
            Estudiante tmp = a.get(i);
            a.set(i, a.get(mayor));
            a.set(mayor, tmp);
            hundir(a, n, mayor, campo);
        }
    }

    private int comparar(Estudiante x, Estudiante y, int campo) {
        if (campo == 1) return Integer.compare(x.id, y.id);
        if (campo == 2) return x.nombre.compareToIgnoreCase(y.nombre);
        return Double.compare(x.promedio, y.promedio);
    }
    
}
