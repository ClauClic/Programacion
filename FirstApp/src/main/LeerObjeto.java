package main;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LeerObjeto {
    public static void main(String[] args) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("persona.ser"))) {
            Persona p = (Persona) in.readObject();
            System.out.println("Leído: " + p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}