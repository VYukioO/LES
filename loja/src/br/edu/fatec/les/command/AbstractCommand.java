package br.edu.fatec.les.command;

import br.edu.fatec.les.facade.Facade;
import br.edu.fatec.les.facade.IFacade;

public abstract class AbstractCommand implements ICommand {
	public IFacade facade = new Facade();

}
