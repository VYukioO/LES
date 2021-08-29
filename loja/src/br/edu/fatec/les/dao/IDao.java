package br.edu.fatec.les.dao;

import java.sql.SQLException;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.facade.Mensagem;

public interface IDao {
	public Mensagem salvar(AEntidade entidade) throws SQLException;
	public Mensagem atualizar(AEntidade entidade) throws SQLException;
	public Mensagem deletar(AEntidade entidade) throws SQLException;
	public List<AEntidade> consultar (AEntidade entidade) throws SQLException;

}
