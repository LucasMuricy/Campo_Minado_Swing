package visao;

import javax.swing.JFrame;

import br.com.jogo.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 50);
		PainelTabuleiro paineltabuleiro = new PainelTabuleiro(tabuleiro);
		
		add(paineltabuleiro);
		
		setTitle("Campo Minado");
		setSize(690, 430);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
		
	}
	
	
	

}
