package br.edu.fatec.les.dominio.modelo;

import br.edu.fatec.les.dominio.EntidadeDominio;
import br.edu.fatec.les.dominio.EntidadeNome;

public class Imagem extends EntidadeNome{
	private String descricao;
	private String caminho;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
}
