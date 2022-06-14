package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class LoteService {

	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository produtoRep;
	
	private Gson gson = new Gson();
	
	private List<Lote> listarLotes() {
		return new ArrayList<Lote>(loteRep.getAll());
	}
	
	public List<Lote> listaLotes() {
		List<Lote> lotesResult = new ArrayList<Lote>();
		for (Lote lote : this.listarLotes()) {
			if (checkIfProductExists(lote.getId()) == true)
				lotesResult.add(lote);
		}
		return(lotesResult);
	}
	
	public String addLote(String jsonData) throws ProductNotFoundException {
		
		LoteDTO loteDTO = gson.fromJson(jsonData, LoteDTO.class);
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		if(prod == null) throw new ProductNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");
		Lote lote = new Lote(prod, loteDTO.getQuantidade());
		this.loteRep.addLote(lote);
		
		return lote.getId();
	}
	
	public void deletLote(String id) throws LoteNotFoundException { 
		this.loteRep.delLot(id);
	}
	
	public Lote getLoteById(String id) throws LoteNotFoundException {
		boolean hasProduct = checkIfProductExists(id);
		Lote lote = this.loteRep.getLote(id);
		if (lote == null || hasProduct == false) throw new LoteNotFoundException("Lote: " + id + "não encontrado");
		
		return(lote);
	}
	
	public void editLote(LoteDTO loteDTO, Lote lote) throws LoteNotFoundException {
		lote.setQuantidade(loteDTO.getQuantidade() >= 0 ? loteDTO.getQuantidade() : lote.getQuantidade());
		this.loteRep.editLote(lote.getId(), lote);
	}
	
	private boolean checkIfProductExists(String idLote) {
		Lote lote = this.loteRep.getLote(idLote);
		String produtoID = lote.getProduto().getId();
		Produto produto = this.produtoRep.getProd(produtoID);
		if (produto == null) {
			this.loteRep.delLot(idLote);
			return false;
		}
		return true;
	}
}
