package com.ufcg.psoft.mercadofacil.model.usuario;


import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String cpf; 
	private String nome; 
	private String endereco; 
	private String telefone;
	private Carrinho carrinho;

	private List<Compra> compras;
	
	public Usuario(String cpf, String nome, String telefone, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.carrinho = new Carrinho();
		this.compras = new ArrayList<>();
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

	public void addCompra(Compra compra) {
		this.compras.add(compra);
	}
	public List<Compra> getCompras() {
		return compras;
	}
	
	@Override
	public String toString() {
		return "Usuário: " + getCpf() + " - Nome: " + getNome();
	}

}