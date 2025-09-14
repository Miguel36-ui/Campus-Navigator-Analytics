package main.java.campus.estudiantes.ordenamientos;

import java.util.List;

import main.java.campus.estudiantes.Estudiante;

/** Selección (O(n^2)) */

public class Seleccion implements EstrategiaOrdenamiento {

        @Override
    public void ordenar(List<Estudiante> lista, int campo) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (comparar(lista.get(j), lista.get(minIdx), campo) < 0) minIdx = j;
            }
            if (minIdx != i) {
                Estudiante tmp = lista.get(i);
                lista.set(i, lista.get(minIdx));
                lista.set(minIdx, tmp);
            }
        }
    }

    private int comparar(Estudiante a, Estudiante b, int campo) {
        if (campo == 1) return Integer.compare(a.id, b.id);
        if (campo == 2) return a.nombre.compareToIgnoreCase(b.nombre);
        return Double.compare(a.promedio, b.promedio);
    }
    
}
