package parcial;

public class Deportes {
	String disciplina;
	int jugadores;
	String puntaje;
	int tiempos;

	public Deportes(String disciplina, int jugadores, String puntaje, int tiempos) {
		this.disciplina = disciplina;
		this.jugadores = jugadores;
		this.puntaje = puntaje;
		this.tiempos = tiempos;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public int getJugadores() {
		return jugadores;
	}

	public void setJugadores(int jugadores) {
		this.jugadores = jugadores;
	}

	public String getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}

	public int getTiempos() {
		return tiempos;
	}

	public void setTiempos(int tiempos) {
		this.tiempos = tiempos;
	}


}
