import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lectura {
	    private double valor;
	    private LocalDateTime timestamp;
	    private String unidad;
	    
	    public Lectura(double valor, String unidad) {
	        this.valor = valor;
	        this.timestamp = LocalDateTime.now();
	        this.unidad = unidad;
	    }
	    
	    public boolean esValida() {
	        return !Double.isNaN(valor) && !Double.isInfinite(valor);
	    }
	    
	    public double getValor() { return valor; }
	    public LocalDateTime getTimestamp() { return timestamp; }
	    public String getUnidad() { return unidad; }
	    
	    @Override
	    public String toString() {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        return String.format("%.2f %s @ %s", valor, unidad, timestamp.format(formatter));
	    }
	}