package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Estoque;
import br.edu.fatec.les.dominio.modelo.EstoqueItem;
import br.edu.fatec.les.dominio.modelo.Fornecedor;
import br.edu.fatec.les.dominio.modelo.Produto;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.util.ConnectionFactory;

public class EstoqueItemDAO implements IDao{
	
	private Connection conn = null;
	FornecedorDAO fornecedorDao = new FornecedorDAO();

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação nao suportada.");
	}

	@Override
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação nao suportada.");
	}

	@Override
	public Mensagem deletar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação nao suportada.");
	}

	@Override
	public List<AEntidade> consultar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação nao suportada.");
	}

	public List<AEntidade> consultarItem(Long id) throws SQLException {
		conn = ConnectionFactory.getConnection();
		List<AEntidade> itens = new ArrayList<AEntidade>();
		List<EstoqueItem> estoqueItens = new ArrayList<EstoqueItem>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "sti_qtde, "
				+ "sti_dt_entrada, "
				+ "sti_custo, "
				+ "sti_fnd_id "
				+ "FROM tb_estoque_item "
				+ "WHERE sti_stq_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, id);
			rs = pstm.executeQuery();
			
			EstoqueItem sti = new EstoqueItem();
			Fornecedor fnd = new Fornecedor();
			Produto pro = new Produto();
			Estoque stq = new Estoque();
			
			while (rs.next()) {
				sti = new EstoqueItem();
				fnd = new Fornecedor();
				pro = new Produto();
				stq = new Estoque();
				
				sti.setQtde(rs.getInt("sti_qtde_total"));
				sti.setCusto(rs.getFloat("sti_custo"));
				
				fnd.setId(rs.getLong("sti_fnd_id"));
				sti.setFornecedor((Fornecedor) fornecedorDao.consultar(fnd).get(0));
				
				estoqueItens.add(sti);
			}
			
			stq.setEstoqueItem(estoqueItens);
			pro.setEstoque(stq);
			
			itens.add(pro);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		
		return itens;
	}
}
