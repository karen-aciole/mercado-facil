package com.ufcg.psoft.mercadofacil.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Usuario;

@Repository
public class UsuarioRepository {
	
	private Map<String, Usuario> usuarios; 
	
	public UsuarioRepository() {
		this.usuarios = new HashMap<String, Usuario>();
	}
	
	public Collection<Usuario> getAll() {
		return usuarios.values();
	}
	
	public Usuario getUser(String cpf) {
		return this.usuarios.get(cpf);
	}
	
	public void delUser(String cpf) { 
		this.usuarios.remove(cpf);
	}
	
	public void editUser(String cpf, Usuario user) {
		this.usuarios.replace(cpf, user);
	}
	
	public String addUser(Usuario user) {
		this.usuarios.put(user.getCpf(), user);
		return(user.getCpf());
	}
}
