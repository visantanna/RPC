import java.util.ArrayList;
import java.util.List;

public class Relatorio {
	private int numeroDaOperacao;
	private long media;
	private double desvioPadrao;
	private List<Long> execucoes = new ArrayList<Long>(); 
	
	public Relatorio(int numeroDaOperacao) {
		this.numeroDaOperacao = numeroDaOperacao;
	}
	
	public void addExecucao(long execucao) {
		execucoes.add(execucao);
	}

	public int getNumeroDaOperacao() {
		return numeroDaOperacao;
	}

	public void setNumeroDaOperacao(int numeroDaOperacao) {
		this.numeroDaOperacao = numeroDaOperacao;
	}

	public long getMedia() {
		return media;
	}

	public void setMedia(long media) {
		this.media = media;
	}

	public double getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(double desvioPadrao) {
		this.desvioPadrao = desvioPadrao;
	}

	public List<Long> getExecucoes() {
		return execucoes;
	}

	public void setExecucoes(List<Long> execucoes) {
		this.execucoes = execucoes;
	}
}
