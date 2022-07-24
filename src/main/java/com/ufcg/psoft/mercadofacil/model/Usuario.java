package com.ufcg.psoft.mercadofacil.model;


import com.ufcg.psoft.mercadofacil.model.Perfil.Especial;
import com.ufcg.psoft.mercadofacil.model.Perfil.Normal;
import com.ufcg.psoft.mercadofacil.model.Perfil.Perfil;
import com.ufcg.psoft.mercadofacil.model.Perfil.Premium;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String perfil;
	private String cpf;
	private String nome; 
	private String endereco; 
	private String telefone;
	private Carrinho carrinho;

	private List<Compra> compras;
	private BigDecimal desconto;
	
	public Usuario(String cpf, String nome, String telefone, String endereco, String perfil) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.carrinho = new Carrinho();
		this.compras = new ArrayList<>();
		this.perfil = perfil;
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

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public void setDescontoDeAcordoComPerfil(String perfil) {
		BigDecimal desconto;
		switch (this.perfil) {
			case "NORMAL":
				Perfil normal = new Normal();
				this.desconto = normal.aplicaDesconto();
				break;

			case "ESPECIAL":
				Perfil especial = new Especial();
				this.desconto = especial.aplicaDesconto();
				break;

			case "PREMIUM":
				Perfil premium = new Premium();
				this.desconto = premium.aplicaDesconto();
				break;
		}
	}
	public BigDecimal getDescontoDeAcordoComPerfil() {
		return desconto;
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