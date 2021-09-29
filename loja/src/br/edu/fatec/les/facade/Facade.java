package br.edu.fatec.les.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fatec.les.dao.CartaoDAO;
import br.edu.fatec.les.dao.ClienteDAO;
import br.edu.fatec.les.dao.EnderecoDAO;
import br.edu.fatec.les.dao.IDao;
import br.edu.fatec.les.dao.UsuarioDAO;
import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.EntidadeDominio;
import br.edu.fatec.les.dominio.modelo.Cartao;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.strategy.IStrategy;

public class Facade implements IFacade {
	
	private Map<String, IDao> daoMap;
	private Map<String, ArrayList<IStrategy>> strategyMap;
	private Resultado resultado;
	private Mensagem mensagem;
	ArrayList<Mensagem> mensagens = new ArrayList<Mensagem>();

	public Facade() {
		daoMap = new HashMap<String, IDao>();
		strategyMap = new HashMap<String, ArrayList<IStrategy>>();
		
		UsuarioDAO usuarioDao = new UsuarioDAO();
		ClienteDAO clienteDao = new ClienteDAO();
		EnderecoDAO enderecoDao = new EnderecoDAO();
		CartaoDAO cartaoDao = new CartaoDAO();

		daoMap.put(Usuario.class.getName(), usuarioDao);
		daoMap.put(Cliente.class.getName(), clienteDao);
		daoMap.put(Endereco.class.getName(), enderecoDao);
		daoMap.put(Cartao.class.getName(), cartaoDao);
		
		//adicionar strategy
	}
	
	@Override
	public Resultado salvar(EntidadeDominio entidadeDominio) {
		resultado = new Resultado();
		mensagem = new Mensagem();
		mensagens = new ArrayList<Mensagem>();
		
		ArrayList<IStrategy> strategies = strategyMap.get(entidadeDominio.getClass().getName());
		
		if (strategies != null) {
			for (IStrategy stg : strategies) {
				mensagem = stg.execute(entidadeDominio);
				if (mensagem.getMsgStatus() == MensagemStatus.ERRO) {
					mensagens.add(mensagem);
				} else {
					continue;
				}
			}
		}
		
		if (!mensagens.isEmpty()) {
			resultado.setMsgs(mensagens);
			return resultado;
		}
		
		IDao dao = daoMap.get(entidadeDominio.getClass().getName());
		
		try {
			mensagem = dao.salvar(entidadeDominio);
			mensagens.add(mensagem);
			resultado.setMsgs(mensagens);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

	@Override
	public Resultado atualizar(EntidadeDominio entidadeDominio) {
		resultado = new Resultado();
		mensagem = new Mensagem();
		mensagens = new ArrayList<Mensagem>();
		
		IDao dao = daoMap.get(entidadeDominio.getClass().getName());
		
		try {
			mensagem = dao.atualizar(entidadeDominio);
			mensagens.add(mensagem);
			resultado.setMsgs(mensagens);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

	@Override
	public Resultado deletar(EntidadeDominio entidadeDominio) {
		resultado = new Resultado();
		mensagem = new Mensagem();
		mensagens = new ArrayList<Mensagem>();
		
		IDao dao = daoMap.get(entidadeDominio.getClass().getName());
		
		try {
			mensagem = dao.deletar(entidadeDominio);
			mensagens.add(mensagem);
			resultado.setMsgs(mensagens);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidadeDominio) {
		resultado = new Resultado();
		mensagem = new Mensagem();
		mensagens = new ArrayList<Mensagem>();
		
		IDao dao = daoMap.get(entidadeDominio.getClass().getName());
		AEntidade aEntidade = entidadeDominio;
		
		try {
			List<AEntidade> listaEntidades = dao.consultar(aEntidade);
			resultado.setEntidades(listaEntidades);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

}
