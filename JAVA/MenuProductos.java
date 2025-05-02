package JAVA;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class MenuProductos {
    public static void main(String[] args) {
        ArrayList<Producto> productos = new ArrayList<>();

        String[] opciones = {"Ingresar producto", "Borrar producto", "Listar productos", "Salir"};
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción",
                "Menú de Productos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            switch (opcion) {
                case 0: // Ingresar
                    String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
                    if (nombre == null || nombre.length() == 0) break;

                    String precioStr = JOptionPane.showInputDialog("Ingrese el precio:");
                    try {
                        double precio = Double.parseDouble(precioStr);
                        productos.add(new Producto(nombre, precio));
                        JOptionPane.showMessageDialog(null, "Producto guardado.");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Precio inválido.");
                    }
                    break;

                case 1: // Borrar
                    String nombreABorrar = JOptionPane.showInputDialog("Ingrese el nombre del producto a borrar:");
                    if (nombreABorrar != null) {
                        boolean eliminado = productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombreABorrar));
                        if (eliminado) {
                            JOptionPane.showMessageDialog(null, "Producto eliminado.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                        }
                    }
                    break;

                case 2: // Listar
                    if (productos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay productos registrados.");
                    } else {
                        StringBuilder lista = new StringBuilder("Lista de productos:\n\n");
                        for (Producto p : productos) {
                            lista.append(p.mostrarInfo()).append("\n\n");
                        }
                        JOptionPane.showMessageDialog(null, lista.toString());
                    }
                    break;

                case 3: // Salir
                    JOptionPane.showMessageDialog(null, "¡Hasta pronto!");
                    break;
            }

        } while (opcion != 3);
    }
}