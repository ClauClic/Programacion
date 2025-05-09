package main;

import java.util.Scanner;

public class Lee {

	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion=0;
		
		while (opcion <= 4) {
			System.out.println("1. Nuevo Perro" +
								"\n 2. Nuevo Gato" +
								"\n 3. Guardar" +
								"\n 4. Salir");
			opcion = sc.nextInt();
			
			
			switch (opcion) {
			
			case 1:
				leerNuevoPerro();
				break;
				
			case 2:
				leerNuevoPerro();
				break;
			case 3:
				leerNuevoPerro();
				break;
			case 4:
				leerNuevoPerro();
				break;
				
			default:
				System.out.println("opción errada");
				opcion=0;
					
			}
		}
	}
// cargar archivo
	// leer nuevo gato
	//leer nuevo perro
	private static void leerNuevoPerro(){
		
	}
	//guardar 
}
