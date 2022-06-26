package com.ufcg.psoft.mercadofacil.model;

public class Usuario {
	
	private String cpf; 
	private String nome; 
	private String endereco; 
	private String telefone;
	private Carrinho carrinho;
	
	public Usuario(String cpf, String nome, String telefone, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.carrinho = new Carrinho();
	}

	public String getCpf() {
		return cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	@Override
	public String toString() {
		return "Usuário: " + getCpf() + " - Nome: " + getNome();
	}

}