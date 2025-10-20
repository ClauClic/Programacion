package varios;

import java.util.ArrayList;


public class Lista1 {
	
	  public static void main(String[] args) {
	    // Crea un array list
	    ArrayList<String> lenguajes = new ArrayList<>();
	    lenguajes.add("Java");
	    lenguajes.add("JavaScript");
	    lenguajes.add("Python");
	    System.out.println("ArrayList: " + lenguajes);

	    // loop para accederlo
	    System.out.println("ArrayList usando un for: ");

	    for(int i = 0; i < lenguajes.size(); i++) {
	      System.out.print(lenguajes.get(i));
	      System.out.print(", ");
	    }
	  }
	}