// Clase principal que gestiona el menú y la interacción con el usuario

import java.util.Scanner;

public class SistemaBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Agregamos algunas publicaciones de ejemplo
        biblioteca.agregarPublicacion(new Libro("001", "Cien Años de Soledad", "Gabriel García Márquez", false, true, 0));
        biblioteca.agregarPublicacion(new Revista("002", "National Geographic", "NG Publications", true, 0));

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nMenú:");
            System.out.println("1. Ver libros disponibles");
            System.out.println("2. Prestar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Ver libros prestados");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();

            // Manejo del menú basado en la opción del usuario
            switch (opcion) {
                case 1:
                    biblioteca.mostrarDisponibles();
                    break;
                case 2:
                    System.out.print("Código del libro/revista a prestar: ");
                    String codPrestar = scanner.next();
                    System.out.print("Días de préstamo: ");
                    int dias = scanner.nextInt();
                    biblioteca.prestarPublicacion(codPrestar, dias);
                    break;
                case 3:
                    System.out.print("Código del libro/revista a devolver: ");
                    String codDevolver = scanner.next();
                    biblioteca.devolverPublicacion(codDevolver);
                    break;
                case 4:
                    biblioteca.mostrarPrestados();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}