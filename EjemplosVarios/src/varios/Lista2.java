package varios;

import java.util.ArrayList;

public class Lista2 {
	
	  public static void main(String[] args) {
	    // Crea un array list
	    ArrayList<String> lenguaje = new ArrayList<>();
	    lenguaje.add("Java");
	    lenguaje.add("JavaScript");
	    lenguaje.add("Python");
	    System.out.println("ArrayList: " + lenguaje);

	        // Usandop forEach
	    System.out.println("Recorriendo el ArrayList con for-each:");
	    for(String leng : lenguaje) {
	      System.out.print(leng);
	      System.out.print(", ");
	    }
	  }
	}