package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeInvalidaException;
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
	@RequestMapping(value = "carrinho/{idUsuario}/addItem/", method = RequestMethod.POST)
	public ResponseEntity<?> adicionaProdutoNoCarrinho(@PathVariable ("idUsuario") String idUsuario, @RequestBody ItemCompraDTO itemDTO, UriComponentsBuilder ucBuilder)
			throws ProductNotFoundException, UsuarioNotFoundException {

		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) throw new UsuarioNotFoundException("Usuario não encontrado");

		Produto produto = produtoService.getProdutoById(itemDTO.getIdProduto());
		if (produto == null) throw new ProductNotFoundException("Produto não encontrado");

		carrinhoService.adicionaItensNoCarrinho(user, itemDTO);

		return new ResponseEntity<String>("Item adicionado no carrinho!" + user.getCarrinho(), HttpStatus.OK);
	}

	@RequestMapping(value = "carrinho/{idUsuario}/removeItem/", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeProdutoDoCarrinho(@PathVariable ("idUsuario") String idUsuario, @RequestBody ItemCompraDTO itemCompraDTO)
			throws ProductNotFoundException, UsuarioNotFoundException, QuantidadeInvalidaException, LoteNotFoundException {
		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) throw new UsuarioNotFoundException("Usuario não encontrado");

		Produto produto = produtoService.getProdutoById(itemCompraDTO.getIdProduto());
		if (produto == null) throw new ProductNotFoundException("Produto não existe no carrinho");

		carrinhoService.removeItensDoCarrinho(user, itemCompraDTO);

		return new ResponseEntity<String>("Item removido do carrinho!\n", HttpStatus.OK);
	}

	@RequestMapping(value = "carrinho/{idUsuario}/descarta", method = RequestMethod.DELETE)
	public ResponseEntity<?> descartaCarrinho(@PathVariable ("idUsuario") String idUsuario) throws UsuarioNotFoundException, LoteNotFoundException {
		Usuario user = usuarioService.getUserById(idUsuario);
		if (user == null) throw new UsuarioNotFoundException("Usuario não encontrado");

		carrinhoService.descartaCarrinho(user);
		return new ResponseEntity<String>("Carrinho descartado!", HttpStatus.OK);
	}

	
}
