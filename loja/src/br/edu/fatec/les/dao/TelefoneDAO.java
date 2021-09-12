package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.TipoTelefone;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Telefone;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class TelefoneDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem = null;

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Telefone telefone = (Telefone) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		String sql = "INSERT INT tb_telefone ("
				+ "tel_ddd, "
				+ "tel_numero, "
				+ "tel_tipo_telefone, "
				+ "tel_cli_id, "
				+ "tel_ativo, "
				+ "tel_dt_cadastro, "
				+ "tel_dt_atualizacao "
				+ ") "
				+ "VALUES (?, ?, ?, ?, TRUE, NOW(), NOW())";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, telefone.getDdd());
			pstm.setString(2, telefone.getNumero());
			pstm.setString(3, telefone.getTipoTelefone().toString());
			pstm.setLong(4, telefone.getCliente().getId());
			
			mensagem.setMsg("Telefone cadastrado com sucesso");
			mensagem.setMsgStatus(MensagemStatus.OPERACAO);
		} catch (SQLException e) {
			mensagem.setMsg("Ocorreu um erro durante a operação. Tente Novamente.");
			mensagem.setMsgStatus(MensagemStatus.ERRO);
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
		return mensagem;
	}

	@Override
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação não suportada");
	}

	@Override
	public Mensagem deletar(AEntidade entidade) throws SQLException {
		Telefone telefone = (Telefone) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		
		String sql = "UPDATE tb_telefone SET "
				+ "tel_ativo = false, "
				+ "tel_dt_atualizacao = NOW() "
				+ "WHERE ";
		
		if (telefone.getId() != null) {
			sql += "tel_id = " + telefone.getId() + " ";
		} else {
			sql += "tel_cli_id = " + telefone.getCliente().getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			
			mensagem.setMsg("Telefone deletado com sucesso.");
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
		Telefone telefone = (Telefone) entidade;
		conn = ConnectionFactory.getConnection();
		Telefone tel = new Telefone();
		Cliente cli = new Cliente();
		
		List<AEntidade> telefones = new ArrayList<AEntidade>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "tel_id, "
				+ "tel_ddd, "
				+ "tel_numero, "
				+ "tel_tipo_telefone, "
				+ "tel_cli_id, "
				+ "tel_ativo, "
				+ "tel_dt_cadastro, "
				+ "tel_dt_atualizacao "
				+ "FROM tb_telefone WHERE tel_ativo = 1 ";
		
		if (telefone.getId() != null) {
			sql += "AND tel_id = " + telefone.getId() + " ";
		}
		if (telefone.getDdd() != 0) {
			sql += "AND tel_ddd = " + telefone.getDdd() + " ";
		}
		if (telefone.getNumero() != null) {
			sql += "AND tel_numero = " + telefone.getNumero() + " ";
		}
		if (telefone.getTipoTelefone() != null) {
			sql += "AND tel_tipo_telefone = " + telefone.getTipoTelefone() + " ";
		}
		if (telefone.getCliente().getId() != null) {
			sql += "AND tel_cli_id = " + telefone.getCliente().getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				tel = new Telefone();
				cli = new Cliente();
				
				cli.setId(rs.getLong("tel_cli_id"));
				tel.setCliente(cli);
				
				tel.setId(rs.getLong("tel_id"));
				tel.setDdd(rs.getInt("tel_ddd"));
				tel.setNumero(rs.getString("tel_numero"));
				tel.setTipoTelefone(TipoTelefone.valueOf(rs.getString("tel_tipo_telefone")));
				tel.setAtivo(rs.getBoolean("tel_ativo"));
				tel.setDtCadastro(rs.getObject("tel_dt_cadastro", LocalDateTime.class));
				tel.setDtAtualizacao(rs.getObject("tel_dt_atualizacao", LocalDateTime.class));
				
				telefones.add(tel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return telefones;
	}

}
