package br.edu.fatec.les.dominio.modelo;

import br.edu.fatec.les.dominio.EntidadeDominio;

public class Usuario extends EntidadeDominio{
	private String email;
	private String senha;
	private boolean admin;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
