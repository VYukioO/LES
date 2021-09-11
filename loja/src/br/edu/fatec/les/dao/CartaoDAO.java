package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.TipoEndereco;
import br.edu.fatec.les.dominio.enums.TipoLogradouro;
import br.edu.fatec.les.dominio.enums.TipoResidencia;
import br.edu.fatec.les.dominio.modelo.Cartao;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class CartaoDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem;

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Cartao cartao = (Cartao) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		String sql = "INSERT INTO tb_cartao ("
				+ "crt_nome, "
				+ "crt_numero, "
				+ "crt_nome_impresso, "
				+ "crt_validade, "
				+ "crt_bandeira, "
				+ "crt_cvc, "
				+ "crt_usu_id, "
				+ "crt_favorito, "
				+ "crt_ativo, "
				+ "crt_dt_cadastro, "
				+ "crt_dt_atualizacao "
				+ ") "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE, NOW(), NOW()"; 
						// 1, 2, 3, 4, 5, 6, 7, 8,
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cartao.getNome());
			pstm.setString(2, cartao.getNumero());
			pstm.setString(3, cartao.getNomeImpresso());
			pstm.setObject(4, cartao.getValidade());
			pstm.setString(5, cartao.getBandeira());
			pstm.setInt(6, cartao.getCvc());
			pstm.setString(7, cartao.getCliente().getId());
			pstm.setBoolean(8, cartao.isFavorito());
			
			mensagem.setMsg("Cartão cadastrado com sucesso.");
			mensagem.setMsgStatus(MensagemStatus.OPERACAO);
		} catch (SQLException e) {
			mensagem.setMsg("Ocorreu um erro durante a operação. Tente novamente.");
			mensagem.setMsgStatus(MensagemStatus.ERRO);
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
		return mensagem;
	}

	@Override
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação não suportada");
		//?
	}

	@Override
	public Mensagem deletar(AEntidade entidade) throws SQLException {
		Cartao cartao = (Cartao) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		
		String sql = "UPDATE tb_cartao SET "
				+ "crt_ativo = false "
				+ "WHERE ";
		
		if (cartao.getId() != null) {
			sql += "end_id = " + cartao.getId() + " ";
		} else {
			sql += "crt_usu_id = " + cartao.getCliente().getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			
			mensagem.setMsg("Cartão deletado com sucesso.");
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
		Cartao cartao = (Cartao) entidade;
		conn = ConnectionFactory.getConnection();
		Cartao car = new Cartao();
		List<AEntidade> cartoes = new ArrayList<AEntidade>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "crt_id, "
				+ "crt_nome, "
				+ "crt_numero, "
				+ "crt_nome_impresso, "
				+ "crt_validade, "
				+ "crt_bandeira, "
				+ "crt_cvc, "
				+ "crt_usu_id, "
				+ "crt_favorito, "
				+ "crt_ativo, "
				+ "crt_dt_cadastro, "
				+ "crt_dt_atualizacao ";
		
		if (cartao.isAtivo()) {
			sql += "WHERE crt_ativo = 1 ";
		}
		if (cartao.getId() != null) {
			sql += "AND crt_id = " + cartao.getId() + " ";
		}
		// fazer pesquisar pelo cliente
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				car = new Cartao();
				
				car.setId(rs.getLong("crt_id"));
				car.setNome(rs.getString("crt_nome"));
				car.setNumero(rs.getString("crt_numero"));
				car.setNomeImpresso(rs.getString("crt_nome_impresso"));
				car.setValidade(rs.getObject("crt_validade", LocalDate.class));
				car.setBandeira(rs.getString("crt_bandeira"));
				car.setCvc(rs.getInt("crt_cvc"));
				car.setFavorito(rs.getBoolean("crt_favorito"));
				car.setAtivo(rs.getBoolean("crt_ativo"));
				car.setDtCadastro(rs.getObject("crt_dt_cadastro", LocalDateTime.class));	
				car.setDtAtualizacao(rs.getObject("crt_dt_atualizacao", LocalDateTime.class));			
				
				cartoes.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return cartoes;
	}
}
