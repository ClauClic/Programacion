package Veterinaria;

public class Perro extends Mascota {
    private String raza;

    public Perro(String nombre, int edad, String raza) {
        super(nombre, edad);
        this.raza = raza;
    }

    public String getDescripcion() {
        return "Perro: " + nombre + " (" + raza + "), " + edad + " años";
    }

    public String toArchivo() {
        return "PERRO;" + nombre + ";" + edad + ";" + raza;
    }
}
