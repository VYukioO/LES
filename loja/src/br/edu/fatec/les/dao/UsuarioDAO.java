package br.edu.fatec.les.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class UsuarioDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem;

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Usuario usuario = (Usuario) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		ResultSet rs;
	
		String sql = "INSERT INTO tb_usuario ("
				+ "usu_email, "
				+ "usu_senha, "
				+ "usu_admin, "
				+ "usu_ativo, "
				+ "usu_dt_cadastro, "
				+ "usu_dt_atualizacao"
				+ ") "
				+ "VALUES (?, ?, FALSE, TRUE, NOW(), NOW())";
						// 1, 2, 3, 
		
		try {
			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, usuario.getEmail());
			pstm.setString(2, usuario.getSenha());
			pstm.executeUpdate();
			
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				mensagem.setMsg(Integer.toString(rs.getInt(1)));
				mensagem.setMsgStatus(MensagemStatus.OPERACAO);
				return mensagem;
			}
		} catch (SQLException e) {
			System.out.println(e);
			mensagem.setMsg("Ocorreu um erro durante a operação. Tente novamente.");
			mensagem.setMsgStatus(MensagemStatus.ERRO);
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
		return mensagem;
	}

	@Override
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		Usuario usuario = (Usuario) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		try {
			if (usuario.getSenha() != null) {
				String sql = "UPDATE tb_usuario SET "
						+ "usu_senha = ?, "
						+ "usu_dt_atualizacao = NOW() "
						+ "WHERE usu_id = ?";
				
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, usuario.getSenha());
				pstm.setLong(2, usuario.getId());
				pstm.executeUpdate();
				
				mensagem.setMsg("Senha atualizada com sucesso.");
				mensagem.setMsgStatus(MensagemStatus.SUCESSO);
			} else {
				String sql = "UPDATE tb_usuario SET "
						+ "usu_email = ?,"
						+ "usu_dt_atualizacao = NOW() "
						+ "WHERE usu_id = ?";
				
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, usuario.getEmail());
				pstm.setLong(2, usuario.getId());
				pstm.executeUpdate();
				
				mensagem.setMsg("Usuário atualizado com sucesso");
				mensagem.setMsgStatus(MensagemStatus.SUCESSO);
			}
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
		Usuario usuario = (Usuario) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		String sql = "UPDATE tb_usuario SET "
				+ "usu_ativo = false, "
				+ "usu_dt_atualizacao = NOW() "
				+ "WHERE usu_id = ? AND usu_id != 1";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, usuario.getId());
			pstm.executeUpdate();
			
			mensagem.setMsg("Usuário deletado com sucesso.");
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
		Usuario usuario = (Usuario) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		List<AEntidade> usuarios = new ArrayList<AEntidade>();
		
		String sql = "SELECT "
				+ "usu_id, "
				+ "usu_email, "
				+ "usu_senha, "
				+ "usu_admin, "
				+ "usu_ativo, "
				+ "usu_dt_cadastro, "
				+ "usu_dt_atualizacao "
				+ "FROM tb_usuario ";
		
		if (usuario.isAtivo()) {
			sql += "WHERE usu_ativo = 1 ";
		} else {
			sql += "WHERE (usu_ativo = 1 OR usu_ativo = 0) ";
		}
		if (usuario.getId() != null) {
			sql += "AND usu_id = " + usuario.getId() + " "; 
		}
		if (usuario.getEmail() != null) {
			sql += "AND usu_email = '" + usuario.getEmail() + "' ";
		}
		if (usuario.getSenha() != null) {
			sql += "AND usu_senha = '" + usuario.getSenha() + "' ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			Usuario usu = new Usuario();
			
			while (rs.next()) {
				usu = new Usuario();
				
				usu.setId(rs.getLong("usu_id"));
				usu.setEmail(rs.getString("usu_email"));
				usu.setSenha(rs.getString("usu_senha"));
				usu.setAdmin(rs.getBoolean("usu_admin"));
				usu.setAtivo(rs.getBoolean("usu_ativo"));
				usu.setDtCadastro(rs.getObject("usu_dt_cadastro", LocalDateTime.class));
				usu.setDtAtualizacao(rs.getObject("usu_dt_atualizacao", LocalDateTime.class));
				
				usuarios.add(usu);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return usuarios;
	}

}
