package com.ufcg.psoft.mercadofacil.model;

import java.util.Date;
import java.util.UUID;

public class Lote {
	
	private String id;
	
	private Produto produto;
	
	private int quantidade; 
	
	private Date dataDeValidade;
	
	
	public Lote(Produto produto, int quantidade, Date dataDeValidade) {
		
		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataDeValidade = dataDeValidade;
	}

	
	public String getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public Date getDataDeValidade() {
		return dataDeValidade;
	}
	
	
	@Override
	public String toString() {
		return "Lote ID: " + getId() + " - Produto: " + getProduto().getNome() 
				+ " - " + getQuantidade() + " itens" + " - Data de validade do lote: "
						+ getDataDeValidade();
	}
}
