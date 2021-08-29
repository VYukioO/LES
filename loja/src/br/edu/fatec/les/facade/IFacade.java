package br.edu.fatec.les.facade;

import br.edu.fatec.les.dominio.EntidadeDominio;

public interface IFacade {
	public Resultado salvar(EntidadeDominio entidadeDominio);
	public Resultado atualizar(EntidadeDominio entidadeDominio);
	public Resultado deletar(EntidadeDominio entidadeDominio);
	public Resultado consultar(EntidadeDominio entidadeDominio);
}
