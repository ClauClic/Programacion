package main;

public class Perro {
	String nombre;
	int edad;
	
	public Perro (String name, int age) {
		this.nombre = name;
		this.edad = age;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	
}
