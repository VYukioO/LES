package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Categoria;
import br.edu.fatec.les.dominio.modelo.Produto;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.util.ConnectionFactory;

public class CategoriaDAO implements IDao{
	
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
		Produto produto = (Produto) entidade;
		conn = ConnectionFactory.getConnection();
		List<AEntidade> categorias = new ArrayList<AEntidade>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "cat_id, "
				+ "cat_nome, "
				+ "cat_ativo, "
				+ "cat_dt_cadastro, "
				+ "cat_dt_atualizacao "
				+ "FROM tb_categoria "
				+ "WHERE cat_ativo = 1 "
				+ "AND cat_pro_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, produto.getId());
			rs = pstm.executeQuery();
			
			Categoria cat = new Categoria();
			
			while (rs.next()) {
				cat = new Categoria();
				
				cat.setId(rs.getLong("cat_id"));
				cat.setNome(rs.getString("cat_nome"));
				cat.setAtivo(rs.getBoolean("cat_ativo"));
				cat.setDtCadastro(rs.getObject("cat_dt_cadastro", LocalDateTime.class));
				cat.setDtAtualizacao(rs.getObject("cat_dt_atualizacao", LocalDateTime.class));
				
				categorias.add(cat);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		
		return categorias;
	}

}
