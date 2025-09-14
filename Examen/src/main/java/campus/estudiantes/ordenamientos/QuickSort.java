package main.java.campus.estudiantes.ordenamientos;


import java.util.List;

import main.java.campus.estudiantes.Estudiante;

/** QuickSort (promedio O(n log n)) */

public class QuickSort implements EstrategiaOrdenamiento {

    @Override
    public void ordenar(List<Estudiante> lista, int campo) {
        quick(lista, 0, lista.size() - 1, campo);
    }

    private void quick(List<Estudiante> a, int lo, int hi, int campo) {
        if (lo < hi) {
            int p = particionar(a, lo, hi, campo);
            quick(a, lo, p - 1, campo);
            quick(a, p + 1, hi, campo);
        }
    }

    private int particionar(List<Estudiante> a, int lo, int hi, int campo) {
        Estudiante pivot = a.get(hi);
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (comparar(a.get(j), pivot, campo) <= 0) {
                i++;
                Estudiante tmp = a.get(i);
                a.set(i, a.get(j));
                a.set(j, tmp);
            }
        }
        Estudiante tmp = a.get(i + 1);
        a.set(i + 1, a.get(hi));
        a.set(hi, tmp);
        return i + 1;
    }

    private int comparar(Estudiante a, Estudiante b, int campo) {
        if (campo == 1) return Integer.compare(a.id, b.id);
        if (campo == 2) return a.nombre.compareToIgnoreCase(b.nombre);
        return Double.compare(a.promedio, b.promedio);
    }
}
