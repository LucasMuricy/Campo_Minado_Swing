package br.com.jogo.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;

	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		super();
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();

	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
		
	}

	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}

	private void notificarObservadores(Boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}

	public void abrir(int linha, int coluna) {

		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());

	}

	public void alteraMarcaco(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarmarcacao());
		;
	}

	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int c = 0; c < colunas; c++) {
				Campo campo = new Campo(i, c);
				campo.registrarObservador(this);
				campos.add(campo);

			}

		}

	}

	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}

	}

	private void sortearMinas() {
		int minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = (int) campos.stream().filter(minado).count();
		} while (minasArmadas < minas);
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	public void reniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	public int getColunas() {
		return colunas;
	}

	public int getLinhas() {
		return linhas;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if (evento == CampoEvento.explodir) {
			mostraMinas();
			notificarObservadores(false);
		} else if (objetivoAlcancado()) {
			notificarObservadores(true);

		}

	}

	private void mostraMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.filter(c -> !c.isMarcado())
		.forEach(c -> c.setAberto(true));

	}
}
