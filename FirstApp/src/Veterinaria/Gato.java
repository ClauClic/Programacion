package Veterinaria;

public class Gato extends Mascota {
    private boolean esCazador;

    public Gato(String nombre, int edad, boolean esCazador) {
        super(nombre, edad);
        this.esCazador = esCazador;
    }

    public String getDescripcion() {
        return "Gato: " + nombre + (esCazador ? " (cazador)" : "") + ", " + edad + " años";
    }

    public String toArchivo() {
        return "GATO;" + nombre + ";" + edad + ";" + esCazador;
    }
}