package com.ufcg.psoft.mercadofacil.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository prodRep;

	public List<Produto> listarProdutos() {
		return new ArrayList<>(prodRep.getAll());
	}
	
	public List<Produto> listarProdsLoteByName(String nome) {
		List<Produto> prods = getProdsWithLote();
		return getProdsByName(nome, prods);
	}

	public List<Produto> listarProdsByName(String nome) {
		return getProdsByName(nome, this.prodRep.getAll());
	}

	private List<Produto> getProdsByName(String nome, Collection<Produto> prods) {
		List<Produto> prodsResult = new ArrayList<>();
		for (Produto produto : prods) {
			if(produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
				prodsResult.add(produto);
			}
		}	
		return(prodsResult);
	}
	
	private List<Produto> getProdsWithLote() {
		List<Produto> prods = new ArrayList<>();
		for (Lote lote : this.loteRep.getAll()) {
			if (checkIfProductExists(lote.getId()))
				prods.add(lote.getProduto());
		}
		return(prods);
	}


	public String addProduto(ProdutoDTO prodDTO) {
		if (prodDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Preço inválido");
		}
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		
		this.prodRep.addProduto(produto);
		
		return produto.getId();
	}
	
	public Produto getProdutoById(String id) throws ProductNotFoundException {
		Produto prod = this.prodRep.getProd(id);
		if(prod == null) throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		
		return(prod);
	}
	
	
	public void editProduto(ProdutoDTO prodDTO, Produto produto) throws ProductNotFoundException { 
		produto.setFabricante(!prodDTO.getFabricante().isBlank() ? prodDTO.getFabricante() : produto.getFabricante());
		produto.setNome(!prodDTO.getNome().isBlank() ? prodDTO.getNome() : produto.getNome());
		produto.setPreco(prodDTO.getPreco().compareTo(BigDecimal.ZERO) > 0 ? prodDTO.getPreco() : produto.getPreco());
		this.prodRep.editProd(produto.getId(), produto);
	}
	
	public void deletProduto(String id) throws ProductNotFoundException {
		Produto prod = this.prodRep.getProd(id);
		if(prod == null) throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		this.prodRep.delProd(id);
	}
	
	private boolean checkIfProductExists(String idLote) {
		Lote lote = this.loteRep.getLote(idLote);
		String produtoID = lote.getProduto().getId();
		Produto produto = this.prodRep.getProd(produtoID);
		if (produto == null) {
			this.loteRep.delLot(idLote);
			return false;
		}
		return true;
	}
}
