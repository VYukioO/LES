package br.edu.fatec.les.dominio;

import java.time.LocalDateTime;

public class EntidadeDominio extends AEntidade{
	private boolean ativo;
	private LocalDateTime dtCadastro;
	private LocalDateTime dtAtualizacao;
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public LocalDateTime getDtCadastro() {
		return dtCadastro;
	}
	
	public void setDtCadastro(LocalDateTime dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	
	public LocalDateTime getDtAtualizacao() {
		return dtAtualizacao;
	}
	
	public void setDtAtualizacao(LocalDateTime dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}
	

}
