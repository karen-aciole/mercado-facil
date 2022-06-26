package com.ufcg.psoft.mercadofacil.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.InvalidDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	private List<Lote> listarLotes() {
		return new ArrayList<>(loteRep.getAll());
	}
	
	public List<Lote> listaLotes() {
		List<Lote> lotesResult = new ArrayList<>();
		for (Lote lote : this.listarLotes()) {
			if (checkIfProductExists(lote.getId()))
				lotesResult.add(lote);
		}
		return(lotesResult);
	}
	
	public String addLote(LoteDTO loteDTO) throws ProductNotFoundException, InvalidDateException {
		
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		if(prod == null) throw new ProductNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");

		if (loteDTO.getDataDeValidade().isBefore(LocalDate.now())) throw new InvalidDateException("Data inválida");

		Lote lote = new Lote(prod, loteDTO.getQuantidade(), loteDTO.getDataDeValidade());
		this.loteRep.addLote(lote);
		
		return lote.getId();
	}
	
	public void deletLote(String id) throws LoteNotFoundException { 
		this.loteRep.delLot(id);
	}
	
	public Lote getLoteById(String id) throws LoteNotFoundException {
		@SuppressWarnings("unused")
		boolean hasProduct = checkIfProductExists(id);
		if (!hasProduct) throw new LoteNotFoundException("Lote: " + id + "não encontrado");
		Lote lote = this.loteRep.getLote(id);
		if (lote == null) throw new LoteNotFoundException("Lote: " + id + "não encontrado");
		
		return(lote);
	}

	public List<Lote> getLotesByProduct(Produto produto) { // Lista todos os lotes de um produto
		List<Lote> lotesResult = listaLotes();
		for (Lote lote : this.listaLotes()) {
			if (lote.getProduto().equals(produto))
				lotesResult.add(lote);
		}
		return lotesResult;
	}

	public Lote getLoteClosestToExpirationDate(Produto produto, int quantidade) {// Retorna o lote do produto mais próximo a data de validade
		List<Lote> lotesOrdenadedByExpirationDate = this.getLotesByProduct(produto);
		lotesOrdenadedByExpirationDate.sort(Comparator.comparing(Lote::getDataDeValidade));
		Collections.reverse(lotesOrdenadedByExpirationDate);

		for (Lote lote : lotesOrdenadedByExpirationDate) {
			if (lote.getQuantidade() > 0 && lote.getQuantidade() >= quantidade)
				return lote;
		}
		return null;
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
