package br.edu.fatec.les.viewHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.TipoTelefone;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Telefone;

public class TelefoneVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Telefone telefone = new Telefone();
		ClienteVH clienteVh = new ClienteVH();
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("cadastrarCliente") || tarefa.equals("atualizarCliente")) {
			telefone.setTipoTelefone(TipoTelefone.valueOf(req.getParameter("telTipoTelefone")));
			telefone.setDdd(Integer.parseInt(req.getParameter("telDdd")));
			telefone.setNumero(req.getParameter("telNumero"));
			telefone.setCliente((Cliente) clienteVh.getEntidade(req));
		} else {
			telefone.setId(Long.parseLong(req.getParameter("crtId")));
		}
		return telefone;
	}

	@Override
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("pagamento.jsp");
		// ??
	}
	
}
