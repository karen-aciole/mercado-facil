package com.ufcg.psoft.mercadofacil.dto;

import java.math.BigDecimal;

public class ProdutoDTO {
	
	private String nome; 
	
	private String fabricante;
	
	private BigDecimal preco;

	public ProdutoDTO(String nome, String fabricante, BigDecimal preco) {
		this.nome = nome;
		this.fabricante = fabricante;
		this.preco = preco;
	}


	public String getNome() {
		return nome;
	}

	public String getFabricante() {
		return fabricante;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public String toString() {
		return "Produto: " + getNome() + " - Fabricante: " + getFabricante() 
		+ " - Preco" + getPreco();
	}
}