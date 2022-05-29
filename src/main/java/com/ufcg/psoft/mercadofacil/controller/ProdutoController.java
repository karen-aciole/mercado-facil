package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

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

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	//CRIA PRODUTO
	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {

		String prodID = produtoService.addProduto(produtoDTO);
		return new ResponseEntity<String>("Produto cadastrado com ID: " + prodID, HttpStatus.CREATED);
	}
	
	//CONSULTA PRODUTO PELO ID 
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProdutoPeloID(@PathVariable("id") String id) {

		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
			
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	//LISTA PRODUTOS
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos() {
		List<Produto> produtos = produtoService.listarProdutos();
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	//EDITA PRODUTOS -> caso tenha lote, é necessário deletar o lote também. 
	@RequestMapping (value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> editarProduto(@PathVariable("id") String id, @RequestBody ProdutoDTO updateProduto, UriComponentsBuilder ucBuilder) {
		
		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
			produtoService.editProduto(updateProduto, produto);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);		
		}
			
		return new ResponseEntity<String>("Produto atualizado." + produto, HttpStatus.OK); 
	}
	
	//DELETA PRODUTO
	@RequestMapping (value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletaProduto(@PathVariable("id") String id) { 
		
		try { 
			this.produtoService.deletProduto(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Produto deletado", HttpStatus.OK);
	}
	
	// CONSULTA PRODUTO PELO NOME
	@RequestMapping (value="/produto/nome", method = RequestMethod.GET)
	public ResponseEntity<?>consultarProdutoPeloNome(@RequestParam(value = "nome") String nome) {
		List<Produto> produtos = produtoService.listarProdsByName(nome);
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	//CONSULTAR PRODUTOS PELO NOME QUE POSSUEM LOTE
	@RequestMapping (value="/produto/nome/possuiLote", method = RequestMethod.GET)
	public ResponseEntity<?>consultarProdutoPeloNomeComLote(@RequestParam(value = "nome") String nome) {
		List<Produto> produtos = produtoService.listarProdsLoteByName(nome);
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

}
