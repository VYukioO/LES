package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Precificacao;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.util.ConnectionFactory;

public class PrecificacaoDAO implements IDao{
	
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
		Precificacao precificacao = (Precificacao) entidade;
		conn = ConnectionFactory.getConnection();
		List<AEntidade> precificacoes = new ArrayList<AEntidade>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "prc_id, "
				+ "prc_percentual, "
				+ "prc_ativo, "
				+ "prc_dt_cadastro, "
				+ "prc_dt_atualizacao "
				+ "FROM tb_precificacao "
				+ "WHERE prc_ativo = 1 "
				+ "AND prc_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, precificacao.getId());
			rs = pstm.executeQuery();
			
			Precificacao prc = new Precificacao();
			while (rs.next()) {
				prc = new Precificacao();
				
				prc.setId(rs.getLong("prc_id"));
				prc.setPercentual(rs.getFloat("prc_percentual"));
				prc.setAtivo(rs.getBoolean("prc_ativo"));
				prc.setDtCadastro(rs.getObject("prc_dt_cadastro", LocalDateTime.class));
				prc.setDtAtualizacao(rs.getObject("prc_dt_atualizacao", LocalDateTime.class));
				
				precificacoes.add(prc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		
		return precificacoes;
	}
}
