package br.edu.fatec.les.facade;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;

public class Resultado {
	private ArrayList<Mensagem> msgs;
	private List<AEntidade> entidade;
	
	public ArrayList<Mensagem> getMsgs() {
		return msgs;
	}
	public void setMsgs(ArrayList<Mensagem> msgs) {
		this.msgs = msgs;
	}
	public List<AEntidade> getEntidade() {
		return entidade;
	}
	public void setEntidade(List<AEntidade> entidade) {
		this.entidade = entidade;
	}
	

}
