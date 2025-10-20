package varios;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EjemploHashM {
	   public static void main(String[] args) {
	      HashMap<String, String> lenguajes = new HashMap();
	      lenguajes.put("Java", "Enterprise");
	      lenguajes.put("Python", "ML/AI");
	      lenguajes.put("JavaScript", "Frontend");
	      System.out.println("HashMap: " + lenguajes);
	      System.out.print("Entradas: ");
	      Iterator var3 = lenguajes.entrySet().iterator();

	      while(var3.hasNext()) {
	         Map.Entry<String, String> entra = (Map.Entry)var3.next();
	         System.out.print(entra);
	         System.out.print(", ");
	      }

	      System.out.print("\nKeys: ");
	      var3 = lenguajes.keySet().iterator();

	      String valor;
	      while(var3.hasNext()) {
	         valor = (String)var3.next();
	         System.out.print(valor);
	         System.out.print(", ");
	      }

	      System.out.print("\nValores: ");
	      var3 = lenguajes.values().iterator();

	      while(var3.hasNext()) {
	         valor = (String)var3.next();
	         System.out.print(valor);
	         System.out.print(", ");
	      }

	   }
	}
