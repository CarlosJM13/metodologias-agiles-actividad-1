import java.util.Scanner;
public class ConfiguracionInicialDeLaSala {

    private final Scanner sc = new Scanner(System.in);
    private int filas;
    private int columnas;
    private String[][] asientos;   // identificadores en memoria (A1, A2, ... B1, B2, ...)
    //ver si el asiento esta disponible o no
    private boolean[][] ocupados;

    // Método que se llama desde el main
    public void iniciar() {
        // 1. Pedir el tamaño de la sala (con validación)
        filas = pedirNumero("¿Cuántas filas tendrá la sala? (máximo 26): ", 26,
                "Dimensiones inválidas. Ingrese números enteros mayores a cero (máximo 26 filas).");
        columnas = pedirNumero("¿Cuántos asientos por fila tendrá la sala?: ", Integer.MAX_VALUE,
                "Dimensiones inválidas. Ingrese números enteros mayores a cero.");

        // 2. Generar los identificadores automáticamente
        generarAsientos();

        //cuando la sala se crea todos los asientos estan libres al inicio
        ocupados = new boolean[filas][columnas];

        // 3. Mostrar la sala
        mostrarSala();

        //Menu
        int opcion = 0;
        while(opcion !=3) {
            System.out.println("=========== Menú de JoPo Cine ===========");
            System.out.println("1. Seleccionar asientos");
            System.out.println("2. Sala");
            System.out.println("3. Salir de JoPo Cine");
            opcion = pedirNumero("Elija una opción: ", 3, "Opción inválida. Elija 1, 2 o 3.");

            if (opcion == 1) {
                seleccionarAsiento();
            } else if (opcion == 2) {
                mostrarSala();
            }
            System.out.println("¡Vuelve pronto a JoPo!");
        }
    }

    // Pide un entero entre 1 y max. Si es inválido, muestra el error y vuelve a pedir.
    private int pedirNumero(String mensaje, int max, String error) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();

            try {
                int valor = Integer.parseInt(entrada);
                if (valor > 0 && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // Letras, vacío o decimales caen aquí
            }
            System.out.println(error);
        }
    }

    // Crea los identificadores: fila con letra y asiento con número (A1, A2, B1...)
    private void generarAsientos() {
        asientos = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                asientos[i][j] = "" + (char) ('A' + i) + (j + 1);
            }
        }
    }

    private int pedirFila() {
        char ultima = (char) ('A' + filas - 1);
        while (true) {
            System.out.print(
                    "¿En qué fila desea estar? (A-" + ultima + "): "
            );
            String entrada = sc.nextLine().trim().toUpperCase();
            if (entrada.length() == 1 && entrada.charAt(0) >= 'A' && entrada.charAt(0) <= ultima) {
                return entrada.charAt(0) - 'A';
            }
            System.out.println("Fila inválida. Ingrese una letra entre A y " + ultima );
        }
    }

    // Dibuja la sala: pantalla arriba, filas con letra y asientos numerados
    private void mostrarSala() {
        int ancho = columnas * 5 + 8;
        String titulo = "P A N T A L L A";
        int espacios = Math.max(0, (ancho - titulo.length()) / 2);

        System.out.println();
        System.out.println(" ".repeat(espacios) + titulo);
        System.out.println("=".repeat(ancho));
        System.out.println();

        for (int i = 0; i < filas; i++) {
            char letra = (char) ('A' + i);
            System.out.print(letra + "   ");
            // El asiento 1 queda a la derecha, igual que en la imagen
            for (int numero = columnas; numero >= 1; numero--) {
                if (ocupados[i][numero - 1]) {
                    System.out.print("[X] ");
                } else {
                    System.out.printf("[%2d] ", numero);
                }
            }
            System.out.println("  " + letra);
        }
    }

    private void seleccionarAsiento() {
        if (salaLlena()) {
            System.out.println("La sala está llena, no quedan asientos disponibles.");
            return;
        }
        mostrarSala();
        System.out.println();
        while (true) {
            int fila = pedirFila();
            int numero = pedirNumero("¿En qué asiento desea estar? (1-" + columnas + "): ", columnas,
                    "Asiento inválido. Ingrese un número entre 1 y " + columnas + ".");
            char letra = (char) ('A' + fila);
            if (ocupados[fila][numero - 1]) {
                System.out.println("El asiento " + letra + numero + " ya está ocupado. Elija otro.\n");
            } else {
                ocupados[fila][numero - 1] = true;
                System.out.println("\n¡Asiento " + letra + numero + " seleccionado con éxito!");
                mostrarSala();
                return;   // regresa al menú}
            }
        }
    }
    private boolean salaLlena() {
        for(int i = 0; i < filas; i++) {
            for(int j = 0; j < columnas; j++) {
                if(!ocupados[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}