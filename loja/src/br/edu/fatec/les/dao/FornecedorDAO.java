package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Fornecedor;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.util.ConnectionFactory;

public class FornecedorDAO implements IDao{
	
	private Connection conn = null;
	
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
		Fornecedor fornecedor = (Fornecedor) entidade;
		conn = ConnectionFactory.getConnection();
		List<AEntidade> fornecedores = new ArrayList<AEntidade>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "fnd_id, "
				+ "fnd_nome, "
				+ "fnd_cnpj, "
				+ "fnd_ativo, "
				+ "fnd_dt_cadastro, "
				+ "fnd_dt_atualizacao"
				+ "FROM tb_fornecedor "
				+ "WHERE fnd_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, fornecedor.getId());
			rs = pstm.executeQuery();
			
			Fornecedor fnd = new Fornecedor();
			
			while (rs.next()) {
				fnd = new Fornecedor();

				fnd.setId(rs.getLong("fnd_id"));
				fnd.setNome(rs.getString("fnd_nome"));
				fnd.setCnpj(rs.getString("fnd_cnpj"));
				fnd.setAtivo(rs.getBoolean("fnd_ativo"));
				fnd.setDtCadastro(rs.getObject("fnd_dt_cadastro", LocalDateTime.class));
				fnd.setDtAtualizacao(rs.getObject("fnd_dt_atualizacao", LocalDateTime.class));
				
				
				fornecedores.add(fnd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return fornecedores;
	}

}
