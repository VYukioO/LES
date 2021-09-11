package br.edu.fatec.les.dominio.modelo;

import java.time.LocalDate;
import java.util.List;

import br.edu.fatec.les.dominio.EntidadeNome;
import br.edu.fatec.les.dominio.enums.Generos;

public class Usuario extends EntidadeNome{
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
