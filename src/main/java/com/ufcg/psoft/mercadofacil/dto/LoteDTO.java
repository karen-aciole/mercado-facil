package com.ufcg.psoft.mercadofacil.dto;

import java.util.Date;

public class LoteDTO {
	
	private String idProduto;
	
	private int quantidade;

	private String dataFabricacao;

	private String dataValidade; 
	
	
	public LoteDTO(String idProduto, int quantidade) {
		
		this.idProduto = idProduto;
		this.quantidade = quantidade;
	}
	
	public String getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public String getDataFabricacao() {
		return dataFabricacao;
	}
	
	public String getDataValidade() {
		return dataValidade;
	}
	
	public String toString() { 
		return "ID do produto: " + getIdProduto() + " - Quantidade" + getQuantidade();
	}
}
