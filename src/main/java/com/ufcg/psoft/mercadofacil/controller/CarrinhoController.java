package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
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
	public ResponseEntity<?> adicionaProdutoNoCarrinho(@PathVariable ("idUsuario") String idUsuario, @RequestBody ItemCompraDTO itemCompraDTO, UriComponentsBuilder ucBuilder) throws UsuarioNotFoundException, ProductNotFoundException, QuantidadeInvalidaException {

		Usuario user;
		Produto produto;

		try {
			user = usuarioService.getUserById(idUsuario);
		} catch (UsuarioNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}

		if (itemCompraDTO.getQuantidade() <= 0) return new ResponseEntity<String> ("Quantidade inválida", HttpStatus.BAD_REQUEST);

		try {
			produtoService.getProdutoById(itemCompraDTO.getIdProduto());
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}

		try {
			carrinhoService.adicionaItensNoCarrinho(user, itemCompraDTO);
		} catch (LoteNotFoundException e){
			return new ResponseEntity<String>("Produto sem estoque.", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("Item adicionado no carrinho!", HttpStatus.OK);
	}

	@RequestMapping(value = "carrinho/{idUsuario}/removeItem/", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeProdutoDoCarrinho(@PathVariable ("idUsuario") String idUsuario, @RequestBody ItemCompraDTO itemCompraDTO) {
		Usuario user;

		try {
			user = usuarioService.getUserById(idUsuario);
		} catch (UsuarioNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		Carrinho carrinho = user.getCarrinho();

		try {
			ItemCompra item = carrinho.getItemNoCarrinho(produtoService.getProdutoById(itemCompraDTO.getIdProduto()));
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não existe no carrinho", HttpStatus.NO_CONTENT);
		}

		if (itemCompraDTO.getQuantidade() <= 0) return new ResponseEntity<String> ("Quantidade inválida", HttpStatus.BAD_REQUEST);

		try {
			carrinhoService.removeItensDoCarrinho(user, itemCompraDTO);
		} catch (QuantidadeInvalidaException | LoteNotFoundException | CarrinhoVazioException e) {
			return new ResponseEntity<String>("Solicitação inválida!", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Item removido do carrinho!", HttpStatus.OK);
	}

	@RequestMapping(value = "carrinho/{idUsuario}/descartar", method = RequestMethod.DELETE)
	public ResponseEntity<?> descartaCarrinho(@PathVariable ("idUsuario") String idUsuario) throws LoteNotFoundException {
		Usuario user;
		try {
			user = usuarioService.getUserById(idUsuario);
		} catch (UsuarioNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		try {
			carrinhoService.descartaCarrinho(user);
		} catch (CarrinhoVazioException e) {
			return new ResponseEntity<String>("Carrinho vazio", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Carrinho descartado!", HttpStatus.OK);
	}
	
}
