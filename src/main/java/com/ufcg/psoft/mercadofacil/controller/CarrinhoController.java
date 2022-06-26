package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
	@RequestMapping(value = "carrinho/{idUsuario}/addItem/", method = RequestMethod.POST)
	public ResponseEntity<?> adicionaProdutoNoCarrinho(@PathVariable ("idUsuario") String idUsuario, @RequestBody ItemCompraDTO itemCompraDTO, UriComponentsBuilder ucBuilder)
			throws ProductNotFoundException, UsuarioNotFoundException, QuantidadeInvalidaException {

		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NO_CONTENT);

		if (itemCompraDTO.getQuantidade() <= 0) return new ResponseEntity<String> ("Quantidade inválida", HttpStatus.BAD_REQUEST);
		Produto produto = produtoService.getProdutoById(itemCompraDTO.getIdProduto());
		if (produto == null) return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);

		carrinhoService.adicionaItensNoCarrinho(user, itemCompraDTO);

		return new ResponseEntity<String>("Item adicionado no carrinho!", HttpStatus.OK);
	}

	@RequestMapping(value = "carrinho/{idUsuario}/removeItem/", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeProdutoDoCarrinho(@PathVariable ("idUsuario") String idUsuario, @RequestBody ItemCompraDTO itemCompraDTO)
			throws ProductNotFoundException, UsuarioNotFoundException, QuantidadeInvalidaException, LoteNotFoundException {

		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NO_CONTENT);

		if (itemCompraDTO.getQuantidade() <= 0) return new ResponseEntity<String> ("Quantidade inválida", HttpStatus.BAD_REQUEST);

		Produto produto = produtoService.getProdutoById(itemCompraDTO.getIdProduto());
		if (produto == null) return new ResponseEntity<String>("Produto não existe no carrinho", HttpStatus.NO_CONTENT);

		carrinhoService.removeItensDoCarrinho(user, itemCompraDTO);

		return new ResponseEntity<String>("Item removido do carrinho!", HttpStatus.OK);
	}


	@RequestMapping(value = "carrinho/{idUsuario}/finalizar", method = RequestMethod.POST)
	public ResponseEntity<?> finalizarCarrinho(@PathVariable ("idUsuario") String idUsuario) throws UsuarioNotFoundException {
		Usuario user = usuarioService.getUserById(idUsuario);
		Compra compra;
		if (user == null) return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NO_CONTENT);

		try {
			compra = carrinhoService.finalizaCarrinho(user);
		} catch (CarrinhoVazioException e) {
			return new ResponseEntity<String>("Carrinho vazio", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Carrinho finalizado com sucesso!\n" + compra, HttpStatus.OK);
	}

	@RequestMapping(value = "carrinho/{idUsuario}/descartar", method = RequestMethod.DELETE)
	public ResponseEntity<?> descartaCarrinho(@PathVariable ("idUsuario") String idUsuario) throws UsuarioNotFoundException, LoteNotFoundException {
		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NO_CONTENT);

		try {
			carrinhoService.descartaCarrinho(user);
		} catch (CarrinhoVazioException e) {
			return new ResponseEntity<String>("Carrinho vazio", HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<String>("Carrinho descartado!", HttpStatus.OK);
	}
	
}
