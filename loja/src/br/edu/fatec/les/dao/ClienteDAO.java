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
import br.edu.fatec.les.dominio.modelo.Cartao;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.dominio.modelo.Telefone;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.util.ConnectionFactory;

public class ClienteDAO implements IDao {
	
	private Connection conn = null;
	private Mensagem mensagem;
	UsuarioDAO usuarioDao = new UsuarioDAO();
	EnderecoDAO enderecoDao = new EnderecoDAO();
	TelefoneDAO telefoneDao	= new TelefoneDAO();
	CartaoDAO cartaoDao = new CartaoDAO();
	

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		Cliente cliente = (Cliente) entidade;
		conn = ConnectionFactory.getConnection();
		mensagem = new Mensagem();
		PreparedStatement pstm = null;
		ResultSet rs;
		Cliente aux = new Cliente();
		
		String sql = "INSERT INTO tb_cliente ("
				+ "cli_nome, "
				+ "cli_cpf, "
				+ "cli_dt_nascimento, "
				+ "cli_genero, "
				+ "cli_usu_id, "
				+ "cli_ativo, "
				+ "cli_dt_cadastro, "
				+ "cli_dt_atualizacao"
				+ ") "
				+ "VALUES (?, ?, ?, ?, ?, TRUE, NOW(), NOW())";
						// 1, 2, 3, 4, 5,
		
		try {
			mensagem = usuarioDao.salvar(cliente.getUsuario());
			if (mensagem.getMsgStatus() == MensagemStatus.ERRO) {
				throw new SQLException();
			}
			String idUsuario = mensagem.getMsg();
			
			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getCpf());
			pstm.setObject(3, cliente.getDtNascimento());
			pstm.setString(4, cliente.getGenero().toString());
			pstm.setLong(5, Long.parseLong(idUsuario));
			pstm.executeUpdate();
			
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				Long idCliente = rs.getLong(1);
				aux = new Cliente();
				aux.setId(idCliente);

				for (Endereco end : cliente.getEnderecos()) {
					end.setCliente(aux);
					if (enderecoDao.salvar(end).getMsgStatus() == MensagemStatus.ERRO) {
						throw new SQLException();
					}
				}

