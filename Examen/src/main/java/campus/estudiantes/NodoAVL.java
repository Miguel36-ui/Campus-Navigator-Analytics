package main.java.campus.estudiantes;


/**
 * Nodo simple para usar en el AVL.
 * (Puede usarse también como clase interna, pero se separa para claridad)
 */

public class NodoAVL {

    public Estudiante valor;
    public NodoAVL izq;
    public NodoAVL der;
    public int altura;

    public NodoAVL(Estudiante valor) {
        this.valor = valor;
        this.altura = 1;
    }
    
}
