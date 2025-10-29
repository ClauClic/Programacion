package webYsafe;


import java.awt.BorderLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

// ----------- Clase Perro -------------
class Perro implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String pais;
	private String temperamento;
	private String urlImagen;

	public Perro(String nombre, String pais, String temperamento, String urlImagen) {
		this.nombre = nombre;
		this.pais = pais;
		this.temperamento = temperamento;
		this.urlImagen = urlImagen;
	}

	public String getNombre() { return nombre; }
	public String getPais() { return pais; }
	public String getTemperamento() { return temperamento; }
	public String getUrlImagen() { return urlImagen; }

	@Override
	public String toString() {
		return nombre + " (" + pais + ")";
	}
}

// ----------- Clase GestorPerros -------------
class GestorPerros {
	private static final String ARCHIVO = "perros.dat";

	public static void guardar(List<Perro> perros) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
			oos.writeObject(perros);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Perro> cargar() {
		File f = new File(ARCHIVO);
		if (!f.exists()) {
			return new ArrayList<>();
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
			return (List<Perro>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}

// ----------- Clase VentanaPrincipal -------------
public class VentanaPrincipal extends JFrame {
	private JTextField txtRaza;
	private JLabel lblInfo, lblImagen;
	private List<Perro> listaPerros = new ArrayList<>();

	public VentanaPrincipal() {
		super("Gestión de Razas de Perros 🐕");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(450, 550);
		setLayout(new BorderLayout());

		JPanel panelTop = new JPanel();
		panelTop.add(new JLabel("Raza:"));
		txtRaza = new JTextField(15);
		panelTop.add(txtRaza);
		JButton btnBuscar = new JButton("Buscar");
		panelTop.add(btnBuscar);

		lblInfo = new JLabel("<html><b>Info del perro</b></html>");
		lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelCentro = new JPanel(new BorderLayout());
		panelCentro.add(lblInfo, BorderLayout.NORTH);
		panelCentro.add(lblImagen, BorderLayout.CENTER);

		JButton btnGuardar = new JButton("Guardar raza");
		JButton btnVerGuardados = new JButton("Ver guardados");

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnGuardar);
		panelBotones.add(btnVerGuardados);

		add(panelTop, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);

		listaPerros = GestorPerros.cargar();

		btnBuscar.addActionListener(e -> buscarRaza());
		btnGuardar.addActionListener(e -> guardarRaza());
		btnVerGuardados.addActionListener(e -> mostrarGuardados());
	}

	private void buscarRaza() {
		try {
			String raza = txtRaza.getText().trim();
			if (raza.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ingrese una raza.");
				return;
			}

			String urlStr = "https://api.thedogapi.com/v1/breeds/search?q=" + URLEncoder.encode(raza, "UTF-8");
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = br.lines().reduce("", (a, b) -> a + b);
			br.close();

			// Si no hay resultados
			if (response.equals("[]")) {
				JOptionPane.showMessageDialog(this, "No se encontró la raza especificada.");
				return;
			}

			String nombre = extraer(response, "\"name\":\"", "\"");
			String pais = extraer(response, "\"origin\":\"", "\"");
			if (pais.isEmpty()) {
				pais = "Desconocido";
			}
			String temp = extraer(response, "\"temperament\":\"", "\"");
			String imgUrl = "https://cdn2.thedogapi.com/images/" + extraer(response, "\"reference_image_id\":\"", "\"") + ".jpg";
			String datos = "nombre=" +nombre+"\nPaís="+pais +"\ntemp="+temp+"\nimg="+imgUrl;

			Perro p = new Perro(nombre, pais, temp, imgUrl);
			System.out.println(datos);

			mostrarPerro(p);

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al consultar la API.");
		}
	}

	private String extraer(String texto, String ini, String fin) {
		int i = texto.indexOf(ini);
		if (i == -1) {
			return "";
		}
		int j = texto.indexOf(fin, i + ini.length());
		if (j == -1) {
			return "";
		}
		return texto.substring(i + ini.length(), j);
	}

	private void mostrarPerro(Perro p) {
		System.out.println("Mostrar Perro");
		lblInfo.setText("<html><h3>" + p.getNombre() + "</h3><p>Origen: " + p.getPais() +
				"<br>Temperamento: " + p.getTemperamento() + "</p></html>");
		try {
			ImageIcon icon = new ImageIcon(new URL(p.getUrlImagen()));
			Image img = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
			lblImagen.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			lblImagen.setIcon(null);
		}
		lblImagen.revalidate();
		lblImagen.repaint();
	}

	private void guardarRaza() {
		if (lblInfo.getText().equals("<html><b>Info del perro</b></html>")) {
			JOptionPane.showMessageDialog(this, "Primero consulte una raza.");
			return;
		}
		String nombre = extraer(lblInfo.getText(), "<h3>", "</h3>");
		for (Perro p : listaPerros) {
			if (p.getNombre().equalsIgnoreCase(nombre)) {
				JOptionPane.showMessageDialog(this, "Esa raza ya está guardada.");
				return;
			}
		}

		String pais = extraer(lblInfo.getText(), "Origen: ", "<br>");
		String temp = extraer(lblInfo.getText(), "Temperamento: ", "</p>");
		Icon icon = lblImagen.getIcon();
		String url = ""; // no guardamos imagen descargada, solo referencia
		Perro nuevo = new Perro(nombre, pais, temp, url);
		listaPerros.add(nuevo);
		try {
			GestorPerros.guardar(listaPerros);
			JOptionPane.showMessageDialog(this, "Raza guardada correctamente.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error al guardar archivo.");
		}
	}

	private void mostrarGuardados() {
		if (listaPerros.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay razas guardadas.");
			return;
		}
		StringBuilder sb = new StringBuilder("🐾 Razas guardadas:\n");
		for (Perro p : listaPerros) {
			sb.append("- ").append(p).append("\n");
		}
		JOptionPane.showMessageDialog(this, sb.toString());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
	}
}
