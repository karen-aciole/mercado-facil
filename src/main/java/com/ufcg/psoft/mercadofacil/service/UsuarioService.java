package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.UsuarioDTO;
import com.ufcg.psoft.mercadofacil.exception.UsuarioAlreadyExists;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository userRepo; 
	
	// Queremos apenas o CPF e o nome dos usuários
	public List<String> listUsers() {
		List<String> users = new ArrayList<>();
		for (Usuario usuario : this.userRepo.getAll()) {
			String userList = "CPF do usuário: " + usuario.getCpf() + " - Nome do usuário: "+ usuario.getNome();
			users.add(userList); 
		}
		return users;
	}
	
	public String createUser(UsuarioDTO userDTO) throws UsuarioAlreadyExists  {

		if(userDTO.getCpf().length() != 11) throw new IllegalArgumentException("CPF inválido - deve conter 11 dígitos");

		if (userRepo.getUser(userDTO.getCpf())!= null) throw new UsuarioAlreadyExists("Usuário já está cadastrado!");

		Usuario usuario = new Usuario(userDTO.getCpf(), userDTO.getNome(), userDTO.getEndereco(), userDTO.getTelefone(),
				userDTO.getPerfil().toUpperCase());

		usuario.setDescontoDeAcordoComPerfil(userDTO.getPerfil().toUpperCase());

		this.userRepo.addUser(usuario);
		
		return usuario.getCpf();
	}
	
	public Usuario getUserById(String cpf) throws UsuarioNotFoundException { 
		Usuario user = this.userRepo.getUser(cpf);
		if (user == null) throw new UsuarioNotFoundException("Usuário: " + cpf + " não encontrado");
		
		return user;
	}
	
	public void editUser(UsuarioDTO usuarioDTO, Usuario usuario) throws UsuarioNotFoundException {
		usuario.setEndereco(usuarioDTO.getEndereco() != null ? usuarioDTO.getEndereco() : usuario.getEndereco());
		usuario.setTelefone(usuarioDTO.getTelefone() != null ? usuarioDTO.getTelefone() : usuario.getTelefone());

		if (usuario.getPerfil() != null) {
			usuario.setPerfil(usuarioDTO.getPerfil().toUpperCase());
			usuario.setDescontoDeAcordoComPerfil(usuarioDTO.getPerfil().toUpperCase());
		}
		usuario.setPerfil(usuario.getPerfil());

		this.userRepo.editUser(usuario.getCpf(), usuario);
	}
	
	public void deletUser(String cpf) throws UsuarioNotFoundException {
		Usuario user = this.userRepo.getUser(cpf);
		if (user == null) throw new UsuarioNotFoundException("Usuário: " + cpf + " não encontrado");
		this.userRepo.delUser(cpf);
	}
	
}
