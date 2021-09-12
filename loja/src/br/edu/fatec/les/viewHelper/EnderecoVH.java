package br.edu.fatec.les.viewHelper;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.enums.TipoEndereco;
import br.edu.fatec.les.dominio.enums.TipoLogradouro;
import br.edu.fatec.les.dominio.enums.TipoResidencia;
import br.edu.fatec.les.dominio.modelo.Cliente;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.facade.Resultado;

public class EnderecoVH implements IViewHelper {

	@Override
	public AEntidade getEntidade(HttpServletRequest req) {
		Endereco endereco = new Endereco();
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("adicionarEndereco") || tarefa.equals("adicionarEnderecoLista")) {
			ClienteVH clienteVh = new ClienteVH();
			endereco.setNome(req.getParameter("endNome"));
			endereco.setLogradouro(req.getParameter("endLogradouro"));
			endereco.setNumero(Integer.parseInt(req.getParameter("endNumero")));
			endereco.setBairro(req.getParameter("endBairro"));
			endereco.setCep(req.getParameter("endCep"));
			endereco.setCidade(req.getParameter("endCidade"));
			endereco.setEstado(req.getParameter("endEstado"));
			endereco.setPais(req.getParameter("endPais"));
			endereco.setComplemento(req.getParameter("endComplemento"));
			endereco.setTipoResidencia(TipoResidencia.valueOf(req.getParameter("endTipoResidencia")));
			endereco.setTipoLogradouro(TipoLogradouro.valueOf(req.getParameter("endTipoLogradouro")));
			endereco.setTipoEndereco(TipoEndereco.valueOf(req.getParameter("endTipoEndereco")));
			endereco.setAtivo(Boolean.parseBoolean(req.getParameter("endAtivo")));
			endereco.setCliente((Cliente) clienteVh.getEntidade(req));
			
			endereco.setCidade("endCidade");
		} else if (tarefa.equals("consultarEndereco")) {
			ClienteVH clienteVh = new ClienteVH();
			endereco.setCliente((Cliente) clienteVh.getEntidade(req));
			endereco.setId(Long.parseLong(req.getParameter("endId")));
			endereco.setAtivo(Boolean.parseBoolean(req.getParameter("endAtivo")));
		} else {
			endereco.setId(Long.parseLong(req.getParameter("endId")));
		}
		return endereco;
	}

	@Override
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tarefa = req.getParameter("tarefa");
		
		if (tarefa.equals("consultarEndereco")) {
			Resultado resultado = new Resultado();
			
			resultado = (Resultado) req.getAttribute("resultado");
			
			String json = new Gson().toJson(resultado);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} else if (tarefa.equals("adicionarEnderecoLista") || tarefa.equals("removerEnderecoLista")) {
			req.getRequestDispatcher("alterarEndereco.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("pagamento.jsp");
			// ??????
		}
	}
	
	public ArrayList<Endereco> getEntidades(HttpServletRequest req){
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		Endereco endereco = new Endereco();
		String tarefa = req.getParameter("tarefa");
		
		if (req.getParameterValues("end") == null) {
			return enderecos;
		} else {
			String[] enderecosForm = req.getParameterValues("end");
			for (int i = 0; i < enderecosForm.length; i++) {
				endereco = new Endereco();
				
				if (tarefa.equals("atualizarCliente")) {
					if (req.getParameterValues("endId")[i] != "") {
						endereco.setId(Long.parseLong(req.getParameterValues("endId")[i]));
					} else {
						endereco.setId(null);						
					}
					
					endereco.setNome(req.getParameterValues("endNome")[i]);
					endereco.setLogradouro(req.getParameterValues("endLogradouro")[i]);
					endereco.setNumero(Integer.parseInt(req.getParameterValues("endNumero")[i]));
					endereco.setBairro(req.getParameterValues("endBairro")[i]);
					endereco.setCep(req.getParameterValues("endCep")[i]);
					endereco.setCidade(req.getParameterValues("endCidade")[i]);
					endereco.setEstado(req.getParameterValues("endEstado")[i]);
					endereco.setPais(req.getParameterValues("endPais")[i]);
					endereco.setComplemento(req.getParameterValues("endComplemento")[i]);
					endereco.setTipoResidencia(TipoResidencia.valueOf(req.getParameterValues("endTipoResidencia")[i]));
					endereco.setTipoLogradouro(TipoLogradouro.valueOf(req.getParameterValues("endTipoLogradouro")[i]));
					endereco.setTipoEndereco(TipoEndereco.valueOf(req.getParameterValues("endTipoEndereco")[i]));
					endereco.setAtivo(Boolean.parseBoolean(req.getParameterValues("endAtivo")[i]));
					
					enderecos.add(endereco);
				}
			}
		}
		return enderecos;
	}

}
