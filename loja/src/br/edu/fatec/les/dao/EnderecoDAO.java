package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.TipoEndereco;
import br.edu.fatec.les.dominio.enums.TipoLogradouro;
import br.edu.fatec.les.dominio.enums.TipoResidencia;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class EnderecoDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem;

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Endereco endereco = (Endereco) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		String sql = "INSERT INTO tb_endereco ("
				+ "end_nome, "
				+ "end_tipo_logradouro, "
				+ "end_logradouro, "
				+ "end_numero, "
				+ "end_bairro, "
				+ "end_cep, "
				+ "end_cidade, "
				+ "end_estado, "
				+ "end_pais, "
				+ "end_cli_id, "
				+ "end_tipo_residencia, "
				+ "end_tipo_endereco, "
				+ "end_favorito, "
				+ "end_ativo, "
				+ "end_dt_cadastro, "
				+ "end_dt_atualizacao "
				+ ") "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE, NOW(), NOW()"; 
						// 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, endereco.getNome());
			pstm.setString(2, endereco.getTipoLogradouro().toString());
			pstm.setString(3, endereco.getLogradouro());
			pstm.setInt(4, endereco.getNumero());
			pstm.setString(5, endereco.getBairro());
			pstm.setString(6, endereco.getCep());
			pstm.setString(7, endereco.getCidade());
			pstm.setString(8, endereco.getEstado());
			pstm.setString(9, endereco.getPais());
			pstm.setLong(10, endereco.getCliente().getId());
			pstm.setString(11, endereco.getTipoResidencia().toString());
			pstm.setString(12, endereco.getTipoEndereco().toString());
			pstm.setBoolean(13, endereco.isFavorito());
			
			mensagem.setMsg("Endereço cadastrado com sucesso.");
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
	}

	@Override
	public Mensagem deletar(AEntidade entidade) throws SQLException {
		Endereco endereco = (Endereco) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		
		String sql = "UPDATE tb_endereco SET "
				+ "end_ativo = false, "
				+ "end_dt_atualizacao = NOW() "
				+ "WHERE ";
		
		if (endereco.getId() != null) {
			sql += "end_id = " + endereco.getId() + " ";
		} else {
			sql += "end_cli_id = " + endereco.getCliente().getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			
			mensagem.setMsg("Endereço deletado com sucesso.");
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
		Endereco endereco = (Endereco) entidade;
		conn = ConnectionFactory.getConnection();
		Endereco end = new Endereco();
		Cliente cli = new Cliente();
		List<AEntidade> enderecos = new ArrayList<AEntidade>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "end_id, "
				+ "end_nome, "
				+ "end_tipo_logradouro, "
				+ "end_logradouro, "
				+ "end_numero, "
				+ "end_bairro, "
				+ "end_cep, "
				+ "end_cidade, "
				+ "end_estado, "
				+ "end_pais, "
				+ "end_complemento, "
				+ "end_cli_id, "
				+ "end_tipo_residencia, "
				+ "end_tipo_endereco, "
				+ "end_favorito, "
				+ "end_ativo, "
				+ "end_dt_cadastro, "
				+ "end_dt_atualizacao "
				+ "FROM tb_endereco ";
		
		if (endereco.isAtivo()) {
			sql += "WHERE end_ativo = 1 ";
		} else {
			sql += "WHERE (end_ativo = 1 OR end_ativo = 0) ";
		}
		if (endereco.getId() != null) {
			sql += "AND end_id = " + endereco.getId() + " ";
		}
		if (endereco.getCliente() != null && endereco.getCliente().getId() != null) {
			sql += "AND end_cli_id = " + endereco.getCliente().getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				end = new Endereco();
				cli = new Cliente();
				
				cli.setId(Long.parseLong(rs.getString("end_cli_id")));
				end.setCliente(cli);
				
				end.setId(rs.getLong("end_id"));
				end.setNome(rs.getString("end_nome"));
				end.setTipoLogradouro(TipoLogradouro.valueOf(rs.getString("end_tipo_logradouro")));
				end.setLogradouro(rs.getString("end_logradouro"));
				end.setNumero(rs.getInt("end_numero"));
				end.setBairro(rs.getString("end_bairro"));
				end.setCep(rs.getString("end_cep"));
				end.setCidade(rs.getString("end_cidade"));
				end.setEstado(rs.getString("end_estado"));
				end.setPais(rs.getString("end_pais"));
				end.setComplemento(rs.getString("end_complemento"));
				end.setTipoResidencia(TipoResidencia.valueOf(rs.getString("end_tipo_residencia")));
				end.setTipoEndereco(TipoEndereco.valueOf(rs.getString("end_tipo_endereco")));
				end.setFavorito(rs.getBoolean("end_favorito"));
				end.setAtivo(rs.getBoolean("end_ativo"));
				end.setDtCadastro(rs.getObject("end_dt_cadastro", LocalDateTime.class));	
				end.setDtAtualizacao(rs.getObject("end_dt_atualizacao", LocalDateTime.class));			
				
				enderecos.add(end);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return enderecos;
	}

}
