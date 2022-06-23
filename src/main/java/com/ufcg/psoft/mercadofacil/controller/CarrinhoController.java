package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.service.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoController {
	
	@Autowired
	UsuarioService usuarioService; 

	@Autowired
	CarrinhoService carrinhoService;
	
	@Autowired
	ProdutoService produtoService;
	
	@RequestMapping(value = "carrinho/add/", method = RequestMethod.POST)
	public ResponseEntity<?> adicionaProdutoNoCarrinho(@RequestBody String idUsuario, @RequestBody String idProduto, @RequestBody int quantidade, UriComponentsBuilder ucBuilder)
			throws ProductNotFoundException, UsuarioNotFoundException {
		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) {
			throw new UsuarioNotFoundException("Usuario não encontrado");
		}
		Produto produto = produtoService.getProdutoById(idProduto);
		if (produto == null) {
			throw new ProductNotFoundException("Produto não encontrado");
		}

		carrinhoService.adicionaProdutoNoCarrinho(produto, quantidade);

		return new ResponseEntity<String>("Produto adicionado no carrinho " + user.getCarrinho(), HttpStatus.CREATED);
	}
	
}
