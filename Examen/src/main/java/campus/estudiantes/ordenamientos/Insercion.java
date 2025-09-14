package main.java.campus.estudiantes.ordenamientos;

import java.util.List;

import main.java.campus.estudiantes.Estudiante;

/** Inserción (O(n^2)) */
public class Insercion implements EstrategiaOrdenamiento {


    @Override
    public void ordenar(List<Estudiante> lista, int campo) {
        for (int i = 1; i < lista.size(); i++) {
            Estudiante key = lista.get(i);
            int j = i - 1;
            while (j >= 0 && comparar(lista.get(j), key, campo) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, key);
        }
    }

    private int comparar(Estudiante a, Estudiante b, int campo) {
        if (campo == 1) return Integer.compare(a.id, b.id);
        if (campo == 2) return a.nombre.compareToIgnoreCase(b.nombre);
        return Double.compare(a.promedio, b.promedio);
    }
    
}
