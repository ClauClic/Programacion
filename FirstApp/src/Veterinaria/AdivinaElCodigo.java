package Veterinaria;

	import java.util.Scanner;
	import java.util.Random;

	public class AdivinaElCodigo {

	    // Método para cambiar el color de la consola
	    public static void color(String color) {
	        switch (color.toLowerCase()) {
	            case "rojo":
	                System.out.print("\u001B[31m"); // Rojo
	                break;
	            case "verde":
	                System.out.print("\u001B[32m"); // Verde
	                break;
	            case "amarillo":
	                System.out.print("\u001B[33m"); // Amarillo
	                break;
	            case "azul":
	                System.out.print("\u001B[34m"); // Azul
	                break;
	            case "reset":
	                System.out.print("\u001B[0m"); // Resetear color
	                break;
	            default:
	                System.out.print("\u001B[37m"); // Blanco por defecto
	                break;
	        }
	    }

	    // Método para imprimir una línea decorativa
	    public static void lineaDecorativa() {
	        color("amarillo");
	        System.out.println("===========================================================");
	        color("reset");
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        Random random = new Random();

	        // Mensaje de bienvenida
	        lineaDecorativa();
	        color("azul");
	        System.out.println("¡Bienvenido a 'Adivina el Código'!");
	        color("reset");
	        lineaDecorativa();
	        
	        // Explicación de las reglas
	        color("amarillo");
	        System.out.println("Las reglas del juego son las siguientes:");
	        color("verde");
	        System.out.println("- El código secreto es un número de 4 dígitos.");
	        color("amarillo");
	        System.out.println("- Tienes 10 intentos para adivinarlo.");
	        color("verde");
	        System.out.println("- Después de cada intento, se te dirá si el código es mayor o menor.");
	        color("amarillo");
	        System.out.println("- Si adivinas el código, ¡ganas! Si no, el juego termina.");
	        color("reset");
	        
	        // Esperar a que el jugador presione Enter para empezar
	        color("azul");
	        System.out.println("\nPresiona Enter para comenzar...");
	        color("reset");
	        scanner.nextLine();

	        // Generar un código secreto aleatorio
	        int codigoSecreto = random.nextInt(9000) + 1000;
	        int intentosRestantes = 10;
	        int intento;
	        
	        // Juego
	        while (intentosRestantes > 0) {
	            lineaDecorativa();
	            color("amarillo");
	            System.out.println("Tienes " + intentosRestantes + " intentos restantes.");
	            color("reset");

	            color("amarillo");
	            System.out.print("Introduce tu intento (un número de 4 dígitos): ");
	            color("reset");

	            try {
	                intento = Integer.parseInt(scanner.nextLine());

	                if (intento == codigoSecreto) {
	                    color("verde");
	                    System.out.println("¡Felicidades! Has adivinado el código.");
	                    color("reset");
	                    break;
	                } else {
	                    intentosRestantes--;
	                    if (intento < codigoSecreto) {
	                        color("rojo");
	                        System.out.println("El código secreto es mayor.");
	                    } else {
	                        color("rojo");
	                        System.out.println("El código secreto es menor.");
	                    }
	                    color("reset");
	                }

	                if (intentosRestantes == 0) {
	                    color("rojo");
	                    System.out.println("¡Lo siento, has perdido! El código secreto era: " + codigoSecreto);
	                    color("reset");
	                }
	            } catch (NumberFormatException ex) {
	                color("rojo");
	                System.out.println("Por favor, introduce un número válido.");
	                color("reset");
	            }
	        }

	        scanner.close();
	    }
	}
