package br.edu.fatec.les.dominio.modelo;

import java.time.LocalDateTime;

import br.edu.fatec.les.dominio.EntidadeNome;

public class Cupom extends EntidadeNome {
	private float valor;
	private LocalDateTime dtVencimento;
	private boolean promocao;
	
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public LocalDateTime getDtVencimento() {
		return dtVencimento;
	}
	public void setDtVencimento(LocalDateTime dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	public boolean isPromocao() {
		return promocao;
	}
	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
	}

}
