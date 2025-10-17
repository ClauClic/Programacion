public class Estudiante extends Persona {
    private double promedio;

    public Estudiante(String nombre, double promedio) {
        super(nombre);
        this.promedio = promedio;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Promedio: " + promedio;
    }
}
