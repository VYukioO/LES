package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.Generos;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class UsuarioDAO implements IDao{
	
	private Connection conn = null;
	private Mensagem mensagem;
	EnderecoDAO enderecoDao = new EnderecoDAO();
	TelefoneDAO telefoneDao	= new TelefoneDAO();
	CartaoDao cartaoDao = new CartaoDao();
	

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Usuario usuario = (Usuario) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		ResultSet rs;
		
		String sql = "INSERT INTO tb_usuario ("
				+ "usu_nome, "
				+ "usu_email, "
				+ "usu_senha, "
				+ "usu_cpf, "
				+ "usu_dt_nascimento, "
				+ "usu_genero, "
				+ "usu_admin, "
				+ "usu_ativo, "
				+ "usu_dt_cadastro, "
				+ "usu_dt_atualizacao"
				+ ") "
				+ "VALUES (?, ?, ?, ?, ?, ?, FALSE, TRUE, NOW(), NOW())";
						// 1, 2, 3, 4, 5, 6,
		
		try {
			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getEmail());
			pstm.setString(3, usuario.getSenha());
			pstm.setString(4, usuario.getCpf());
			pstm.setObject(5, usuario.getDtNascimento());
			pstm.setString(6, usuario.getGenero().toString());
			pstm.executeUpdate();
			
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				mensagem.setMsg(Integer.toString(rs.getInt(1)));
				mensagem.setMsgStatus(MensagemStatus.OPERACAO);
				return mensagem;
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
		Usuario usuario = (Usuario) entidade;
		Endereco endereco = new Endereco();
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		try {
			if (usuario.getSenha() != null) {
				String sql = "UPDATE tb_usuario SET "
						+ "usu_senha = ? "
						+ "WHERE usu_id = ?";
				
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, usuario.getSenha());
				pstm.setLong(2, usuario.getId());
				pstm.executeUpdate();
				
				mensagem.setMsg("Senha atualizada com sucesso.");
				mensagem.setMsgStatus(MensagemStatus.SUCESSO);
			} else {
				String sql = "UPDATE tb_usuario SET "
						+ "usu_nome = ?"
						+ "usu_email = ?"
						+ "usu_cpf = ?"
						+ "usu_dt_nascimento = ?"
						+ "usu_genero = ?"
						+ "usu_admin = ?"
						+ "usu_dt_atualizacao = NOW() "
						+ "WHERE usu_id = ?";
				
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, usuario.getNome());
				pstm.setString(2, usuario.getEmail());
				pstm.setString(3, usuario.getCpf());
				pstm.setObject(4, usuario.getDtNascimento());
				pstm.setString(5, usuario.getGenero().toString());
				pstm.setBoolean(6, usuario.isAdmin());
				pstm.setLong(7, usuario.getId());
				
				List<AEntidade> BDEndereco = new ArrayList<AEntidade>();
				endereco = new Endereco(); // dnv?
				end
				
				
				
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
		Endereco endereco = new Endereco();
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		String sql = "UPDATE tb_usuario SET "
				+ "usu_ativo = false "
				+ "WHERE usu_id = ? AND usu_id != 1";
		
		endereco.setcliente();
		try {
			if (enderecoDao.deletar(endereco).getMsgStatus() == MensagemStatus.ERRO) {
				throw new SQLException();
			}
			
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
		List<AEntidade> usuarios = new ArrayList<AEntidade>();
		List<AEntidade> bancoEnderecos = new ArrayList<AEntidade>();
		
		List<Endereco> enderecos = new ArrayList<Endereco>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "usu_id, "
				+ "usu_nome, "
				+ "usu_email, "
				+ "usu_senha, "
				+ "usu_cpf, "
				+ "usu_dt_nascimento, "
				+ "usu_genero, "
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
		if (usuario.getCpf() != null) {
			sql += "AND usu_cpf = '" + usuario.getCpf() + "' ";
		}
		if (usuario.getDtNascimento() != null) {
			sql += "AND usu_dt_nasimento = '" + usuario.getDtNascimento() + "' ";
		}
		if (usuario.getGenero() != null) {
			sql += "AND usu_genero = '" + usuario.getGenero() + "' ";
		}
		if (usuario.getNome() != null) {
			sql += "AND usu_nome LIKE '" + usuario.getNome() + "' ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			Usuario usu = new Usuario();
			Endereco end = new Endereco();
			
			while (rs.next()) {
				usu = new Usuario();
				end = new Endereco();
				
				listaEnderecos = new ArrayList<AEntidade>();
				enderecos = new ArrayList<Endereco>();
				
				usu.setId(rs.getLong("usu_id"));
				usu.setNome(rs.getString("usu_nome"));
				usu.setEmail(rs.getString("usu_email"));
				usu.setSenha(rs.getString("usu_senha"));
				usu.setCpf(rs.getString("usu_cpf"));
				usu.setDtNascimento(rs.getObject("usu_dt_nascimento", LocalDate.class));
				usu.setGenero(Generos.valueOf(rs.getString("usu_genero")));
				usu.setAdmin(rs.getBoolean("usu_admin"));
				usu.setAtivo(rs.getBoolean("usu_ativo"));
				usu.setDtCadastro(rs.getObject("usu_dt_cadastro", LocalDateTime.class));
				usu.setDtAtualizacao(rs.getObject("usu_dt_atualizacao", LocalDateTime.class));
				
				end.setusuario();
				bancoEnderecos.addAll(enderecoDao.consultar(end));
				for (AEntidade endereco : bancoEnderecos) {
					enderecos.add((Endereco) endereco);
				}
				
				usu.setEnderecos(enderecos);
				
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
