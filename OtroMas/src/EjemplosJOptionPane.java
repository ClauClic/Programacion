import javax.swing.*;

public class EjemplosJOptionPane {
    
    public static void main(String[] args) {
        
        // 1. showMessageDialog - Mostrar un mensaje simple
        JOptionPane.showMessageDialog(
            null, 
            "¡Bienvenido a Java!", 
            "Mensaje de Bienvenida", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Otros tipos de mensajes
        JOptionPane.showMessageDialog(
            null, 
            "Esta es una advertencia", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE
        );
        
        JOptionPane.showMessageDialog(
            null, 
            "¡Ha ocurrido un error!", 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
        
        
        // 2. showInputDialog - Solicitar entrada de datos
        String nombre = JOptionPane.showInputDialog(
            null, 
            "¿Cuál es tu nombre?", 
            "Entrada de datos", 
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (nombre != null && !nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                null, 
                "Hola " + nombre + "!"
            );
        }
        
        // Input con opciones predefinidas
        String[] opciones = {"Java", "Python", "C++", "JavaScript"};
        String lenguaje = (String) JOptionPane.showInputDialog(
            null,
            "Selecciona tu lenguaje favorito:",
            "Lenguaje de Programación",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (lenguaje != null) {
            JOptionPane.showMessageDialog(null, "Seleccionaste: " + lenguaje);
        }
        
        
        // 3. showConfirmDialog - Solicitar confirmación
        int respuesta = JOptionPane.showConfirmDialog(
            null, 
            "¿Deseas continuar?", 
            "Confirmación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Seleccionaste SÍ");
        } else if (respuesta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Seleccionaste NO");
        }
        
        // Confirm con tres opciones
        int opcion = JOptionPane.showConfirmDialog(
            null,
            "¿Quieres guardar los cambios?",
            "Guardar",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        switch (opcion) {
            case JOptionPane.YES_OPTION:
                JOptionPane.showMessageDialog(null, "Guardando...");
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Descartando cambios...");
                break;
            case JOptionPane.CANCEL_OPTION:
                JOptionPane.showMessageDialog(null, "Operación cancelada");
                break;
        }
        
        
        // 4. showOptionDialog - Cuadro de diálogo personalizado
        Object[] botonesPersonalizados = {"Aceptar", "Rechazar", "Más Info"};
        int seleccion = JOptionPane.showOptionDialog(
            null,
            "¿Aceptas los términos y condiciones?",
            "Términos de Servicio",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            botonesPersonalizados,
            botonesPersonalizados[0]
        );
        
        switch (seleccion) {
            case 0:
                JOptionPane.showMessageDialog(null, "Términos aceptados");
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Términos rechazados");
                break;
            case 2:
                JOptionPane.showMessageDialog(
                    null, 
                    "Aquí irían más detalles sobre los términos..."
                );
                break;
        }
        
        System.out.println("Programa finalizado");
    }
}