package JAVA;

import javax.swing.*;
import java.awt.event.*;

public class VentanaProducto {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Registro de Producto");

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(15);

        JLabel lblPrecio = new JLabel("Precio:");
        JTextField txtPrecio = new JTextField(10);

        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                double precio;

                try {
                    precio = Double.parseDouble(txtPrecio.getText());
                    Producto prod = new Producto(nombre, precio);
                    JOptionPane.showMessageDialog(frame, prod.mostrarInfo(), "Producto Registrado", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Precio inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblPrecio);
        panel.add(txtPrecio);
        panel.add(btnGuardar);

        frame.add(panel);
        frame.setSize(300, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}