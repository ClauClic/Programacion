package parcial;

public class Futbol extends Deportes {
	int tiemposCancha;
	String posiciones;

	public Futbol(String disciplina, int jugadores, String puntaje, int tiempos, int tiemposCancha, String posiciones) {
		super(disciplina, jugadores, puntaje, tiempos);
		this.tiemposCancha = tiemposCancha;
		this.posiciones = posiciones;
	}
	public int getTiemposCancha() {
		return tiemposCancha;
	}
	public void setTiemposCancha(int tiemposCancha) {
		this.tiemposCancha = tiemposCancha;
	}
	public String getPosiciones() {
		return posiciones;
	}
	public void setPosiciones(String posiciones) {
		this.posiciones = posiciones;
	}
}
