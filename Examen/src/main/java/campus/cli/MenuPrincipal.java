package main.java.campus.cli;

import main.java.campus.estudiantes.ordenamientos.Seleccion;

import java.util.*;

import main.java.campus.estudiantes.ArbolAVL;
import main.java.campus.estudiantes.Estudiante;
import main.java.campus.estudiantes.ordenamientos.EstrategiaOrdenamiento;
import main.java.campus.estudiantes.ordenamientos.HeapSort;
import main.java.campus.estudiantes.ordenamientos.Insercion;
import main.java.campus.estudiantes.ordenamientos.MergeSort;
import main.java.campus.estudiantes.ordenamientos.QuickSort;
import main.java.campus.estudiantes.ordenamientos.ShellSort;
import main.java.campus.grafo.Dijkstra;
import main.java.campus.grafo.Grafo;
import main.java.campus.grafo.Kruskal;
// import main.java.campus.grafo.Kruskal.ElementoArista;
import main.java.campus.grafo.Recorridos;
import main.java.campus.incidencias.Incidencia;
import main.java.campus.incidencias.MaxHeapIncidencias;

public class MenuPrincipal {

    private final Scanner sc = new Scanner(System.in);
    private Grafo grafo;
    private ArbolAVL arbolEstudiantes = new ArbolAVL();
    private MaxHeapIncidencias heapIncidencias = new MaxHeapIncidencias();

