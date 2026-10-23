// Coordina el programa y pide el tamaño de la sala y muestra el menú y gestiona la selección de asientos
public class ConfiguracionInicialDeLaSala {

    private final LectorConsola lector = new LectorConsola();
    private final VistaSala vista = new VistaSala();
    private Sala sala;

    // Método que se llama desde el main
    public void iniciar() {
        // 1. Pedir el tamaño de la sala con la validación
        int filas = lector.pedirNumero("¿Cuántas filas tendrá la sala? (máximo 26): ", 26,
                "Dimensiones inválidas. Ingrese números enteros mayores a cero (máximo 26 filas).");
        int columnas = lector.pedirNumero("¿Cuántos asientos por fila tendrá la sala?: ", Integer.MAX_VALUE,
                "Dimensiones inválidas. Ingrese números enteros mayores a cero.");

        // 2. Crear la sala y genera los asientos y estan todos libres al inicio
        sala = new Sala(filas, columnas);

        // 3. Mostrar la sala
        vista.mostrarSala(sala);

        // 4. Menú
        int opcion = 0;
        while (opcion != 3) {
            vista.mostrarMenu();
            opcion = lector.pedirNumero("Elija una opción: ", 3, "Opción inválida. Elija 1, 2 o 3.");

            if (opcion == 1) {
                seleccionarAsiento();
            } else if (opcion == 2) {
                vista.mostrarSala(sala);
            }
        }
        System.out.println("¡Vuelve pronto a JoPo's Juan cinema!");
    }

    // Pide fila y asiento si el asiento ya está ocupado no lo deja seleccionarlo
    private void seleccionarAsiento() {
        if (sala.estaLlena()) {
            System.out.println("La sala está llena, no quedan asientos disponibles.");
            return;
        }

        vista.mostrarSala(sala);
        System.out.println();

        while (true) {
            int fila = lector.pedirFila(sala.getFilas());
            int numero = lector.pedirNumero("¿En qué asiento desea estar? (1-" + sala.getColumnas() + "): ",
                    sala.getColumnas(),
                    "Asiento inválido. Ingrese un número entre 1 y " + sala.getColumnas() + ".");

            Asiento asiento = sala.buscarAsiento(fila, numero);

            if (asiento.isOcupado()) {
                System.out.println("El asiento " + asiento.getId() + " ya está ocupado. Elija otro.\n");
            } else {
                asiento.setOcupado(true);
                System.out.println("\n¡Asiento " + asiento.getId() + " seleccionado con éxito!");
                vista.mostrarSala(sala);
                return;   // regresa al menú
            }
        }
    }
}