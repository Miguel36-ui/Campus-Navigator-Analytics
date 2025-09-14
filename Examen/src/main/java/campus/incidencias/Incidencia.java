package main.java.campus.incidencias;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Incidencia {

    private int id;
    private String titulo;
    private int prioridad; // 1 = baja, 2 = media, 3 = alta
    private long timestamp; // epoch millis

    public Incidencia(int id, String titulo, int prioridad, long timestamp) {
        this.id = id;
        this.titulo = titulo;
        if (prioridad < 1 || prioridad > 3) {
            throw new IllegalArgumentException(
                "Prioridad inválida: debe ser 1 (baja), 2 (media) o 3 (alta).");
        }
        this.prioridad = prioridad;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) {
        if (prioridad < 1 || prioridad > 3) {
            throw new IllegalArgumentException(
                "Prioridad inválida: debe ser 1 (baja), 2 (media) o 3 (alta).");
        }
        this.prioridad = prioridad;
    }
    public long getTimestamp() { return timestamp; }

    // Comparación considerando prioridad y timestamp
    public int compareTo(Incidencia otra) {
        int cmp = Integer.compare(this.prioridad, otra.prioridad);
        if (cmp != 0) return cmp;
        return Long.compare(this.timestamp, otra.timestamp);
    }

    @Override
    public String toString() {
        // Convertir timestamp a fecha legible
        String fecha = Instant.ofEpochMilli(timestamp)
                              .atZone(ZoneId.systemDefault())
                              .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format("[%d] (prio=%d) %s @%s", id, prioridad, titulo, fecha);
    }
}
