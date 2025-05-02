package JAVA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerJsonManual {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("persona.json"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Contenido: " + linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}