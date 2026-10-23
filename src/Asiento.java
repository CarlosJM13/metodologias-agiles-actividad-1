// Representa un asiento de la sala: su fila (letra), su número y si está ocupado o libre
public class Asiento {

    private final char fila;
    private final int numero;
    private boolean ocupado;

    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.ocupado = false;   // al crearse todos los asientos están libres
    }

    public char getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    // Identificador del asiento: A1, A2, B1
    public String getId() {
        return "" + fila + numero;
    }
}
