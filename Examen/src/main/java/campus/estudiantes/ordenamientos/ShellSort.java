package main.java.campus.estudiantes.ordenamientos;

import java.util.List;
import main.java.campus.estudiantes.Estudiante;

public class ShellSort implements EstrategiaOrdenamiento {

    @Override
    public void ordenar(List<Estudiante> lista, int campo) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Estudiante temp = lista.get(i);
                int j = i;
                while (j >= gap && comparar(lista.get(j - gap), temp, campo) > 0) {
                    lista.set(j, lista.get(j - gap));
                    j -= gap;
                }
                lista.set(j, temp);
            }
        }
    }

    private int comparar(Estudiante a, Estudiante b, int campo) {
        switch (campo) {
            case 1: return Integer.compare(a.id, b.id);
            case 2: return a.nombre.compareToIgnoreCase(b.nombre);
            case 3: return Double.compare(a.promedio, b.promedio);
            default: return 0;
        }
    }
}
