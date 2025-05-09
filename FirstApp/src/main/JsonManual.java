package main;
import java.io.FileWriter;
import java.io.IOException;

public class JsonManual {
    public static void main(String[] args) {
        String json = "{ \"nombre\": \"Carla\", \"edad\": 28 }";
        try (FileWriter writer = new FileWriter("persona.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}