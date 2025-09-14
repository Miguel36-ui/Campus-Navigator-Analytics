package main.java.campus.estudiantes.ordenamientos;

import java.util.ArrayList;
import java.util.List;

import main.java.campus.estudiantes.Estudiante;

/**
 * Radix Sort (LSB) para IDs enteros no negativos.
 * Si 'campo' != 1 (ID), se delega a inserción por simplicidad.
 */
public class RadixSort implements EstrategiaOrdenamiento {

    @Override
    public void ordenar(List<Estudiante> lista, int campo) {
        if (campo != 1) {
            // Radix sólo para ID; usar insertion como fallback
            new Insercion().ordenar(lista, campo);
            return;
        }
        if (lista.isEmpty()) return;
        int max = lista.stream().mapToInt(s -> s.id).max().orElse(0);
        int exp = 1;
        List<Estudiante> aux = new ArrayList<>(lista);
        while (max / exp > 0) {
            int[] count = new int[10];
            for (Estudiante e : lista) count[(e.id / exp) % 10]++;
            for (int i = 1; i < 10; i++) count[i] += count[i - 1];
            for (int i = lista.size() - 1; i >= 0; i--) {
                Estudiante e = lista.get(i);
                int d = (e.id / exp) % 10;
                aux.set(--count[d], e);
            }
            for (int i = 0; i < lista.size(); i++) lista.set(i, aux.get(i));
            exp *= 10;
        }
    }
    
}
