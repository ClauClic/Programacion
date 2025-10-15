import java.io.*;
import java.util.*;

// Clase abstracta que representa una publicación en la biblioteca
abstract class Publicacion {
    protected String codigo;
    protected String titulo;
    protected boolean disponible;
    protected int diasPrestado;

    // Constructor para inicializar una publicación con su código, título y estado
    public Publicacion(String codigo, String titulo, boolean disponible, int diasPrestado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.disponible = disponible;
        this.diasPrestado = diasPrestado;
    }

    // Método abstracto que cada tipo de publicación deberá implementar para mostrar su información
    public abstract void mostrarInfo();

    // Método para prestar una publicación por un número determinado de días
    public void prestar(int dias) {
        if (disponible) {
            disponible = false;
            diasPrestado = dias;
            System.out.println("Prestado por " + convertirNumeroATexto(dias) + " días.");
        } else {
            System.out.println("No disponible.");
        }
    }

    // Método para devolver una publicación y marcarla como disponible nuevamente
    public void devolver() {
        disponible = true;
        diasPrestado = 0;
        System.out.println("Libro/Revista devuelto.");
    }

    // Convierte un número a su representación en texto para mejorar la legibilidad en los reportes
    protected String convertirNumeroATexto(int numero) {
        String[] textoNumeros = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", 
                                 "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", 
                                 "dieciocho", "diecinueve", "veinte"};
        return (numero >= 0 && numero <= 20) ? textoNumeros[numero] : String.valueOf(numero);
    }
}

// Clase que representa un libro, extendiendo la funcionalidad de Publicacion
class Libro extends Publicacion {
    private String autor;
    private boolean esDigital;

    public Libro(String codigo, String titulo, String autor, boolean esDigital, boolean disponible, int diasPrestado) {
        super(codigo, titulo, disponible, diasPrestado);
        this.autor = autor;
        this.esDigital = esDigital;
    }

    // Implementación del método abstracto para mostrar la información específica de un libro
    @Override
    public void mostrarInfo() {
        System.out.println("Libro: " + titulo + " (" + codigo + ") - Autor: " + autor + 
                           " - " + (esDigital ? "Digital" : "Físico") + 
                           " - Estado: " + (disponible ? "Disponible" : "Prestado por " + convertirNumeroATexto(diasPrestado) + " días"));
    }
}

// Clase que representa una revista, extendiendo la funcionalidad de Publicacion
class Revista extends Publicacion {
    private String editorial;

    public Revista(String codigo, String titulo, String editorial, boolean disponible, int diasPrestado) {
        super(codigo, titulo, disponible, diasPrestado);
        this.editorial = editorial;
    }

    // Implementación del método abstracto para mostrar la información específica de una revista
    @Override
    public void mostrarInfo() {
        System.out.println("Revista: " + titulo + " (" + codigo + ") - Editorial: " + editorial +
                           " - Estado: " + (disponible ? "Disponible" : "Prestado por " + convertirNumeroATexto(diasPrestado) + " días"));
    }
}

// Clase que maneja la gestión de publicaciones en la biblioteca
class Biblioteca {
    private List<Publicacion> publicaciones = new ArrayList<>();

    // Método para agregar nuevas publicaciones a la biblioteca
    public void agregarPublicacion(Publicacion pub) {
        publicaciones.add(pub);
    }

    // Método para mostrar todas las publicaciones disponibles
    public void mostrarDisponibles() {
        System.out.println("\nPublicaciones disponibles:");
        for (Publicacion pub : publicaciones) {
            if (pub.disponible) pub.mostrarInfo();
        }
    }

    // Método para prestar una publicación si está disponible
    public void prestarPublicacion(String codigo, int dias) {
        for (Publicacion pub : publicaciones) {
            if (pub.codigo.equals(codigo)) {
                pub.prestar(dias);
                return;
            }
        }
        System.out.println("Publicación no encontrada.");
    }

    // Método para devolver una publicación prestada
    public void devolverPublicacion(String codigo) {
        for (Publicacion pub : publicaciones) {
            if (pub.codigo.equals(codigo)) {
                pub.devolver();
                return;
            }
        }
        System.out.println("Publicación no encontrada.");
    }

    // Método para mostrar las publicaciones que están actualmente prestadas
    public void mostrarPrestados() {
        System.out.println("\nPublicaciones prestadas:");
        for (Publicacion pub : publicaciones) {
            if (!pub.disponible) pub.mostrarInfo();
        }
    }
}