package main.java.campus.estudiantes.ordenamientos;

import java.util.ArrayList;
import java.util.List;

import main.java.campus.estudiantes.Estudiante;

/** MergeSort (O(n log n) en todos los casos) */

public class MergeSort implements EstrategiaOrdenamiento {

    @Override
    public void ordenar(List<Estudiante> lista, int campo) {
        if (lista.size() <= 1) return;
        List<Estudiante> aux = new ArrayList<>(lista);
        mergeSort(lista, aux, 0, lista.size() - 1, campo);
    }

    private void mergeSort(List<Estudiante> a, List<Estudiante> aux, int lo, int hi, int campo) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        mergeSort(a, aux, lo, mid, campo);
        mergeSort(a, aux, mid + 1, hi, campo);
        merge(a, aux, lo, mid, hi, campo);
    }

    private void merge(List<Estudiante> a, List<Estudiante> aux, int lo, int mid, int hi, int campo) {
        for (int k = lo; k <= hi; k++) aux.set(k, a.get(k));
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a.set(k, aux.get(j++));
            else if (j > hi) a.set(k, aux.get(i++));
            else if (comparar(aux.get(i), aux.get(j), campo) <= 0) a.set(k, aux.get(i++));
            else a.set(k, aux.get(j++));
        }
    }

    private int comparar(Estudiante x, Estudiante y, int campo) {
        if (campo == 1) return Integer.compare(x.id, y.id);
        if (campo == 2) return x.nombre.compareToIgnoreCase(y.nombre);
        return Double.compare(x.promedio, y.promedio);
    }
    
}
