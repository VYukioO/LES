package br.edu.fatec.les.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.edu.fatec.les.dao.CartaoDAO;
import br.edu.fatec.les.dao.ClienteDAO;
import br.edu.fatec.les.dao.EnderecoDAO;
import br.edu.fatec.les.dao.IDao;
import br.edu.fatec.les.dao.UsuarioDAO;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado atualizar(EntidadeDominio entidadeDominio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado deletar(EntidadeDominio entidadeDominio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidadeDominio) {
		// TODO Auto-generated method stub
		return null;
	}

}
