// Coordina el programa y pide el tamaño de la sala y muestra el menú y gestiona la selección de asientos
import java.util.ArrayList;
import java.util.List;
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
        int disponibles = sala.cantidadAsientosLibres();

        vista.mostrarSala(sala);
        System.out.println("Asientos disponibles: " + disponibles);
        System.out.println("Escriba 0 para cancelar.");

        int cantidad = lector.pedirNumeroCancelable("¿Cuántos asientos desea comprar?: ", disponibles, "Cantidad inválida. Ingrese una cantidad entre 1 y " + disponibles + ".");
        if (cantidad == 0) {
            System.out.println("Compra cancelada.");
            return;
        }

        List<Asiento> seleccionados = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            while (true) {
                System.out.println("\nSeleccione el asiento " + (i + 1) + " de " + cantidad);
                int fila = lector.pedirFilaCancelable(sala.getFilas());
                if (fila == -1) {
                    System.out.println("Compra cancelada. No se reservó ningún asiento.");
                    return;
                }

                int numero = lector.pedirNumeroCancelable("Número de asiento (0 para cancelar): ", sala.getColumnas(), "Número inválido. Ingrese un número entre 1 y " + sala.getColumnas() + ".");
                if (numero == 0) {
                    System.out.println("Compra cancelada. No se reservó ningún asiento.");
                    return;
                }

                Asiento asiento = sala.buscarAsiento(fila, numero);
                if (asiento.isOcupado()) {
                    System.out.println("El asiento " + asiento.getId() + " ya está ocupado. Elija otro.");
                } else if (seleccionados.contains(asiento)) {
                    System.out.println("El asiento " + asiento.getId() + " ya fue seleccionado. Elija otro.");
                } else {
                    seleccionados.add(asiento);
                    System.out.println("Asiento " + asiento.getId() + " seleccionado.");
                    break;
                }
            }
        }

// Marcar los asientos seleccionados como ocupados
        for (Asiento asiento : seleccionados) {
            asiento.setOcupado(true);
        }
        for (Asiento asiento : seleccionados) {
            System.out.println("Asiento reservado: " + asiento.getId());
        }
        vista.mostrarSala(sala);
    }
}