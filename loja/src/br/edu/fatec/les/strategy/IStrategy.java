package br.edu.fatec.les.strategy;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.facade.Mensagem;

public interface IStrategy {
	public Mensagem execute(AEntidade aEntidade);
}
