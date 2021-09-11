package br.edu.fatec.les.dominio.modelo;

import java.time.LocalDate;
import java.util.List;

import br.edu.fatec.les.dominio.EntidadeNome;
import br.edu.fatec.les.dominio.enums.Generos;

public class Cliente extends EntidadeNome {
	
	private Usuario usuario;
	private String cpf;
	private LocalDate dtNascimento;
	private Generos genero;
	private Telefone telefone;
	private List<Endereco> enderecos;
	private List<Cartao> cartoes;
	private List<Cupom> cupons;

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDate getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public Generos getGenero() {
		return genero;
	}
	public void setGenero(Generos genero) {
		this.genero = genero;
	}
	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public List<Cartao> getCartoes() {
		return cartoes;
	}
	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}
	public List<Cupom> getCupons() {
		return cupons;
	}
	public void setCupons(List<Cupom> cupons) {
		this.cupons = cupons;
	}
	
	
}
