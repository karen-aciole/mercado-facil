package com.ufcg.psoft.mercadofacil.model;
import java.math.BigDecimal;
import java.util.UUID;

public class Produto {
	
	private String id;
	
	private String nome; 
	
	private String fabricante;
	
	private BigDecimal preco;

	public Produto(String nome, String fabricante, BigDecimal preco) {
		this.id = UUID.randomUUID().toString();
		this.nome = nome;
		this.fabricante = fabricante;
		this.preco = preco;
	}
		
	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public String toString() {
		return "\nProduto: " + getNome() + " - Fabricante: " + getFabricante() + " - Preco: " + getPreco();
	}
}
