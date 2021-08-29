package br.edu.fatec.les.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.les.command.ICommand;
import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.facade.Resultado;
import br.edu.fatec.les.viewHelper.IViewHelper;

@WebServlet(name = "servletController", urlPatterns = "/app")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, ICommand> commandMap;
	private static Map<String, IViewHelper> vhMap;
	private static String tarefa = null;
	private static IViewHelper vh;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletController() {
        super();
        commandMap = new HashMap<String, ICommand>();
        vhMap = new HashMap<String, IViewHelper>();
        
    }
    
    protected void service(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException ,IOException {
    	resp.setContentType("txt/html;charset=URF-8");
    	
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
