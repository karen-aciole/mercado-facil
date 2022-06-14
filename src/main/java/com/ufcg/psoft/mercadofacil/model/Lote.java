package com.ufcg.psoft.mercadofacil.model;
import java.util.UUID;

public class Lote {
	
	private String id;
	
	private Produto produto;
	
	private int quantidade; 
	
	
	public Lote(Produto produto, int quantidade) {
		
		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
	}

	
	public String getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
	@Override
	public String toString() {
		return "Lote ID: " + getId() + " - Produto: " + getProduto().getNome() 
				+ " - " + getQuantidade() + " itens";
	}
}
