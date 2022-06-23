package com.ufcg.psoft.mercadofacil.dto;

import java.util.Date;

public class LoteDTO {
	
	private String idProduto;
	
	private int quantidade;
	
	private Date dataDeValidade;
	
	
	public LoteDTO(String idProduto, int quantidade, Date dataDeValidade) {
		
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
	
	public Date getDataDeValidade() { 
		return dataDeValidade; 
	}
	
	public String toString() { 
		return "ID do produto: " + getIdProduto() + " - Quantidade" + getQuantidade() + 
				" - Data de validade: " + getDataDeValidade();
	}
}
