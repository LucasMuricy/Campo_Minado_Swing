package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.jogo.modelo.Campo;
import br.com.jogo.modelo.CampoEvento;
import br.com.jogo.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

	private final Color BG_padrao = new Color(184, 184, 184);
	private final Color BG_marcar = new Color(8, 179, 247);
	private final Color BG_explodir = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);

	private Campo campo;

	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_padrao);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));

		addMouseListener(this);
		campo.registrarObservador(this);

	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {

		switch (evento) {
		case abrir:
			aplicarEstiloAbrir();
			break;
		case marca:
			aplicarEstiloMarca();
			break;
		case explodir:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadao();

		}

	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_explodir);
		setForeground(Color.WHITE);
		setText("X");

	}

	private void aplicarEstiloMarca() {
		setBackground(BG_marcar);
		setForeground(Color.BLACK);
		setText("M");
		

	}

	private void aplicarEstiloPadao() {
		setBackground(BG_padrao);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");

	}

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(campo.isMinado()) {
			setBackground(BG_explodir);
			return;
		}
		setBackground(BG_padrao);

		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;

		default:
			setForeground(Color.PINK);

		}
		
		String valor = !campo.vizinhancaSegura() ?
				campo.minasNaVizinhanca() + "" : "";
		setText(valor);
	}

	// Interface dos Eventos Do Mouse

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarmarcacao();
			;
		}

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}
