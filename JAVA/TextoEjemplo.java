package JAVA;

import java.io.FileWriter;
import java.io.IOException;

public class TextoEjemplo {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("saludo.txt")) {
            writer.write("Hola, mundo\n");
            writer.write("POO en Java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}