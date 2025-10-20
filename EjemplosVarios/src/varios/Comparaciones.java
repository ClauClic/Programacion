package varios;


//comparando el uso de == y de equals
public class Comparaciones {
	public static void main(String[] args) {

		    String nombre1 = new String("Programacion de Computadores");
		    String nombre2 = new String("Programacion de Computadores");

		    System.out.println("Cuando se definen dos variables con el mismo contenido. Los dos Strings son iguales?");

		    // usando == 
		    boolean compara1 = (nombre1 == nombre2);
		    System.out.println("Usando == : " + compara1);

		    // usando el método equals() 
		    boolean compara2 = nombre1.equals(nombre2);
		    System.out.println("Usando equals(): " + compara2);
		  
		    // Ahora cuando se crea la variable a partir de otra
	  
	        String nombre3 = nombre1;
	        System.out.println();
		    System.out.println("Cuando una variable se define usando el =  Despues de copiar, son iguales?");

		    // usando == 
		    boolean compara1 = (nombre1 == nombre3);
		    System.out.println("Usando == da: " + compara1);

		    // usando el método equals() 
		    boolean compara2 = nombre1.equals(nombre3);
		    System.out.println("Usando equals(): " + compara2);
		}
}
