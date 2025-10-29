package webYsafe;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.stream.*;

/**
 * GUÍA COMPLETA DE java.nio (New I/O)
 * 
 * java.nio fue introducido en Java 1.4 y mejorado en Java 7 (NIO.2)
 * Ofrece mejor rendimiento, API más simple y más funcionalidades
 */

public class GuiaNio {

    // ============================================
    // 1. PATH - Representación de rutas
    // ============================================
    
    static class EjemploPath {
        public static void demostrar() {
            System.out.println("\n=== 1. TRABAJANDO CON PATH ===");
            
            // Crear Path de diferentes formas
            Path path1 = Paths.get("archivo.txt");
            Path path2 = Paths.get("carpeta", "subcarpeta", "archivo.txt");
            Path path3 = Path.of("archivo.txt"); // Java 11+
            
            System.out.println("Path absoluto: " + path1.toAbsolutePath());
            System.out.println("Nombre archivo: " + path1.getFileName());
            System.out.println("Directorio padre: " + path1.getParent());
            
            // Operaciones con rutas
            Path directorio = Paths.get("datos");
            Path archivo = directorio.resolve("ejemplo.txt"); // Combinar rutas
            System.out.println("Ruta combinada: " + archivo);
            
            // Path relativo
            Path base = Paths.get("/home/usuario");
            Path completo = Paths.get("/home/usuario/documentos/archivo.txt");
            Path relativo = base.relativize(completo);
            System.out.println("Path relativo: " + relativo);
        }
    }

    // ============================================
    // 2. FILES - Operaciones con archivos
    // ============================================
    
    static class EjemploFiles {
        public static void demostrar() throws IOException {
            System.out.println("\n=== 2. OPERACIONES CON FILES ===");
            
            Path archivo = Paths.get("prueba_nio.txt");
            
            // Escribir contenido (sobrescribe)
            Files.writeString(archivo, "Hola desde NIO!\n");
            System.out.println("Archivo escrito");
            
            // Leer contenido completo
            String contenido = Files.readString(archivo);
            System.out.println("Contenido: " + contenido);
            
            // Verificar existencia
            System.out.println("¿Existe? " + Files.exists(archivo));
            System.out.println("¿Es archivo? " + Files.isRegularFile(archivo));
            System.out.println("¿Es directorio? " + Files.isDirectory(archivo));
            
            // Información del archivo
            System.out.println("Tamaño: " + Files.size(archivo) + " bytes");
            System.out.println("Última modificación: " + Files.getLastModifiedTime(archivo));
            
            // Copiar archivo
            Path copia = Paths.get("copia_prueba.txt");
            Files.copy(archivo, copia, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo copiado");
            
            // Mover/renombrar
            Path nuevo = Paths.get("archivo_movido.txt");
            Files.move(copia, nuevo, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo movido");
            
            // Eliminar archivos
            Files.deleteIfExists(nuevo);
            Files.deleteIfExists(archivo);
            System.out.println("Archivos eliminados");
        }
    }

    // ============================================
    // 3. LECTURA Y ESCRITURA DE LÍNEAS
    // ============================================
    
    static class EjemploLineas {
        public static void demostrar() throws IOException {
            System.out.println("\n=== 3. TRABAJANDO CON LÍNEAS ===");
            
            Path archivo = Paths.get("lineas.txt");
            
            // Escribir lista de líneas
            List<String> lineas = Arrays.asList(
                "Primera línea",
                "Segunda línea",
                "Tercera línea"
            );
            Files.write(archivo, lineas);
            System.out.println("Líneas escritas");
            
            // Leer todas las líneas
            List<String> lineasLeidas = Files.readAllLines(archivo);
            System.out.println("Líneas leídas: " + lineasLeidas);
            
            // Agregar líneas (append)
            Files.write(archivo, 
                       Arrays.asList("Cuarta línea", "Quinta línea"),
                       StandardOpenOption.APPEND);
            
            // Leer línea por línea con Stream (eficiente para archivos grandes)
            System.out.println("\nLeyendo con Stream:");
            try (Stream<String> stream = Files.lines(archivo)) {
                stream.forEach(linea -> System.out.println("  - " + linea));
            }
            
            // Procesar y filtrar líneas
            System.out.println("\nLíneas que contienen 'línea':");
            try (Stream<String> stream = Files.lines(archivo)) {
                stream.filter(l -> l.toLowerCase().contains("línea"))
                      .forEach(System.out::println);
            }
            
            Files.delete(archivo);
        }
    }

    // ============================================
    // 4. TRABAJAR CON BYTES
    // ============================================
    
    static class EjemploBytes {
        public static void demostrar() throws IOException {
            System.out.println("\n=== 4. TRABAJANDO CON BYTES ===");
            
            Path archivo = Paths.get("datos.bin");
            
            // Escribir bytes
            byte[] datos = {10, 20, 30, 40, 50};
            Files.write(archivo, datos);
            System.out.println("Bytes escritos");
            
            // Leer bytes
            byte[] datosLeidos = Files.readAllBytes(archivo);
            System.out.println("Bytes leídos: " + Arrays.toString(datosLeidos));
            
            // Escribir con OutputStream
            try (OutputStream out = Files.newOutputStream(archivo)) {
                out.write(new byte[]{60, 70, 80});
            }
            
            // Leer con InputStream
            try (InputStream in = Files.newInputStream(archivo)) {
                int b;
                System.out.print("Lectura con stream: ");
                while ((b = in.read()) != -1) {
                    System.out.print(b + " ");
                }
                System.out.println();
            }
            
            Files.delete(archivo);
        }
    }

    // ============================================
    // 5. DIRECTORIOS Y LISTADO
    // ============================================
    
    static class EjemploDirectorios {
        public static void demostrar() throws IOException {
            System.out.println("\n=== 5. TRABAJANDO CON DIRECTORIOS ===");
            
            // Crear directorios
            Path dir = Paths.get("test_dir");
            Path subdir = Paths.get("test_dir/sub1/sub2");
            
            Files.createDirectories(subdir); // Crea toda la estructura
            System.out.println("Directorios creados");
            
            // Crear archivos de prueba
            Files.writeString(dir.resolve("archivo1.txt"), "Contenido 1");
            Files.writeString(dir.resolve("archivo2.txt"), "Contenido 2");
            Files.writeString(subdir.resolve("archivo3.txt"), "Contenido 3");
            
            // Listar contenido del directorio
            System.out.println("\nContenido de test_dir:");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path entrada : stream) {
                    System.out.println("  " + entrada.getFileName());
                }
            }
            
            // Listar con filtro
            System.out.println("\nSolo archivos .txt:");
            try (DirectoryStream<Path> stream = 
                    Files.newDirectoryStream(dir, "*.txt")) {
                for (Path entrada : stream) {
                    System.out.println("  " + entrada.getFileName());
                }
            }
            
            // Recorrer árbol de directorios (Java 8+)
            System.out.println("\nTodo el árbol:");
            try (Stream<Path> stream = Files.walk(dir)) {
                stream.forEach(p -> System.out.println("  " + p));
            }
            
            // Buscar archivos
            System.out.println("\nBuscar archivos .txt:");
            try (Stream<Path> stream = Files.find(dir, 10,
                    (path, attrs) -> path.toString().endsWith(".txt"))) {
                stream.forEach(p -> System.out.println("  " + p));
            }
            
            // Eliminar todo el directorio
            eliminarDirectorio(dir);
            System.out.println("\nDirectorio eliminado");
        }
        
