package br.edu.fatec.les.dominio.modelo;

import java.util.List;

import br.edu.fatec.les.dominio.EntidadeNome;

public class Produto extends EntidadeNome{
	private float preco;
	private String descricao;
	private Imagem imagem;
	private List<Categoria> categorias;
	private List<Modalidade> modalidades;
	private Precificacao precificacao;
	private Estoque estoque;
	
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Imagem getImagem() {
		return imagem;
	}
	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	public List<Modalidade> getModalidades() {
		return modalidades;
	}
	public void setModalidades(List<Modalidade> modalidades) {
		this.modalidades = modalidades;
	}
	public Precificacao getPrecificacao() {
		return precificacao;
	}
	public void setPrecificacao(Precificacao precificacao) {
		this.precificacao = precificacao;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
}
