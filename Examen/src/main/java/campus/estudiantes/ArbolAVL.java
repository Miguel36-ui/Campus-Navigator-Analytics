package main.java.campus.estudiantes;

import java.util.ArrayList;
import java.util.List;

/** Árbol AVL para almacenar estudiantes por id */
public class ArbolAVL {

     private NodoAVL raiz;

    private int altura(NodoAVL n) { return n == null ? 0 : n.altura; }

    private int factorBalance(NodoAVL n) { return n == null ? 0 : altura(n.izq) - altura(n.der); }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;
        x.der = y;
        y.izq = T2;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;
        y.izq = x;
        x.der = T2;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        return y;
    }

    public void insertar(Estudiante e) { raiz = insertarRec(raiz, e); }

    private NodoAVL insertarRec(NodoAVL nodo, Estudiante e) {
        if (nodo == null) return new NodoAVL(e);
        if (e.id < nodo.valor.id) nodo.izq = insertarRec(nodo.izq, e);
        else if (e.id > nodo.valor.id) nodo.der = insertarRec(nodo.der, e);
        else { // id igual -> actualizar datos
            nodo.valor = e;
            return nodo;
        }

        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        int fb = factorBalance(nodo);

        // LL
        if (fb > 1 && e.id < nodo.izq.valor.id) return rotarDerecha(nodo);
        // RR
        if (fb < -1 && e.id > nodo.der.valor.id) return rotarIzquierda(nodo);
        // LR
        if (fb > 1 && e.id > nodo.izq.valor.id) {
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }
        // RL
        if (fb < -1 && e.id < nodo.der.valor.id) {
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    public Estudiante buscarPorId(int id) {
        NodoAVL cur = raiz;
        while (cur != null) {
            if (id == cur.valor.id) return cur.valor;
            cur = id < cur.valor.id ? cur.izq : cur.der;
        }
        return null;
    }

    public List<Estudiante> buscarPorNombre(String nombre) {
        List<Estudiante> encontrados = new ArrayList<>();
        buscarPorNombreRec(raiz, nombre.toLowerCase(), encontrados);
        return encontrados;
    }

    private void buscarPorNombreRec(NodoAVL nodo, String nombreLower, List<Estudiante> out) {
        if (nodo == null) return;
        buscarPorNombreRec(nodo.izq, nombreLower, out);
        if (nodo.valor.nombre.toLowerCase().contains(nombreLower)) out.add(nodo.valor);
        buscarPorNombreRec(nodo.der, nombreLower, out);
    }

    // Eliminación completa (con rebalanceo)
    public void eliminar(int id) { raiz = eliminarRec(raiz, id); }

    private NodoAVL eliminarRec(NodoAVL nodo, int id) {
        if (nodo == null) return null;
        if (id < nodo.valor.id) nodo.izq = eliminarRec(nodo.izq, id);
        else if (id > nodo.valor.id) nodo.der = eliminarRec(nodo.der, id);
        else {
            // nodo encontrado
            if (nodo.izq == null || nodo.der == null) {
                NodoAVL temp = (nodo.izq != null) ? nodo.izq : nodo.der;
                if (temp == null) { // sin hijos
                    nodo = null;
                } else { // un hijo
                    nodo = temp;
                }
            } else {
                // dos hijos: obtener sucesor (mínimo de la subárbol derecho)
                NodoAVL sucesor = minValueNode(nodo.der);
                nodo.valor = sucesor.valor;
                nodo.der = eliminarRec(nodo.der, sucesor.valor.id);
            }
        }

        if (nodo == null) return null;

        nodo.altura = Math.max(altura(nodo.izq), altura(nodo.der)) + 1;
        int fb = factorBalance(nodo);

        // LL
        if (fb > 1 && factorBalance(nodo.izq) >= 0) return rotarDerecha(nodo);
        // LR
        if (fb > 1 && factorBalance(nodo.izq) < 0) {
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }
        // RR
        if (fb < -1 && factorBalance(nodo.der) <= 0) return rotarIzquierda(nodo);
        // RL
        if (fb < -1 && factorBalance(nodo.der) > 0) {
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL minValueNode(NodoAVL nodo) {
        NodoAVL current = nodo;
        while (current.izq != null) current = current.izq;
        return current;
    }

    /** Devuelve todos los estudiantes en orden (por ID ascendente) */
    public List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        inorder(raiz, lista);
        return lista;
    }

    private void inorder(NodoAVL nodo, List<Estudiante> out) {
        if (nodo == null) return;
        inorder(nodo.izq, out);
        out.add(nodo.valor);
        inorder(nodo.der, out);
    }

    
}
