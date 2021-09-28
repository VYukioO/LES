package br.edu.fatec.les.dominio.modelo;

import java.util.List;

import br.edu.fatec.les.dominio.EntidadeDominio;

public class Estoque extends EntidadeDominio{
	private Produto produto;
	private int qtdeTotal;
	private List<EstoqueItem> estoqueItem;
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQtdeTotal() {
		return qtdeTotal;
	}
	public void setQtdeTotal(int qtdeTotal) {
		this.qtdeTotal = qtdeTotal;
	}
	public List<EstoqueItem> getEstoqueItem() {
		return estoqueItem;
	}
	public void setEstoqueItem(List<EstoqueItem> estoqueItem) {
		this.estoqueItem = estoqueItem;
	}
	
	
}
