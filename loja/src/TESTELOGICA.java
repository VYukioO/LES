import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Endereco;
import br.edu.fatec.les.facade.MensagemStatus;

public class TESTELOGICA {

	public static void main(String[] args) {

		Endereco endereco = new Endereco();
		List<Endereco> BDEndereco = new ArrayList<Endereco>();
		
		endereco = new Endereco();
		endereco.setId((long) 1);
		endereco.setLogradouro("RUA " + 1);
		BDEndereco.add(endereco);

		endereco = new Endereco();
		endereco.setId((long) 2);
		endereco.setLogradouro("RUA " + 2);
		BDEndereco.add(endereco);

		endereco = new Endereco();
		endereco.setId((long) 3);
		endereco.setLogradouro("RUA " + 3);
		BDEndereco.add(endereco);
		
		endereco = new Endereco();
		endereco.setId((long) 4);
		endereco.setLogradouro("RUA " + 4);
		BDEndereco.add(endereco);


		List<Endereco> cliEnderecos = new ArrayList<Endereco>();
		
		endereco = new Endereco();
		endereco.setId((long) 1);
		endereco.setLogradouro("RUA " + 1);
		cliEnderecos.add(endereco);

		endereco = new Endereco();
		endereco.setId((long) 2);
		endereco.setLogradouro("RUA " + 2);
		cliEnderecos.add(endereco);
		
		endereco = new Endereco();
		endereco.setId();
		endereco.setLogradouro("RUA " + 7);
		cliEnderecos.add(endereco);
		
		/*
		for (Endereco bde : BDEndereco) {
			boolean excluir = true;
			for (Endereco e : cliEnderecos) {
				if (e.getId() != null) {
					if (e.getId() != bde.getId()) {
						continue;
					} else {
						excluir = false;
						break;
					}
				}
			}
			if (excluir) {
				BDEndereco.remove(Bade);
			}
		}
*/
		

		for (Endereco end : cliEnderecos) {

			if (end.getId() == null) {
				BDEndereco.add(end);
			} else {
				boolean excluir = true;
				for (Endereco bdend : BDEndereco) {
					if (end.getId() != bdend.getId()) {
						continue;
					} else {
						excluir = false;
						break;
					}
				}
				if (excluir) {
					BDEndereco.remove(bdend);
				}
			}
		}

		boolean a = false;
		if (a) {
			System.out.println("Acasca");
		}
	}

}
