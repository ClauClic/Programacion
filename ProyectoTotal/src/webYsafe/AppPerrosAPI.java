package webYsafe;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

// Clase base
class Animal {
    protected String nombre;
    protected String tipo;

    public Animal(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
}

// Clase hija: Perro
class Perro extends Animal {
    private String temperamento;
    private String origen;
    private String esperanzaVida;
    private String urlImagen;

    public Perro(String nombre, String temperamento, String origen, String esperanzaVida, String urlImagen) {
        super(nombre, "Perro");
        this.temperamento = temperamento;
        this.origen = origen;
        this.esperanzaVida = esperanzaVida;
        this.urlImagen = urlImagen;
    }

    public String getNombre() { return nombre; }
    public String getTemperamento() { return temperamento; }
    public String getOrigen() { return origen; }
    public String getEsperanzaVida() { return esperanzaVida; }
    public String getUrlImagen() { return urlImagen; }

    @Override
    public String toString() {
        return nombre + ";" + temperamento + ";" + origen + ";" + esperanzaVida + ";" + urlImagen;
    }
}

// Gestor que maneja archivo y API
class GestorPerrosAPI {
    private final String archivo = "perros_api.txt";

    // Consulta API
    public Perro buscarRaza(String nombreRaza) {
        try {
            URL url = new URL("https://api.thedogapi.com/v1/breeds");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) json.append(line);
            br.close();

            JSONArray arr = new JSONArray(json.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.getString("name").toLowerCase();
                if (name.contains(nombreRaza.toLowerCase())) {
                    String temp = obj.optString("temperament", "Desconocido");
                    String origen = obj.optString("origin", "No especificado");
                    String vida = obj.optString("life_span", "N/A");
                    String img = obj.optJSONObject("image") != null ?
                            obj.getJSONObject("image").optString("url", "") : "";
                    return new Perro(obj.getString("name"), temp, origen, vida, img);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error consultando API: " + e.getMessage());
        }
        return null;
    }

    // Guardar localmente
    public void guardar(Perro p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(p.toString());
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error guardando archivo: " + e.getMessage());
        }
    }
}

// Programa principal
public class AppPerrosAPI {
    public static void main(String[] args) {
        GestorPerrosAPI gestor = new GestorPerrosAPI();

        while (true) {
            String raza = JOptionPane.showInputDialog(null, "Ingrese nombre de raza (o 'salir'):");
            if (raza == null || raza.equalsIgnoreCase("salir")) break;

            Perro p = gestor.buscarRaza(raza);
            if (p != null) {
                gestor.guardar(p);
                ImageIcon icon = null;
                try {
                    if (!p.getUrlImagen().isEmpty())
                        icon = new ImageIcon(new URL(p.getUrlImagen()));
                } catch (Exception ignored) {}

                JOptionPane.showMessageDialog(null,
                        "🐶 Raza: " + p.getNombre() +
                        "\nTemperamento: " + p.getTemperamento() +
                        "\nOrigen: " + p.getOrigen() +
                        "\nEsperanza de vida: " + p.getEsperanzaVida(),
                        "Información del perro",
                        JOptionPane.INFORMATION_MESSAGE,
                        icon);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró esa raza.");
            }
        }
    }
}