    public void ejecutar() {
        System.out.println("\n====================================");
        System.out.println("Bienvenido a Campus Navigator & Analytics");
        System.out.println("Sistema de gestión y análisis para campus universitario");
        System.out.println("------------------------------------");
        System.out.println("¿Qué puedes hacer aquí?");
        System.out.println("- Campus: Analiza y navega el mapa del campus (grafos, rutas, MST, etc.)");
        System.out.println("- Estudiantes: Administra y ordena estudiantes (AVL, búsquedas, ordenamientos)");
        System.out.println("- Incidencias: Gestiona tareas urgentes y reportes (heap de prioridades)");
        System.out.println("- Reportes: Consulta métricas, tiempos y tamaños de estructuras");
        System.out.println("------------------------------------");
        System.out.println("En cada módulo encontrarás instrucciones adicionales.");
        System.out.println("====================================\n");
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1) Campus  - Mapa, rutas y análisis de conectividad");
            System.out.println("2) Estudiantes  - Registro, búsqueda y ordenamiento");
            System.out.println("3) Incidencias  - Gestión de tareas urgentes");
            System.out.println("4) Reportes  - Métricas y comparativas");
            System.out.println("5) Salir");
            System.out.print("Elige una opción (1-5): ");
            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "1":
                    menuCampus();
                    break;
                case "2":
                    menuEstudiantes();
                    break;
                case "3":
                    menuIncidencias();
                    break;
                case "4":
                    menuReportes();
                    break;
                case "5":
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida. Ingresa un número del 1 al 5.");
            }
        }
    }

    /* ---------------- REPORTES ---------------- */
    private void menuReportes() {
        System.out.println("\n--- Reportes ---");
        // Tamaños de estructuras
        System.out.println("Nodos en grafo: " + (grafo == null ? 0 : grafo.getAdyacencia().size()));
        System.out.println(
                "Estudiantes en AVL: " + (arbolEstudiantes == null ? 0 : arbolEstudiantes.obtenerTodos().size()));
        System.out.println("Incidencias en heap: " + (heapIncidencias == null ? 0 : heapIncidencias.aLista().size()));

        // Métricas de rutas (si hay grafo)
        if (grafo != null && grafo.getAdyacencia().size() > 1) {
            String[] nodos = grafo.getAdyacencia().keySet().toArray(new String[0]);
            if (nodos.length >= 2) {
                System.out.println("Ejemplo de ruta mínima entre dos nodos:");
                Dijkstra.dijkstra(grafo, nodos[0], nodos[1]);
            }
        }

        // Tiempos de ordenamiento (comparativa simple)
        if (arbolEstudiantes != null && arbolEstudiantes.obtenerTodos().size() > 1) {
            List<Estudiante> base = arbolEstudiantes.obtenerTodos();
            int campo = 1; // Por ID
            EstrategiaOrdenamiento[] algos = {
                    new Insercion(), new Seleccion(), new ShellSort(), new QuickSort(), new MergeSort(), new HeapSort()
            };
            String[] nombres = { "Inserción", "Selección", "Shell", "QuickSort", "MergeSort", "HeapSort" };
            System.out.println("\nComparativa de tiempos de ordenamiento (por ID):");
            for (int i = 0; i < algos.length; i++) {
                List<Estudiante> copia = new ArrayList<>(base);
                long t1 = System.nanoTime();
                algos[i].ordenar(copia, campo);
                long t2 = System.nanoTime();
                System.out.printf("%s: %.3f ms\n", nombres[i], (t2 - t1) / 1e6);
            }
        }
        System.out.println("\nPresiona Enter para regresar...");
        sc.nextLine();

    }

    /* ---------------- CAMPUS ---------------- */
    public void menuCampus() {
        while (true) {
            System.out.println("\n--- MÓDULO CAMPUS ---");
            System.out.println("Este módulo permite analizar el mapa del campus como un grafo.");
            System.out.println("Puedes explorar rutas, conectividad y árboles de expansión mínima.");
            System.out.println("Opciones:");
            System.out.println("1) Crear grafo vacío");
            System.out.println("2) Cargar grafo de prueba (agrega nodos y conexiones de ejemplo)");
            System.out.println("3) Mostrar nodos actuales");
            System.out.println("4) Agregar nodo/arista manualmente");
            System.out.println("5) Eliminar nodo");
            System.out.println("6) BFS (recorrido en anchura desde un nodo)");
            System.out.println("7) DFS (recorrido en profundidad desde un nodo)");
            System.out.println("8) Dijkstra (ruta más corta entre dos nodos)");
            System.out.println("9) MST (Kruskal) (árbol de expansión mínima)");
            System.out.println("10) Validar conexidad (¿todo el campus está conectado?)");
            System.out.println("11) Mostrar matriz/lista de adyacencia (ver conexiones en tabla y lista)");
            System.out.println("12) Regresar");
            System.out.print("Elige una opción (1-12): ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.println("\nCrear grafo vacío:");
                    System.out.println(
                            "Permite iniciar un grafo sin nodos ni aristas. Útil para construir manualmente el mapa del campus.");
                    grafo = new Grafo();
                    System.out.println("Grafo vacío creado. Puedes agregar nodos y aristas manualmente.");
                    break;

                case "2":
                    System.out.println("\nCargar grafo de prueba:");
                    System.out.println(
                            "Agrega nodos y conexiones de ejemplo para probar algoritmos sin ingresar datos manualmente.");
                    grafo = new Grafo();
                    grafo.agregarArista("A", "B", 2);
                    grafo.agregarArista("A", "C", 4);
                    grafo.agregarArista("B", "C", 1);
                    grafo.agregarArista("B", "D", 7);
                    grafo.agregarArista("C", "E", 3);
                    System.out.println("Grafo de prueba creado.");
                    break;

                case "3":
                    System.out.println("\nMostrar nodos actuales:");
                    System.out.println(
                            "Lista todos los nodos presentes en el grafo, representando ubicaciones del campus.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.println("Nodos actuales en el grafo:");
                    for (String nodo : grafo.getAdyacencia().keySet()) {
                        System.out.println("- " + nodo);
                    }
                    break;

                case "4":
                    System.out.println("\nAgregar nodo/arista manualmente:");
                    System.out.println(
                            "Permite agregar un nodo y su conexión (arista) con peso, simulando rutas entre edificios o áreas del campus.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.print("Nombre del primer nodo: ");
                    String n1 = sc.nextLine().trim();
                    System.out.print("Nombre del segundo nodo: ");
                    String n2 = sc.nextLine().trim();
                    System.out.print("Peso de la arista: ");
                    int peso = Integer.parseInt(sc.nextLine().trim());
                    grafo.agregarArista(n1, n2, peso);
                    System.out.println("Arista agregada entre '" + n1 + "' y '" + n2 + "' con peso " + peso);
                    break;

                case "5":
                    System.out.println("\nEliminar nodo:");
                    System.out.println(
                            "Elimina un nodo del grafo y todas sus conexiones. Útil para simular cierres o cambios de áreas del campus.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.print("Nombre del nodo a eliminar: ");
                    String nodoDel = sc.nextLine().trim();
                    boolean eliminado = grafo.eliminarNodo(nodoDel);
                    if (eliminado) {
                        System.out.println("Nodo '" + nodoDel + "' eliminado.");
                    } else {
                        System.out.println("El nodo '" + nodoDel + "' no existe en el grafo.");
                    }
                    break;

                case "6":
                    System.out.println("\nBFS (Breadth-First Search / Búsqueda en Anchura):");
                    System.out.println(
                            "Recorre el grafo por niveles, mostrando primero los nodos más cercanos al nodo de inicio.");
                    System.out.println("Útil para explorar conexiones y distancias mínimas en pasos.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.print("Ingresa el nombre del nodo de inicio: ");
                    String inicioBfs = sc.nextLine().trim();
                    if (!grafo.getAdyacencia().containsKey(inicioBfs)) {
                        System.out.println("El nodo '" + inicioBfs + "' no existe en el grafo.");
                        break;
                    }
                    Recorridos.RecorridoResultado resBfs = Recorridos.bfs(grafo, inicioBfs);
                    System.out.println("\nOrden de visita BFS: " + resBfs.ordenNodos);
                    System.out.println("Aristas recorridas: " + resBfs.aristas);
                    System.out.println("Costo total del recorrido: " + resBfs.costoTotal);
                    break;

                case "7":
                    System.out.println("\nDFS (Depth-First Search / Búsqueda en Profundidad):");
                    System.out.println("Recorre el grafo explorando lo más profundo posible antes de retroceder.");
                    System.out
                            .println("Útil para detectar ciclos, componentes conexas y explorar caminos alternativos.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.print("Ingresa el nombre del nodo de inicio: ");
                    String inicioDfs = sc.nextLine().trim();
                    if (!grafo.getAdyacencia().containsKey(inicioDfs)) {
                        System.out.println("El nodo '" + inicioDfs + "' no existe en el grafo.");
                        break;
                    }
                    Recorridos.RecorridoResultado resDfs = Recorridos.dfs(grafo, inicioDfs);
                    System.out.println("\nOrden de visita DFS: " + resDfs.ordenNodos);
                    System.out.println("Aristas recorridas: " + resDfs.aristas);
                    System.out.println("Costo total del recorrido: " + resDfs.costoTotal);
                    break;

                case "8":
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.println("\nDijkstra (Camino más corto):");
                    System.out.println("Este algoritmo encuentra el camino de menor costo entre dos nodos.");
                    System.out.println("Útil para rutas óptimas en mapas, redes y planificación.");
                    System.out.print("Ingresa el nodo de origen: ");
                    String origen = sc.nextLine().trim();
                    System.out.print("Ingresa el nodo de destino: ");
                    String destino = sc.nextLine().trim();

                    if (!grafo.getAdyacencia().containsKey(origen)) {
                        System.out.println("El nodo de origen '" + origen + "' no existe en el grafo.");
                        break;
                    }
                    if (!grafo.getAdyacencia().containsKey(destino)) {
                        System.out.println("El nodo de destino '" + destino + "' no existe en el grafo.");
                        break;
                    }

                    Dijkstra.DijkstraResultado dijRes = Dijkstra.dijkstra(grafo, origen, destino);
                    if (dijRes.ruta.isEmpty()) {
                        System.out.println("No se encontró ruta entre " + origen + " y " + destino + ".");
                    } else {
                        System.out.println("Ruta óptima: " + dijRes.ruta);
                        System.out.println("Costo total: " + dijRes.costo);
                    }
                    break;

                case "9":
                    System.out.println("\nKruskal (Árbol de Expansión Mínima - MST):");
                    System.out.println(
                            "Encuentra el subconjunto de aristas que conecta todos los nodos con el menor costo total sin formar ciclos.");
                    System.out.println("Útil para optimizar redes de caminos, rutas o conexiones dentro del campus.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    List<Kruskal.AristaK> mst = Kruskal.mst(grafo);
                    double total = 0;
                    for (Kruskal.AristaK e : mst) {
                        System.out.println(e.origen + " - " + e.destino + " (" + e.peso + ")");
                        total += e.peso;
                    }
                    System.out.println("Costo total MST = " + total);
                    break;

                case "10":
                    System.out.println("\nValidar conexidad:");
                    System.out.println("Verifica si existe un camino entre cada par de nodos del grafo.");
                    System.out.println("Si el grafo es conexo, toda la red del campus está comunicada.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    boolean conexo = grafo.esConexo();
                    System.out.println(conexo ? "El grafo es conexo." : "El grafo es disconexo.");
                    break;

                case "11":
                    System.out.println("\nMostrar matriz/lista de adyacencia:");
                    System.out.println(
                            "Permite ver las conexiones entre nodos en forma de matriz (con pesos) o lista (nodos y vecinos).");
                    System.out.println("Útil para analizar rutas, conexiones y estructura general del campus.");
                    if (grafo == null) {
                        System.out.println("Primero crea el grafo.");
                        break;
                    }
                    System.out.println("¿Cómo deseas verla?");
                    System.out.println("1) Matriz de adyacencia (con pesos/INF)");
                    System.out.println("2) Matriz de adyacencia binaria (1=conexión, 0=no)");
                    System.out.println("3) Lista de adyacencia (nodo -> vecinos)");
                    String modo = sc.nextLine().trim();
                    if ("2".equals(modo)) {
                        grafo.imprimirMatrizAdyacencia(true);
                    } else if ("3".equals(modo)) {
                        System.out.println("Lista de adyacencia:");
                        for (Map.Entry<String, List<Grafo.Arista>> entry : grafo.getAdyacencia().entrySet()) {
                            System.out.print(entry.getKey() + " -> ");
                            for (Grafo.Arista a : entry.getValue()) {
                                System.out.print(a.destino + "(" + a.peso + ") ");
                            }
                            System.out.println();
                        }
                    } else {
                        grafo.imprimirMatrizAdyacencia(false);
                    }
                    break;

                case "12":
                    System.out.println("\nRegresar:");
                    System.out.println("Vuelve al menú principal.");
                    return;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    /* ---------------- ESTUDIANTES ---------------- */
    private void menuEstudiantes() {
        while (true) {
            System.out.println("\n--- MÓDULO ESTUDIANTES ---");
            System.out.println("Administra estudiantes usando un árbol AVL para búsquedas eficientes.");
            System.out.println("Puedes registrar, buscar, eliminar y ordenar estudiantes por diferentes campos.");
            System.out.println("Opciones:");
            System.out.println("1) Alta (registrar nuevo estudiante)");
            System.out.println("2) Búsqueda por ID");
            System.out.println("3) Ordenar (elige algoritmo y campo)");
            System.out.println("4) Baja (eliminar estudiante por ID)");
            System.out.println("5) Regresar");
            System.out.print("Elige una opción (1-5): ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    int id;
                    while (true) {
                        System.out.print("ID: ");
                        String idStr = sc.nextLine();
                        try {
                            id = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("El ID debe ser un número entero. Intenta de nuevo.");
                        }
                    }
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Carrera: ");
                    String carrera = sc.nextLine();
                    double prom;
                    while (true) {
                        System.out.print("Promedio: ");
                        String promStr = sc.nextLine();
                        try {
                            prom = Double.parseDouble(promStr);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("El promedio debe ser un número válido. Intenta de nuevo.");
                        }
                    }
                    arbolEstudiantes.insertar(new Estudiante(id, nombre, carrera, prom));
                    System.out.println("Estudiante registrado.");
                    break;
                case "2":
                    int idBusq;
                    while (true) {
                        System.out.print("ID: ");
                        String idStr = sc.nextLine();
                        try {
                            idBusq = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("El ID debe ser un número entero. Intenta de nuevo.");
                        }
                    }
                    Estudiante e = arbolEstudiantes.buscarPorId(idBusq);
                    System.out.println(e == null ? "No encontrado." : e);
                    break;
                case "3":
                    List<Estudiante> lista = arbolEstudiantes.obtenerTodos();
                    if (lista.isEmpty()) {
                        System.out.println("No hay estudiantes.");
                        break;
                    }
                    EstrategiaOrdenamiento algoritmo = elegirAlgoritmo();
                    int campo = -1;
                    while (campo < 1 || campo > 3) {
                        System.out.println("Ordenar por: 1) ID 2) Nombre 3) Promedio");
                        String entrada = sc.nextLine().trim();
                        try {
                            campo = Integer.parseInt(entrada);
                            if (campo < 1 || campo > 3) {
                                System.out.println("Opción inválida, debe ser 1, 2 o 3.");
                            }
                        } catch (NumberFormatException ex) { // ← cambié 'e' por 'ex'
                            System.out.println("Entrada inválida. Ingresa un número (1, 2 o 3).");
                        }
                    }
                    long t1 = System.nanoTime();
                    algoritmo.ordenar(lista, campo);
                    long t2 = System.nanoTime();
                    lista.forEach(System.out::println);
                    System.out.println("Tiempo: " + (t2 - t1) / 1e6 + " ms");
                    break;

                case "4":
                    int idDel;
                    while (true) {
                        System.out.print("ID del estudiante a eliminar: ");
                        String idStr = sc.nextLine();
                        try {
                            idDel = Integer.parseInt(idStr);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("El ID debe ser un número entero. Intenta de nuevo.");
                        }
                    }
                    arbolEstudiantes.eliminar(idDel);
                    System.out.println("Estudiante eliminado (si existía).");
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private EstrategiaOrdenamiento elegirAlgoritmo() {
        while (true) {
            System.out.println("Elige algoritmo: 1) Inserción 2) Selección 3) Shell 4) Quick 5) Merge 6) Heap");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    return new Insercion();
                case "2":
                    return new Seleccion();
                case "3":
                    return new ShellSort();
                case "4":
                    return new QuickSort();
                case "5":
                    return new MergeSort();
                case "6":
                    return new HeapSort();
                default:
                    System.out.println("Entrada inválida. Ingresa un número entre 1 y 6.");
            }
        }
    }

    /* ---------------- INCIDENCIAS ---------------- */
    private void menuIncidencias() {
        while (true) {
            System.out.println("\n--- MÓDULO INCIDENCIAS ---");
            System.out.println("Gestiona tareas urgentes y reportes usando un heap de prioridades.");
            System.out.println("Opciones:");
            System.out.println("1) Insertar incidencia");
            System.out.println("2) Cambiar prioridad de una incidencia");
            System.out.println("3) Atender siguiente (extraer la más prioritaria)");
            System.out.println("4) Listar todas las incidencias");
            System.out.println("5) Ver siguiente a atender (peek)");
            System.out.println("6) Reconstruir heap desde lista actual (buildHeap)");
            System.out.println("7) Regresar");
            System.out.print("Elige una opción (1-7): ");

            String op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    try {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Título: ");
                        String titulo = sc.nextLine();

                        int pr = 0;
                        while (true) {
                            try {
                                System.out.print("Prioridad (1=baja, 2=media, 3=alta): ");
                                pr = Integer.parseInt(sc.nextLine());
                                if (pr < 1 || pr > 3) {
                                    System.out.println("Error: prioridad debe ser 1, 2 o 3. Intenta de nuevo.");
                                    continue;
                                }
                                break; // prioridad válida, salimos del ciclo
                            } catch (NumberFormatException e) {
                                System.out.println("Error: ingresa un número válido (1-3).");
                            }
                        }

                        heapIncidencias.push(new Incidencia(id, titulo, pr, System.currentTimeMillis()));
                        System.out.println("Incidencia insertada.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debes ingresar un número válido para el ID.");
                    }
                    break;

                case "2":
                    try {
                        System.out.print("ID a modificar: ");
                        int idc = Integer.parseInt(sc.nextLine());

                        System.out.print("Nueva prioridad (1-3): ");
                        int np = Integer.parseInt(sc.nextLine());

                        boolean ok = heapIncidencias.cambiarPrioridad(idc, np);
                        System.out.println(ok ? "Prioridad actualizada." : "No se encontró la incidencia.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debes ingresar números válidos para ID y prioridad.");
                    }
                    break;

                case "3":
                    Incidencia top = heapIncidencias.pop();
                    System.out.println(top == null ? "No hay incidencias para atender." : "Atendida: " + top);
                    break;

                case "4":
                    heapIncidencias.mostrarHeap();
                    break;

                case "5":
                    Incidencia peek = heapIncidencias.peek();
                    System.out.println(peek == null ? "No hay incidencias." : "Siguiente a atender: " + peek);
                    break;

                case "6":
                    List<Incidencia> lista = heapIncidencias.aLista();
                    heapIncidencias.buildHeap(lista);
                    System.out.println("Heap reconstruido desde la lista actual.");
                    break;

                case "7":
                    return;

                default:
                    System.out.println("Opción inválida. Ingresa un número del 1 al 7.");
            }
        }
    }
}
