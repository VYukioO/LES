package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Imagem;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class ImagemDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem;

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Imagem imagem = (Imagem) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		ResultSet rs;

		String sql = "INSERT INT tb_imagem ("
				+ "img_nome, "
				+ "img_descricao, "
				+ "img_caminho, "
				+ "img_ativo,"
				+ "img_dt_cadastro"
				+ "img_dt_atualizacao"
				+ ") "
				+ "VALUES (?, ?, ?, TRUE, NOW(), NOW())";
		
		try {
			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, imagem.getNome());
			pstm.setString(2, imagem.getDescricao());
			pstm.setString(3, imagem.getCaminho());
			pstm.executeUpdate();
			
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				mensagem.setMsg(Integer.toString(rs.getInt(1)));
				mensagem.setMsgStatus(MensagemStatus.OPERACAO);
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
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		Imagem imagem = (Imagem) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;

		String sql = "UPDATE tb_imagem SET "
				+ "img_nome = ?, "
				+ "img_descricao = ?, "
				+ "img_caminho = ?, "
				+ "img_dt_atualizacao = NOW() "
				+ "WHERE img_id = ? "
				+ "AND img_ativo = 1";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, imagem.getNome());
			pstm.setString(2, imagem.getDescricao());
			pstm.setString(3, imagem.getCaminho());
			pstm.setLong(4, imagem.getId());
			pstm.executeUpdate();
			
			mensagem.setMsg("Imagem atualizada com sucesso.");
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
		Imagem imagem = (Imagem) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		
		String sql = "UPDATE tb_imagem SET "
				+ "img_ativo = false "
				+ "WHERE img_id = ? ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, imagem.getId());
			pstm.executeUpdate();
			mensagem.setMsg("Imagem deletada com sucesso.");
			mensagem.setMsgStatus(MensagemStatus.SUCESSO);
		} catch (SQLException e) {
			mensagem.setMsg("Ocorreu um erro durante a operacao. Tente novamente.");
			mensagem.setMsgStatus(MensagemStatus.ERRO);
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
		
		return mensagem;
	}

	@Override
	public List<AEntidade> consultar(AEntidade entidade) throws SQLException {
		Imagem imagem = (Imagem) entidade;
		conn = ConnectionFactory.getConnection();
		List<AEntidade> imagens = new ArrayList<AEntidade>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "img_id, "
				+ "img_nome, "
				+ "img_descricao, "
				+ "img_caminho, "
				+ "img_ativo,"
				+ "img_dt_cadastro"
				+ "img_dt_atualizacao"
				+ "FROM tb_imagem "
				+ "WHERE img_ativo = 1 ";
		
		if (imagem.getId() != null) {
			sql += "AND prc_id = " + imagem.getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			Imagem img = new Imagem();
			while (rs.next()) {
				img = new Imagem();
				
				img.setId(rs.getLong("img_id"));
				img.setNome(rs.getString("img_nome"));
				img.setDescricao(rs.getString("img_descricao"));
				img.setCaminho(rs.getString("img_caminho"));
				img.setAtivo(rs.getBoolean("img_ativo"));
				img.setDtCadastro(rs.getObject("img_dt_cadastro", LocalDateTime.class));
				img.setDtAtualizacao(rs.getObject("img_dt_atualizacao", LocalDateTime.class));
				
				imagens.add(img);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		
		return imagens;
	}
}
