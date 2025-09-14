package main.java.campus.estudiantes;



public class Estudiante {

    public int id;
    public String nombre;
    public String carrera;
    public double promedio;

    public Estudiante(int id, String nombre, String carrera, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %.2f", id, nombre, carrera, promedio);
    }

}
