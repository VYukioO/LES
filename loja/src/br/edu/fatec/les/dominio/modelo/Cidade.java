package br.edu.fatec.les.dominio.modelo;

import br.edu.fatec.les.dominio.EntidadeNome;

public class Cidade extends EntidadeNome {
	private Estado estado;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	

}
