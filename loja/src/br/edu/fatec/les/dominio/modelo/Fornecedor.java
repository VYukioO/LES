package br.edu.fatec.les.dominio.modelo;

import br.edu.fatec.les.dominio.EntidadeNome;

public class Fornecedor extends EntidadeNome {
	private String cnpj;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
