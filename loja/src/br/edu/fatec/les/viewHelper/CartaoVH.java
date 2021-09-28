package br.edu.fatec.les.viewHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Cartao;
import br.edu.fatec.les.dominio.modelo.Cliente;

public class CartaoVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Cartao cartao = new Cartao();
		ClienteVH clienteVh = new ClienteVH();
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("adicionarCartao")) {
			cartao.setNome(req.getParameter("crtNome"));
			cartao.setNumero(req.getParameter("crtNumero"));
			cartao.setNomeImpresso(req.getParameter("crtNomeImpresso"));
			cartao.setValidade(LocalDate.parse(req.getParameter("crtDtValidade")));
			cartao.setBandeira(req.getParameter("crtBandeira"));
			cartao.setCvc(Integer.parseInt(req.getParameter("crtCvc")));
			cartao.setFavorito(Boolean.parseBoolean(req.getParameter("crtFavorito")));
			cartao.setCliente((Cliente) clienteVh.getEntidade(req));
		} else {
			cartao.setId(Long.parseLong(req.getParameter("crtId")));
		}
		return cartao;
	}

	@Override
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("pagamento.jsp");
		// ??
	}
	
	public ArrayList<Cartao> getEntidades(HttpServletRequest req){
		ArrayList<Cartao> cartoes = new ArrayList<Cartao>();
		Cartao cartao = new Cartao();
		String tarefa = req.getParameter("tarefa");
		
		if (req.getParameterValues("crt") == null) {
			return cartoes;
		} else {
			String[] cartoesForm = req.getParameterValues("end");
			for (int i = 0; i < cartoesForm.length; i++) {
				cartao = new Cartao();
				
				if (tarefa.equals("atualizarCliente")) {
					if (req.getParameterValues("crtId")[i] != "") {
						cartao.setId(Long.parseLong(req.getParameterValues("crtId")[i]));
					} else {
						cartao.setId(null);
					}
				}
				
				cartao.setNome(req.getParameterValues("crtNome")[i]);
				cartao.setNumero(req.getParameterValues("crtNumero")[i]);
				cartao.setNomeImpresso(req.getParameterValues("crtNomeImpresso")[i]);
				cartao.setValidade(LocalDate.parse(req.getParameterValues("crtDtValidade")[i]));
				cartao.setBandeira(req.getParameterValues("crtBandeira")[i]);
				cartao.setCvc(Integer.parseInt(req.getParameterValues("crtCvc")[i]));
				cartao.setFavorito(Boolean.parseBoolean(req.getParameterValues("crtFavorito")[i]));
				
				cartoes.add(cartao);
			}
		}
		return cartoes;
	}
}
