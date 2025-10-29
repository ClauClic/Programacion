package webYsafe;


import java.io.*;
import java.nio.file.*;
import java.util.*;

// ============================================
// 1. ESCRITURA Y LECTURA DE TEXTO BÁSICO
// ============================================

class EjemploTextoBasico {

	// Escribir texto simple
	public static void escribirTexto(String archivo, String contenido) throws IOException {
		FileWriter writer = new FileWriter(archivo);
		writer.write(contenido);
		writer.close();
		System.out.println("Texto escrito en: " + archivo);
	}

	// Leer texto simple
	public static String leerTexto(String archivo) throws IOException {
		StringBuilder contenido = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		String linea;
		while ((linea = reader.readLine()) != null) {
			contenido.append(linea).append("\n");
		}
		reader.close();
		return contenido.toString();
	}

	// Agregar texto al final del archivo
	public static void agregarTexto(String archivo, String contenido) throws IOException {
		FileWriter writer = new FileWriter(archivo, true); // true = append mode
		writer.write(contenido);
		writer.close();
	}
}

// ============================================
// 2. ESCRITURA Y LECTURA CON BufferedWriter/Reader
// ============================================

class EjemploBuffered {

	public static void escribirLineas(String archivo, List<String> lineas) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
		for (String linea : lineas) {
			writer.write(linea);
			writer.newLine();
		}
		writer.close();
	}

	public static List<String> leerLineas(String archivo) throws IOException {
		List<String> lineas = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		String linea;
		while ((linea = reader.readLine()) != null) {
			lineas.add(linea);
		}
		reader.close();
		return lineas;
	}
}

// ============================================
// 3. ALMACENAMIENTO CON java.nio.file (Moderno)
// ============================================

class EjemploNIO {

	// Escribir todo el contenido de una vez
	public static void escribirTodo(String archivo, String contenido) throws IOException {
		Path path = Paths.get(archivo);
		Files.writeString(path, contenido);
	}

	// Leer todo el contenido de una vez
	public static String leerTodo(String archivo) throws IOException {
		Path path = Paths.get(archivo);
		return Files.readString(path);
	}

	// Escribir lista de líneas
	public static void escribirLineas(String archivo, List<String> lineas) throws IOException {
		Path path = Paths.get(archivo);
		Files.write(path, lineas);
	}

	// Leer todas las líneas
	public static List<String> leerLineas(String archivo) throws IOException {
		Path path = Paths.get(archivo);
		return Files.readAllLines(path);
	}
}

// ============================================
// 4. SERIALIZACIÓN DE OBJETOS
// ============================================

// Clase serializable
class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private int edad;
	private String email;

	public Persona(String nombre, int edad, String email) {
		this.nombre = nombre;
		this.edad = edad;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Persona{nombre='" + nombre + "', edad=" + edad + ", email='" + email + "'}";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}

class EjemploSerializacion {

	// Guardar objeto
	public static void guardarObjeto(String archivo, Object objeto) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
		oos.writeObject(objeto);
		oos.close();
		System.out.println("Objeto guardado en: " + archivo);
	}

	// Cargar objeto
	public static Object cargarObjeto(String archivo) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
		Object objeto = ois.readObject();
		ois.close();
		return objeto;
	}

	// Guardar lista de objetos
	public static void guardarLista(String archivo, List<Persona> lista) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
		oos.writeObject(lista);
		oos.close();
	}

	// Cargar lista de objetos
	@SuppressWarnings("unchecked")
	public static List<Persona> cargarLista(String archivo) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
		List<Persona> lista = (List<Persona>) ois.readObject();
		ois.close();
		return lista;
	}
}

// ============================================
// 5. ARCHIVOS CSV
// ============================================

class EjemploCSV {

	public static void escribirCSV(String archivo, List<Persona> personas) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
		// Escribir encabezado
		writer.write("Nombre,Edad,Email");
		writer.newLine();
		// Escribir datos
		for (Persona p : personas) {
			writer.write(p.getNombre() + "," + p.getEdad() + "," + p.getEmail());
			writer.newLine();
		}
		writer.close();
	}

	public static List<Persona> leerCSV(String archivo) throws IOException {
		List<Persona> personas = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		reader.readLine(); // Saltar encabezado
		String linea;
		while ((linea = reader.readLine()) != null) {
			String[] datos = linea.split(",");
			personas.add(new Persona(datos[0], Integer.parseInt(datos[1]), datos[2]));
		}
		reader.close();
		return personas;
	}
}

// ============================================
// CLASE PRINCIPAL CON EJEMPLOS
// ============================================

public class Archivos {

	public static void main(String[] args) {
		try {
			System.out.println("=== EJEMPLOS DE ALMACENAMIENTO EN ARCHIVOS ===\n");

			// 1. Texto básico
			System.out.println("1. Texto básico:");
			EjemploTextoBasico.escribirTexto("ejemplo1.txt", "Hola Mundo!\nEsta es la segunda línea.");
			String texto = EjemploTextoBasico.leerTexto("ejemplo1.txt");
			System.out.println("Contenido leído:\n" + texto);

			// 2. BufferedWriter/Reader
			System.out.println("\n2. Con BufferedWriter/Reader:");
			List<String> lineas = Arrays.asList("Línea 1", "Línea 2", "Línea 3");
			EjemploBuffered.escribirLineas("ejemplo2.txt", lineas);
			List<String> lineasLeidas = EjemploBuffered.leerLineas("ejemplo2.txt");
			System.out.println("Líneas leídas: " + lineasLeidas);

			// 3. NIO (Moderno)
			System.out.println("\n3. Con java.nio.file:");
			EjemploNIO.escribirTodo("ejemplo3.txt", "Contenido usando NIO\nMás fácil y moderno");
			String contenidoNIO = EjemploNIO.leerTodo("ejemplo3.txt");
			System.out.println("Contenido NIO:\n" + contenidoNIO);

			// 4. Serialización de objetos
			System.out.println("\n4. Serialización de objetos:");
			Persona persona = new Persona("Juan Pérez", 30, "juan@example.com");
			EjemploSerializacion.guardarObjeto("persona.ser", persona);
			Persona personaCargada = (Persona) EjemploSerializacion.cargarObjeto("persona.ser");
			System.out.println("Persona cargada: " + personaCargada);

			// 5. Lista de objetos
			System.out.println("\n5. Lista de objetos:");
			List<Persona> personas = Arrays.asList(
					new Persona("Ana García", 25, "ana@example.com"),
					new Persona("Carlos López", 35, "carlos@example.com")
					);
			EjemploSerializacion.guardarLista("personas.ser", personas);
			List<Persona> personasCargadas = EjemploSerializacion.cargarLista("personas.ser");
			System.out.println("Personas cargadas: " + personasCargadas);

			// 6. CSV
			System.out.println("\n6. Archivos CSV:");
			EjemploCSV.escribirCSV("personas.csv", personas);
			List<Persona> personasCSV = EjemploCSV.leerCSV("personas.csv");
			System.out.println("Personas desde CSV: " + personasCSV);

			System.out.println("\n¡Fin de todos los ejemplos!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}