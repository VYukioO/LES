package br.edu.fatec.les.dominio.modelo;

import java.time.LocalDate;

public class EstoqueItem {
	private int qtde;
	private LocalDate dtEntrada;
	private float custo;
	private Estoque estoque;
	private Fornecedor fornecedor;
	
	public int getQtde() {
		return qtde;
	}
	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	public LocalDate getDtEntrada() {
		return dtEntrada;
	}
	public void setDtEntrada(LocalDate dtEntrada) {
		this.dtEntrada = dtEntrada;
	}
	public float getCusto() {
		return custo;
	}
	public void setCusto(float custo) {
		this.custo = custo;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

}
