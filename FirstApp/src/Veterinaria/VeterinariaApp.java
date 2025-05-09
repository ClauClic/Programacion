package Veterinaria;

import java.io.*;
import java.util.*;

public class VeterinariaApp {
    private static final String ARCHIVO = "mascotas.txt";
    private static ArrayList<Mascota> mascotas = new ArrayList<>();

    public static void main(String[] args) {
        cargarMascotas();

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ VETERINARIA ---");
            System.out.println("1. Ver mascotas");
            System.out.println("2. Agregar perro");
            System.out.println("3. Agregar gato");
            System.out.println("4. Guardar y salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt(); sc.nextLine();

            switch (opcion) {
                case 1 -> mostrarMascotas();
                case 2 -> agregarPerro(sc);
                case 3 -> agregarGato(sc);
                case 4 -> guardarMascotas();
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private static void cargarMascotas() {
        mascotas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split(";");
                String tipo = tokens[0];
                String nombre = tokens[1];
                int edad = Integer.parseInt(tokens[2]);

                if (tipo.equals("PERRO")) {
                    String raza = tokens[3];
                    mascotas.add(new Perro(nombre, edad, raza));
                } else if (tipo.equals("GATO")) {
                    boolean cazador = Boolean.parseBoolean(tokens[3]);
                    mascotas.add(new Gato(nombre, edad, cazador));
                }
            }
            System.out.println("Mascotas cargadas desde archivo.");
        } catch (IOException e) {
            System.out.println("Archivo no encontrado. Se comenzará con lista vacía.");
        }
    }

    private static void guardarMascotas() {
      /*  try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Mascota m : mascotas) {
                if (m instanceof Perro) {
                    pw.println(((Perro) m).toArchivo());
                } else if (m instanceof Gato) {
                    pw.println(((Gato) m).toArchivo());
                }
            }
            System.out.println("Mascotas guardadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar mascotas: " + e.getMessage());
        }
        */
    	try (FileWriter pw = new FileWriter(ARCHIVO)) {
    		for (Mascota m : mascotas) {
                if (m instanceof Perro) {
                	pw.write(((Perro) m).toArchivo());
                } else if (m instanceof Gato) {
                    pw.write(((Gato) m).toArchivo());
                }
            }
            System.out.println("Mascotas guardadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar mascotas: " + e.getMessage());
        }
    }

    private static void mostrarMascotas() {
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            for (Mascota m : mascotas) {
                System.out.println(m.getDescripcion());
            }
        }
    }

    private static void agregarPerro(Scanner sc) {
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Edad: "); int edad = sc.nextInt(); sc.nextLine();
        System.out.print("Raza: "); String raza = sc.nextLine();
        mascotas.add(new Perro(nom, edad, raza));
    }

    private static void agregarGato(Scanner sc) {
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Edad: "); int edad = sc.nextInt(); sc.nextLine();
        System.out.print("¿Es cazador? (true/false): "); boolean cazador = sc.nextBoolean(); sc.nextLine();
        mascotas.add(new Gato(nom, edad, cazador));
    }
}
