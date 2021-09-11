package br.edu.fatec.les.dominio.modelo;

import br.edu.fatec.les.dominio.EntidadeDominio;
import br.edu.fatec.les.dominio.enums.TipoTelefone;

public class Telefone extends EntidadeDominio{
	private TipoTelefone tipoTelefone;
	private int ddd;
	private String numero;
	private Cliente cliente;
	
	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}
	
	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}
	
	public int getDdd() {
		return ddd;
	}
	
	public void setDdd(int ddd) {
		this.ddd = ddd;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


}
