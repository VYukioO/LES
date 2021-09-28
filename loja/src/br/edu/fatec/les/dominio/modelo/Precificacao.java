package br.edu.fatec.les.dominio.modelo;

import br.edu.fatec.les.dominio.EntidadeNome;

public class Precificacao extends EntidadeNome{
	private float percentual;

	public float getPercentual() {
		return percentual;
	}

	public void setPercentual(float percentual) {
		this.percentual = percentual;
	}
	
}
