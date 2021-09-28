package br.edu.fatec.les.viewHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.fatec.les.dao.ClienteDAO;
import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.facade.Resultado;

public class UsuarioVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Usuario usuario = new Usuario();
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("atualizarCliente") ||
				tarefa.equals("deletarCliente") ||
				tarefa.equals("editarCliente")) {
			usuario.setId(Long.parseLong(req.getParameter("usuId")));
		}
		
		if (tarefa.equals("alterarSenha")) {
			usuario.setId(Long.parseLong(req.getParameter("usuId")));
			usuario.setSenha(req.getParameter("usuSenha"));
			return usuario;
		}
		
		usuario.setAdmin(false);
		usuario.setEmail(req.getParameter("usuEmail"));
		usuario.setEmail(req.getParameter("usuSenha"));
		return usuario;
	}

	@Override
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("alterarSenha")) {
			req.getRequestDispatcher("clienteMenu.jsp").forward(req, resp);
		} else {
			Resultado resultado = new Resultado();
			resultado = (Resultado) req.getAttribute("resultado");
			if (resultado.getEntidade().isEmpty() ||
					resultado.getEntidade() == null) {
				Mensagem mensagem = new Mensagem();
				mensagem.setMsg("Login e/ou Senha inválidos");
				mensagem.setMsgStatus(MensagemStatus.ERRO);
				
				ArrayList<Mensagem> mensagens = new ArrayList<Mensagem>();
				mensagens.add(mensagem);
				
				resultado = new Resultado();
				resultado.setMsgs(mensagens);
				
				req.setAttribute("resultado", resultado);
				req.getRequestDispatcher("usuarioLogin.jsp").forward(req, resp);
			} else {
				Usuario usuario = (Usuario) resultado.getEntidade().get(0);
				
				HttpSession session = req.getSession();
				
				session.invalidate();
				session = req.getSession();
				session.setMaxInactiveInterval(15*60);
				
				if (!usuario.isAdmin()) {
					try {
						ClienteDAO clienteDao = new ClienteDAO();
						Cliente cliente = new Cliente();
						
						cliente.setUsuario(usuario);
						cliente = (Cliente) clienteDao.consultar(cliente).get(0);
						session.setAttribute("cliente", cliente);
					} catch (SQLException e) {
						// TODO: handle exception
					}
				}
				
				session.setAttribute("status", "on");
				session.setAttribute("usuario", usuario);
				
				resp.sendRedirect("clienteMenu.jsp");
			}
		}
	}

}
