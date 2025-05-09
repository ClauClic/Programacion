package main;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GuardarObjeto {
    public static void main(String[] args) {
        Persona p = new Persona("Ana", 30);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("persona.ser"))) {
            out.writeObject(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}