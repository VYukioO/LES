package br.edu.fatec.les.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.les.command.AtualizarCommand;
import br.edu.fatec.les.command.ConsultarCommand;
import br.edu.fatec.les.command.DeletarCommand;
import br.edu.fatec.les.command.ICommand;
import br.edu.fatec.les.command.SalvarCommand;
import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.facade.Resultado;
import br.edu.fatec.les.viewHelper.CartaoVH;
import br.edu.fatec.les.viewHelper.ClienteVH;
import br.edu.fatec.les.viewHelper.EnderecoVH;
import br.edu.fatec.les.viewHelper.IViewHelper;
import br.edu.fatec.les.viewHelper.ProdutoVH;
import br.edu.fatec.les.viewHelper.UsuarioVH;

@WebServlet(name = "servletController", urlPatterns = "/app")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, ICommand> commandMap;
	private static Map<String, IViewHelper> vhMap;
	private static String tarefa = null;
	private static IViewHelper vh;
    
    public ServletController() {
        commandMap = new HashMap<String, ICommand>();
        vhMap = new HashMap<String, IViewHelper>();

        //Usuario
        vhMap.put("loginUsuario", new UsuarioVH());
        vhMap.put("logoutUsuario", new UsuarioVH());
        vhMap.put("alterarSenha", new UsuarioVH());
        commandMap.put("loginUsuario", new ConsultarCommand());
        commandMap.put("logoutUsuario", new ConsultarCommand());
        commandMap.put("alterarSenha", new AtualizarCommand());
        
        //Cliente
        vhMap.put("cadastrarCliente", new ClienteVH());
        vhMap.put("consultarCliente", new ClienteVH());
        vhMap.put("atualizarCliente", new ClienteVH());
        vhMap.put("deletarCliente", new ClienteVH());
        vhMap.put("editarCliente", new ClienteVH());
        commandMap.put("cadastrarCliente", new SalvarCommand());
        commandMap.put("consultarCliente", new ConsultarCommand());
        commandMap.put("atualizarCliente", new AtualizarCommand());
        commandMap.put("deletarCliente", new DeletarCommand());
        commandMap.put("editarCliente", new ConsultarCommand());
        
        //Endereco
        vhMap.put("adicionarEndereco", new EnderecoVH());
        vhMap.put("removerEndereco", new EnderecoVH());
        vhMap.put("consultarEndereco", new EnderecoVH());
        vhMap.put("adicionarEnderecoLista", new EnderecoVH());
        vhMap.put("removerEnderecoLista", new EnderecoVH());
        commandMap.put("adicionarEndereco", new SalvarCommand());
        commandMap.put("removerEndereco", new DeletarCommand());
        commandMap.put("consultarEndereco", new ConsultarCommand());
        commandMap.put("adicionarEnderecoLista", new SalvarCommand());
        commandMap.put("removerEnderecoLista", new DeletarCommand());
        
        //Cartao
        vhMap.put("adicionarCartao", new CartaoVH());
        vhMap.put("removerCartao", new CartaoVH());
        commandMap.put("adicionarCartao", new SalvarCommand());
        commandMap.put("removerCartao", new DeletarCommand());
        
        //Produto
        vhMap.put("consultarProduto", new ProdutoVH());
        vhMap.put("consultarProdutos", new ProdutoVH());
        commandMap.put("consultarProduto", new ConsultarCommand());
        commandMap.put("consultarProdutos", new DeletarCommand());
        
    }
    
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
    	resp.setContentType("txt/html;charset=UTF-8");
    	
    	try {
			tarefa = req.getParameter("tarefa");
			Resultado resultado;
			vh = vhMap.get(tarefa);
			AEntidade entidade = vh.getEntidade(req);
			ICommand command = commandMap.get(tarefa);
			resultado = command.execute(entidade);
			
			req.setAttribute("resultado", resultado);
			
			vh.setEntidade(req, resp);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
    }
}
