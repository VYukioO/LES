package br.edu.fatec.les.command;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.EntidadeDominio;
import br.edu.fatec.les.facade.Resultado;

public class SalvarCommand extends AbstractCommand {

	@Override
	public Resultado execute(AEntidade aEntidade) {
		return facade.salvar((EntidadeDominio) aEntidade);
	}

}
