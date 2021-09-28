package br.edu.fatec.les.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.les.dominio.AEntidade;
import br.edu.fatec.les.dominio.modelo.Categoria;
import br.edu.fatec.les.dominio.modelo.Estoque;
import br.edu.fatec.les.dominio.modelo.Imagem;
import br.edu.fatec.les.dominio.modelo.Precificacao;
import br.edu.fatec.les.dominio.modelo.Produto;
import br.edu.fatec.les.facade.Mensagem;
import br.edu.fatec.les.util.ConnectionFactory;

public class ProdutoDAO implements IDao{
	
	private Connection conn = null;
	ImagemDAO imagemDao = new ImagemDAO();
	PrecificacaoDAO precificacaoDao = new PrecificacaoDAO();
	CategoriaDAO categoriaDao = new CategoriaDAO();
	EstoqueDAO estoqueDao = new EstoqueDAO();

	@Override
	public Mensagem salvar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação não suportada.");
	}

	@Override
	public Mensagem atualizar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação não suportada.");
	}

	@Override
	public Mensagem deletar(AEntidade entidade) throws SQLException {
		throw new UnsupportedOperationException("Operação não suportada.");
	}

	@Override
	public List<AEntidade> consultar(AEntidade entidade) throws SQLException {
		Produto produto = (Produto) entidade;
		conn = ConnectionFactory.getConnection();
		List<AEntidade> produtos = new ArrayList<AEntidade>();
		List<AEntidade> cats = new ArrayList<AEntidade>();
		List<Categoria> categorias = new ArrayList<Categoria>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "pro_id, "
				+ "pro_nome, "
				+ "pro_marca, "
				+ "pro_preco, "
				+ "pro_descricao, "
				+ "pro_img_id"
				+ "pro_pre_id, "
				+ "pro_ativo, "
				+ "pro_dt_cadastro, "
				+ "pro_dt_atualizacao "
				+ "FROM tb_produtos "
				+ "WHERE pro_ativo = 1 ";
		if (produto.getId() != null) {
			sql += " AND pro_id = " + produto.getId() + " ";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			Produto pro = new Produto();
			Imagem img = new Imagem();
			Precificacao pre = new Precificacao();
			cats = new ArrayList<AEntidade>();
			categorias = new ArrayList<Categoria>();
			
			while (rs.next()) {
				pro = new Produto();
				img = new Imagem();
				pre = new Precificacao();
				
				
				pro.setId(rs.getLong("pro_id"));
				pro.setPreco(rs.getFloat("pro_preco"));
				pro.setDescricao(rs.getString("pro_descricao"));
				pro.setAtivo(rs.getBoolean("pro_ativo"));
				pro.setDtCadastro(rs.getObject("pro_dt_cadastro", LocalDateTime.class));
				pro.setDtAtualizacao(rs.getObject("pro_dt_atualizacao", LocalDateTime.class));
				
				img.setId(rs.getLong("pro_img_id"));
				pro.setImagem((Imagem) imagemDao.consultar(img).get(0));
				
				pre.setId(rs.getLong("pro_pre_id"));
				pro.setPrecificacao((Precificacao) precificacaoDao.consultar(pre).get(0));
				
				cats.addAll(categoriaDao.consultar(pro));
				for (AEntidade cat : cats) {
					categorias.add((Categoria) cat);
				}
				pro.setCategorias(categorias);
				
				
				pro.setEstoque((Estoque) estoqueDao.consultar(pro).get(0));
				
				produtos.add(pro);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
		}
		return produtos;
	}

}
