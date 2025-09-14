package main.java.campus.estudiantes.ordenamientos;

import java.util.List;
import main.java.campus.estudiantes.Estudiante;

/**
 * Interfaz del patrón Strategy para ordenar la lista de estudiantes.
 * El parámetro 'campo' debe ser: 1=ID, 2=Nombre, 3=Promedio.
 */

public interface EstrategiaOrdenamiento {

    void ordenar(List<Estudiante> lista, int atributo);

}