				if (!cliente.getCartoes().isEmpty()) {
					for (Cartao crt : cliente.getCartoes()) {
						crt.setCliente(aux);
						if (cartaoDao.salvar(crt).getMsgStatus() == MensagemStatus.ERRO) {
							throw new SQLException();
						}
					}					
				}
				
			}
			
			mensagem = new Mensagem();
			mensagem.setMsg("Cliente cadastrado com sucesso.");
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
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		Cliente cliente = (Cliente) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		
		String sql = "UPDATE tb_cliente SET "
				+ "cli_nome = ?, "
				+ "cli_cpf = ?, "
				+ "cli_dt_nascimento = ?, "
				+ "cli_genero = ?, "
				+ "cli_dt_atualizacao = NOW() "
				+ "WHERE cli_id = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getCpf());
			pstm.setObject(3, cliente.getDtNascimento());
			pstm.setString(4, cliente.getGenero().toString());
			pstm.setLong(5, cliente.getId());

			if (usuarioDao.atualizar(cliente.getUsuario()).getMsgStatus() == MensagemStatus.ERRO) {
				throw new SQLException();
			}
			
			if (telefoneDao.atualizar(cliente.getTelefone()).getMsgStatus() == MensagemStatus.ERRO) {
				throw new SQLException();
			}
			
			// Atualizar endereco
			List<AEntidade> BDEndereco = new ArrayList<AEntidade>();
			Endereco endereco = new Endereco();
			endereco.setCliente(cliente);
			BDEndereco.addAll(enderecoDao.consultar(endereco));
			
			// Adiciona no banco os novos enderecos
			for (Endereco end : cliente.getEnderecos()) {
				if (end.getId() == null) {
					if (enderecoDao.salvar(end).getMsgStatus() == MensagemStatus.ERRO) {
						throw new SQLException();
					}
				}
			}
			
			// Exclui do banco os enderecos apagados
			for (AEntidade bdend : BDEndereco) {
				bdend = (Endereco) bdend;
				boolean excluir = true;
				for (Endereco end : cliente.getEnderecos()) {
					if (end.getId() != null) {
						if (end.getId() != bdend.getId()) {
							continue;
						} else {
							excluir = false;
							break;
						}
					}
				}
				if (excluir) {
					if (enderecoDao.deletar(bdend).getMsgStatus() == MensagemStatus.ERRO) {
						throw new SQLException();
					}
				}
			}
			///

			// Atualizar cartao
			List<AEntidade> BDCartao = new ArrayList<AEntidade>();
			Cartao cartao = new Cartao();
			cartao.setCliente(cliente);
			BDCartao.addAll(cartaoDao.consultar(cartao));
			
			// Adiciona no banco os novos cartoes
			for (Cartao crt : cliente.getCartoes()) {
				if (crt.getId() == null) {
					if (cartaoDao.salvar(crt).getMsgStatus() == MensagemStatus.ERRO) {
						throw new SQLException();
					}
				}
			}
			
			// Exclui do banco os cartoes apagados
			for (AEntidade bdcrt : BDCartao) {
				bdcrt = (Cartao) bdcrt;
				boolean excluir = true;
				for (Cartao crt : cliente.getCartoes()) {
					if (crt.getId() != null) {
						if (crt.getId() != bdcrt.getId()) {
							continue;
						} else {
							excluir = false;
							break;
						}
					}
				}
				if (excluir) {
					if (enderecoDao.deletar(bdcrt).getMsgStatus() == MensagemStatus.ERRO) {
						throw new SQLException();
					}
				}
			}
			
			mensagem = new Mensagem();
			pstm.executeUpdate();
			mensagem.setMsg("Cliente atualizado com sucesso.");
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
		Cliente cliente = (Cliente) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		mensagem = new Mensagem();
		Endereco endereco = new Endereco();
		Cartao cartao = new Cartao();
		
		String sql = "UPDATE tb_cliente SET "
				+ "cli_ativo = false, "
				+ "cli_dt_atualizacao = NOW() "
				+ "WHERE cli_id = ? AND cli_id != 1";
		
		endereco.setCliente(cliente);
		cartao.setCliente(cliente);
		try {
			if (enderecoDao.deletar(endereco).getMsgStatus() == MensagemStatus.ERRO
					|| cartaoDao.deletar(cartao).getMsgStatus() == MensagemStatus.ERRO) {
				throw new SQLException();
			}
			
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, cliente.getId());
			pstm.executeUpdate();
			
			mensagem.setMsg("Cliente deletado com sucesso.");
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
		Cliente cliente = (Cliente) entidade;
		conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		List<AEntidade> clientes = new ArrayList<AEntidade>();
		List<AEntidade> BDEnderecos = new ArrayList<AEntidade>();
		List<AEntidade> BDCartoes = new ArrayList<AEntidade>();

		List<Endereco> enderecos = new ArrayList<Endereco>();
		List<Cartao> cartoes = new ArrayList<Cartao>();
		
		
		String sql = "SELECT "
				+ "cli_id, "
				+ "cli_nome, "
				+ "cli_cpf, "
				+ "cli_dt_nascimento, "
				+ "cli_genero, "
				+ "cli_ativo, "
				+ "cli_dt_cadastro, "
				+ "cli_dt_atualizacao "
				+ "FROM tb_cliente ";
		
		if (cliente.isAtivo()) {
			sql += "WHERE cli_ativo = 1 ";
		} else {
			sql += "WHERE (cli_ativo = 1 OR cli_ativo = 0) ";
		}
		if (cliente.getId() != null) {
			sql += "AND cli_id = " + cliente.getId() + " "; 
		}
		if (cliente.getCpf() != null) {
			sql += "AND cli_cpf = '" + cliente.getCpf() + "' ";
		}
		if (cliente.getDtNascimento() != null) {
			sql += "AND cli_dt_nasimento = '" + cliente.getDtNascimento() + "' ";
		}
		if (cliente.getGenero() != null) {
			sql += "AND cli_genero = '" + cliente.getGenero().toString() + "' ";
		}
		if (cliente.getNome() != null) {
			sql += "AND cli_nome LIKE '" + cliente.getNome() + "' ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			Cliente cli = new Cliente();
			Usuario usu = new Usuario();
			Telefone tel = new Telefone();
			Endereco end = new Endereco();
			Cartao crt = new Cartao();
			
			while (rs.next()) {
				cli = new Cliente();
				usu = new Usuario();
				tel = new Telefone();
				end = new Endereco();
				crt = new Cartao();

				BDEnderecos = new ArrayList<AEntidade>();
				enderecos = new ArrayList<Endereco>();
				BDCartoes = new ArrayList<AEntidade>();
				cartoes = new ArrayList<Cartao>();
				
				cli.setId(rs.getLong("cli_id"));
				cli.setNome(rs.getString("cli_nome"));
				cli.setCpf(rs.getString("cli_cpf"));
				cli.setDtNascimento(rs.getObject("cli_dt_nascimento", LocalDate.class));
				cli.setGenero(Generos.valueOf(rs.getString("cli_genero")));
				cli.setAtivo(rs.getBoolean("cli_ativo"));
				cli.setDtCadastro(rs.getObject("cli_dt_cadastro", LocalDateTime.class));
				cli.setDtAtualizacao(rs.getObject("cli_dt_atualizacao", LocalDateTime.class));
				// pegar telefone do cliente
				tel.setCliente(cli);
				cli.setTelefone((Telefone) telefoneDao.consultar(tel).get(0));

				// pegar enderecos do cliente
				end.setCliente(cli);
				BDEnderecos.addAll(enderecoDao.consultar(end));
				for (AEntidade endereco : BDEnderecos) {
					enderecos.add((Endereco) endereco);
				}
				cli.setEnderecos(enderecos);
				
				// pegar cartoes do cliente
				crt.setCliente(cli);
				BDCartoes.addAll(cartaoDao.consultar(crt));
				for (AEntidade cartao : BDCartoes) {
					cartoes.add((Cartao) cartao);
				}
				cli.setCartoes(cartoes);
				
				// pegar usuario do cliente
				usu.setId(rs.getLong("cli_usu_id"));
				cli.setUsuario((Usuario) usuarioDao.consultar(usu).get(0));
				
				// adicionar cliente na lista
				clientes.add(usu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return clientes;
	}
}
