package com.ufcg.psoft.mercadofacil.dto;

public class UsuarioDTO {
	
	private String cpf;
	private String nome;
	private String endereco;
	private String telefone;
	private String perfil;
	
	public UsuarioDTO(String cpf, String nome, String telefone, String endereco, String perfil) {
		this.cpf = cpf; 
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
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
	
	public String getTelefone() {
		return telefone;
	}
	public String getPerfil() {
		return perfil;
	}

	@Override
	public String toString() {
		return "Usuário: " + getCpf() + " - Nome: " + getNome();
	}

}
