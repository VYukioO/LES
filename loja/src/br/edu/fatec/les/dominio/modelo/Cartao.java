package br.edu.fatec.les.dominio.modelo;

import java.time.LocalDate;

import br.edu.fatec.les.dominio.EntidadeNome;

public class Cartao extends EntidadeNome{
	private String numero;
	private String nomeImpresso;
	private LocalDate validade;
	private String bandeira;
	private int cvc;
	private boolean favorito;
	private Cliente cliente;

	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getNomeImpresso() {
		return nomeImpresso;
	}
	
	public void setNomeImpresso(String nomeImpresso) {
		this.nomeImpresso = nomeImpresso;
	}
	
	public LocalDate getValidade() {
		return validade;
	}
	
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	
	public String getBandeira() {
		return bandeira;
	}
	
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	
	public int getCvc() {
		return cvc;
	}
	
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	
	public boolean isFavorito() {
		return favorito;
	}
	
	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
