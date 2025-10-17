import java.util.ArrayList;
import java.util.Scanner;

public class GestionNotas {

    // Lista estática compartida entre todos los métodos
    private static ArrayList<Estudiante> lista = new ArrayList<>();

    // Método para registrar estudiantes
    public static void registrarEstudiante(Scanner sc) {
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = sc.nextLine();

        System.out.print("¿Cuántas notas desea ingresar? ");
        int n = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        double suma = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("Nota " + (i + 1) + ": ");
            double nota = sc.nextDouble();
            suma += nota;
        }
        sc.nextLine(); // limpiar buffer
        double promedio = suma / n;

        Estudiante est = new Estudiante(nombre, promedio);
        lista.add(est);
        System.out.println("✅ Estudiante registrado con éxito.\n");
    }

    // Método para mostrar los estudiantes registrados
    public static void mostrarEstudiantes() {
        if (lista.isEmpty()) {
            System.out.println("⚠️ No hay estudiantes registrados.\n");
        } else {
            System.out.println("\n📋 Lista de estudiantes:");
            for (Estudiante e : lista) {
                System.out.println(e);
            }
            System.out.println();
        }
    }

    // Método principal (menú)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Registrar nuevo estudiante");
            System.out.println("2. Mostrar lista de estudiantes");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    registrarEstudiante(sc);
                    break;
                case 2:
                    mostrarEstudiantes();
                    break;
                case 3:
                    System.out.println("👋 Saliendo del programa...");
                    break;
                default:
                    System.out.println("❌ Opción no válida.\n");
            }
        } while (opcion != 3);

        sc.close();
    }
}
