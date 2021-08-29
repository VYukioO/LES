package br.edu.fatec.les.command;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.facade.Resultado;

public interface ICommand {
	public Resultado execute(AEntidade aEntidade);

}
