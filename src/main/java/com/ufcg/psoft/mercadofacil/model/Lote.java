package com.ufcg.psoft.mercadofacil.model;

import java.time.LocalDate;
import java.util.UUID;

public class Lote {
	
	private String id;
	
	private Produto produto;
	
	private int quantidade; 
	
	private LocalDate dataDeValidade;
	
	
	public Lote(Produto produto, int quantidade, LocalDate dataDeValidade) {
		
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
	
	public LocalDate getDataDeValidade() {
		return dataDeValidade;
	}
	
	
	@Override
	public String toString() {
		return "Lote ID: " + getId() + " - Produto: " + getProduto().getNome() 
				+ " - " + getQuantidade() + " itens" + " - Data de validade do lote: "
						+ getDataDeValidade();
	}
}
