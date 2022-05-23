package com.ufcg.psoft.mercadofacil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
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
	
	public String addLote(String jsonData) throws ProductNotFoundException {
		LoteDTO loteDTO = gson.fromJson(jsonData, LoteDTO.class);
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		if(prod == null) throw new ProductNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");
		Lote lote = new Lote(prod, loteDTO.getQuantidade());
		this.loteRep.addLote(lote);

		return lote.getId();
	}
}
