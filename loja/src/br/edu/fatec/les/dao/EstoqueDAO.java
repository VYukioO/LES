package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Estoque;
import br.edu.fatec.les.dominio.modelo.Produto;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class EstoqueDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem = null;
	EstoqueItemDAO estoqueItemDao = new EstoqueItemDAO();

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação nao suportada.");
	}

	@Override
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		Estoque estoque = (Estoque) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		
		String sql = "UPDATE tb_estoque SET "
				+ "stq_qtde_total = stq_qtde_total + ? "
				+ "WHERE stq_pro_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, estoque.getQtdeTotal());
			pstm.setLong(2, estoque.getProduto().getId());
			
			pstm.executeUpdate();
			mensagem.setMsg("Estoque atualizado com sucesso.");
			mensagem.setMsgStatus(MensagemStatus.SUCESSO);
		} catch (SQLException e) {
			mensagem.setMsg("Ocorreu um erro durante a operação. Tente novamente.");
			mensagem.setMsgStatus(MensagemStatus.ERRO);
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
		
		return mensagem;
	}

	@Override
	public Mensagem deletar(AEntidade entidade) throws SQLException {
		Estoque estoque = (Estoque) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		
		String sql = "UPDATE tb_estoque SET "
				+ "stq_qtde_total = stq_qtde_total - ? "
				+ "WHERE stq_pro_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, estoque.getQtdeTotal());
			pstm.setLong(2, estoque.getProduto().getId());
			
			mensagem.setMsg("Estoque deletado com sucesso.");
			mensagem.setMsgStatus(MensagemStatus.SUCESSO);
		} catch (SQLException e) {
			mensagem.setMsg("Ocorreu um erro durante a operação. Tente novamente.");
			mensagem.setMsgStatus(MensagemStatus.ERRO);
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
		return mensagem;
	}

	@Override
	public List<AEntidade> consultar(AEntidade entidade) throws SQLException {
		Produto produto = (Produto) entidade;
		conn = ConnectionFactory.getConnection();
		List<AEntidade> estoques = new ArrayList<AEntidade>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "stq_id, "
				+ "stq_qtde_total, "
				+ "FROM tb_estoque WHERE stq_pro_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, produto.getId());
			rs = pstm.executeQuery();
			
			Estoque stq = new Estoque();
			//Produto pro = new Produto();
			
			while (rs.next()) {
				stq = new Estoque();
				//pro = new Produto();
				
				stq.setQtdeTotal(rs.getInt("stq_qtde_total"));
				stq.setEstoqueItem(((Produto) estoqueItemDao.consultarItem(rs.getLong("stq_id")).get(0)).getEstoque().getEstoqueItem());
				//pro.setEstoque(stq);

				//estoques.add(pro);
				estoques.add(stq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return estoques;
	}

}
