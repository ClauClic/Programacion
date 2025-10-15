package parcial;

public class Pingpong extends Deportes {
	int tiemposMesa ;

	public Pingpong(String disciplina, int jugadores, String puntaje, int tiempos, int tiemposMesa) {
		super(disciplina, jugadores, puntaje, tiempos);
		this.tiemposMesa = tiemposMesa;
	}

	public int getTiemposMesa() {
		return tiemposMesa;
	}

	public void setTiemposMesa(int tiemposMesa) {
		this.tiemposMesa = tiemposMesa;
	}
	

}
