import java.util.Scanner;

public class ConfiguracionInicialDeLaSala {

    private final Scanner sc = new Scanner(System.in);

    private int filas;
    private int columnas;
    private String[][] asientos;   // identificadores en memoria (A1, A2, ... B1, B2, ...)

    // Método que se llama desde el main
    public void iniciar() {
        // 1. Pedir el tamaño de la sala (con validación)
        filas = pedirNumero("¿Cuántas filas tendrá la sala? (máximo 26): ", 26,
                "Dimensiones inválidas. Ingrese números enteros mayores a cero (máximo 26 filas).");
        columnas = pedirNumero("¿Cuántos asientos por fila tendrá la sala?: ", Integer.MAX_VALUE,
                "Dimensiones inválidas. Ingrese números enteros mayores a cero.");

        // 2. Generar los identificadores automáticamente
        generarAsientos();

        // 3. Mostrar la sala
        mostrarSala();
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
                System.out.print(String.format("[%2d]", numero) + " ");
            }
            System.out.println("  " + letra);
        }
    }
}