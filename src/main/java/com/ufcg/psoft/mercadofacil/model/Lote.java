package com.ufcg.psoft.mercadofacil.model;
import java.util.Date;
import java.util.UUID;

public class Lote {
	
	private String id;
	
	private Produto produto;
	
	private int quantidade; 
	
	private Date dataFabricacao;
	
	private Date dataValidade; 
	
	
	public Lote(Produto produto, int quantidade) {
		
		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
	}
	
	public void updateDataLote(Produto produto, int quantidade, Date dataFabricacao, Date dataValidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataFabricacao = dataFabricacao;
		this.dataValidade = dataValidade;

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

	public Date getDataFabricacao() {
		return dataFabricacao;
	}
	
	public void setDataFabricacao(Date dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}
	
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	@Override
	public String toString() {
		return "Lote ID: " + getId() + " - Produto: " + getProduto().getNome() + " - " + getQuantidade() + " itens" +
				"- Data de Fabricacao" + getDataFabricacao() + "- Data de validade" + getDataValidade();
	}
}
