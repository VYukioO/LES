package br.edu.fatec.les.viewHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.Generos;
import br.edu.fatec.les.dominio.modelo.Usuario;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.facade.MensagemStatus;
import br.edu.fatec.les.facade.Resultado;

public class UsuarioVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Usuario usuario = new Usuario();
		TelefoneVH telefoneVh = new TelefoneVH();
		EnderecoVH enderecoVh = new EnderecoVH();
		CartaoVH cartaoVh = new CartaoVH();
		
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("cadastrarUsuario") ||
				tarefa.equals("atualizarUsuario")) {
			if (req.getParameter("uId") != null) {
				usuario.setId(Long.parseLong(req.getParameter("uId")));
			}
			
			usuario.setNome(req.getParameter("uNome"));
			usuario.setEmail(req.getParameter("uEmail"));
			usuario.setSenha(req.getParameter("uSenha"));
			usuario.setCpf(req.getParameter("uCpf"));
			usuario.setDtNascimento(req.getParameter("uDtNasc"));
			usuario.setGenero(Generos.valueOf(req.getParameter("uGenero")));
			usuario.setTelefone(telefoneVh.getEntidade(req));
			usuario.setAdmin(req.getParameter("uAdmin"));
			usuario.setEnderecos(enderecoVh.getEntidade(req));
			usuario.setCartoes(cartaoVh.getEntidade(req));
		
		}
		return usuario;
		
		if (tarefa.equals("alterarSenha")) {
			usuario.setId(Long.parseLong(req.getParameter("uId")));
			usuario.setSenha(req.getParameter("uSenha"));
			return usuario;
		}
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
						// pegar dados do cliente/usuario?
						// ou ja ta na variavel usuario?
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
