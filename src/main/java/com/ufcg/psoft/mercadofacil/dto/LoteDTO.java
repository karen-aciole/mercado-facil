package com.ufcg.psoft.mercadofacil.dto;

import java.time.LocalDate;


public class LoteDTO {
	
	private String idProduto;
	
	private int quantidade;
	
	private LocalDate dataDeValidade;
	
	
	public LoteDTO(String idProduto, int quantidade, LocalDate dataDeValidade) {
		
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.dataDeValidade = dataDeValidade;
	}
	
	public String getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public LocalDate getDataDeValidade() {
		return dataDeValidade; 
	}
	
	public String toString() { 
		return "ID do produto: " + getIdProduto() + " - Quantidade" + getQuantidade() + 
				" - Data de validade: " + getDataDeValidade();
	}
}
