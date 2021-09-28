package br.edu.fatec.les.viewHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.Generos;
import br.edu.fatec.les.dominio.modelo.Cartao;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.dominio.modelo.Telefone;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.facade.Resultado;

public class ClienteVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Cliente cliente = new Cliente();
		UsuarioVH usuarioVh = new UsuarioVH();
		TelefoneVH telefoneVh = new TelefoneVH();
		EnderecoVH enderecoVh = new EnderecoVH();
		CartaoVH cartaoVh = new CartaoVH();
		
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("cadastrarCliente") || tarefa.equals("atualizarCliente")) {
			if (req.getParameter("cliId") != null) {
				cliente.setId(Long.parseLong(req.getParameter("cliId")));
			}
			
			cliente.setNome(req.getParameter("cliNome"));
			cliente.setCpf(req.getParameter("cliCpf"));
			cliente.setDtNascimento(LocalDate.parse(req.getParameter("cliDtNasc")));
			cliente.setGenero(Generos.valueOf(req.getParameter("cliGenero")));
			cliente.setTelefone((Telefone) telefoneVh.getEntidade(req));
			cliente.setEnderecos(enderecoVh.getEntidades(req));
			cliente.setCartoes(cartaoVh.getEntidades(req));

			for (Endereco end : cliente.getEnderecos()) {
				end.setCliente(cliente);
			}
			
			for (Cartao crt : cliente.getCartoes()) {
				crt.setCliente(cliente);
			}
		} else {
			if (req.getParameter("cliId") != null) {
				cliente.setId(Long.parseLong(req.getParameter("cliId")));
			}
			if (req.getParameter("usuId") != null) {
				cliente.setUsuario((Usuario) usuarioVh.getEntidade(req));
			}
		}
		return cliente;
	}

	@Override
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("consultarCliente")) {
			Resultado resultado = new Resultado();
			resultado = (Resultado) req.getAttribute("resultado");
			
			String json = new Gson().toJson(resultado);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} else if (tarefa.equals("atualizarCliente")) {
			Resultado resultado = new Resultado();
			resultado = (Resultado) req.getAttribute("resultado");
			boolean erro = false;
			
			for (Mensagem mensagem : resultado.getMsgs()) {
				if (mensagem.getMsgStatus() == MensagemStatus.ERRO) {
					erro = true;
				}
			}
			
			if (erro) {
				if (tarefa.equals("atualizarCliente")) {
					req.getRequestDispatcher("clienteLista.jsp").forward(req, resp);
				} else {
					req.getRequestDispatcher("clienteCadastro.jsp").forward(req, resp);
				}
			} else {
				req.getRequestDispatcher("usuarioLogin.jsp").forward(req, resp);
			}
		} else if (tarefa.equals("editarCliente")) {
			List<Cliente> clientes = new ArrayList<Cliente>();
			Resultado resultado = new Resultado();
			resultado = (Resultado) req.getAttribute("resultado");
			
			for (AEntidade cli : resultado.getEntidade()) {
				Cliente cliente = (Cliente)	cli;
				clientes.add(cliente);
			}
			
			Cliente cliente = clientes.get(0);
			
			req.setAttribute("cliente", cliente);
			req.getRequestDispatcher("clienteEditar.jsp").forward(req, resp);
		} else if (tarefa.equals("deletarCliente")) {
			req.getRequestDispatcher("clienteLista.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("index.html");
		}
	}

}