        private static void eliminarDirectorio(Path dir) throws IOException {
            if (Files.exists(dir)) {
                try (Stream<Path> stream = Files.walk(dir)) {
                    stream.sorted(Comparator.reverseOrder())
                          .forEach(path -> {
                              try {
                                  Files.delete(path);
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }
                          });
                }
            }
        }
    }

    // ============================================
    // 6. ATRIBUTOS DE ARCHIVOS
    // ============================================
    
    static class EjemploAtributos {
        public static void demostrar() throws IOException {
            System.out.println("\n=== 6. ATRIBUTOS DE ARCHIVOS ===");
            
            Path archivo = Paths.get("test_atributos.txt");
            Files.writeString(archivo, "Archivo de prueba");
            
            // Atributos básicos
            BasicFileAttributes attrs = Files.readAttributes(archivo, 
                                                            BasicFileAttributes.class);
            System.out.println("Tamaño: " + attrs.size());
            System.out.println("Creación: " + attrs.creationTime());
            System.out.println("Última modificación: " + attrs.lastModifiedTime());
            System.out.println("Último acceso: " + attrs.lastAccessTime());
            System.out.println("¿Es directorio? " + attrs.isDirectory());
            System.out.println("¿Es archivo regular? " + attrs.isRegularFile());
            
            // Modificar atributos
            FileTime nuevoTiempo = FileTime.fromMillis(System.currentTimeMillis());
            Files.setLastModifiedTime(archivo, nuevoTiempo);
            
            // Permisos (en sistemas Unix/Linux)
            if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                System.getProperty("os.name").toLowerCase().contains("nux")) {
                Set<PosixFilePermission> permisos = 
                    Files.getPosixFilePermissions(archivo);
                System.out.println("Permisos: " + 
                    PosixFilePermissions.toString(permisos));
            }
            
            Files.delete(archivo);
        }
    }

    // ============================================
    // 7. COPIAR Y MOVER CON OPCIONES
    // ============================================
    
    static class EjemploCopiarMover {
        public static void demostrar() throws IOException {
            System.out.println("\n=== 7. COPIAR Y MOVER ARCHIVOS ===");
            
            Path origen = Paths.get("origen.txt");
            Files.writeString(origen, "Archivo original");
            
            // Copiar con opciones
            Path destino1 = Paths.get("copia1.txt");
            Files.copy(origen, destino1, 
                      StandardCopyOption.REPLACE_EXISTING,
                      StandardCopyOption.COPY_ATTRIBUTES);
            System.out.println("Copia creada");
            
            // Mover archivo
            Path destino2 = Paths.get("movido.txt");
            Files.move(destino1, destino2,
                      StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo movido");
            
            // Copiar desde InputStream
            Path destino3 = Paths.get("desde_stream.txt");
            try (InputStream in = new ByteArrayInputStream("Desde stream".getBytes())) {
                Files.copy(in, destino3, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // Copiar a OutputStream
            try (OutputStream out = new FileOutputStream("hacia_stream.txt")) {
                Files.copy(origen, out);
            }
            
            // Limpiar
            Files.delete(origen);
            Files.delete(destino2);
            Files.delete(destino3);
            Files.delete(Paths.get("hacia_stream.txt"));
        }
    }

    // ============================================
    // MAIN - EJECUTAR TODOS LOS EJEMPLOS
    // ============================================
    
    public static void main(String[] args) {
        try {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║  GUÍA COMPLETA DE java.nio            ║");
            System.out.println("╚════════════════════════════════════════╝");
            
            EjemploPath.demostrar();
            EjemploFiles.demostrar();
            EjemploLineas.demostrar();
            EjemploBytes.demostrar();
            EjemploDirectorios.demostrar();
            EjemploAtributos.demostrar();
            EjemploCopiarMover.demostrar();
            
            System.out.println("\n✅ Todos los ejemplos ejecutados exitosamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}