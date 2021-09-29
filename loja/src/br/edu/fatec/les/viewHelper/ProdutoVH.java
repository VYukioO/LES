package br.edu.fatec.les.viewHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Produto;
import br.edu.fatec.les.facade.Resultado;

public class ProdutoVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Produto produto = new Produto();
		
		if (req.getParameter("prdId") != null) {
			produto.setId(Long.parseLong(req.getParameter("prdId")));
		}
		
		return produto;
	}

	@Override
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("consultarProdutos")) {
			List<Produto> produtos = new ArrayList<Produto>();
			Resultado resultado = new Resultado();
			
			resultado = (Resultado) req.getAttribute("resultado");
			
			for (AEntidade prd : resultado.getEntidades()) {
				Produto produto = (Produto) prd;
				produtos.add(produto);
			}
			
			String json = new Gson().toJson(produtos);
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		}
		if (tarefa.equals("consultarProduto")) {
			Resultado resultado = new Resultado();
			
			resultado = (Resultado) req.getAttribute("resultado");
			Produto prd = (Produto) resultado.getEntidades().get(0);
			
			req.setAttribute("produto", prd);
			req.getRequestDispatcher("produtoVisualizar.jsp").forward(req, resp);
		}
	}
}
