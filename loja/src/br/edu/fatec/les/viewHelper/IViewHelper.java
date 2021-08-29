package br.edu.fatec.les.viewHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.les.dominio.AEntidade;

public interface IViewHelper {
	public AEntidade getEntidade(HttpServletRequest req);
	public void setEntidade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
