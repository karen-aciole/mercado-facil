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

import com.google.gson.Gson;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {
	
	@Autowired
	private LoteService loteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	//CriaLote
	@RequestMapping(value = "/lote/", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@RequestBody String id, int quantidade, UriComponentsBuilder ucBuilder) {
		Gson gson = new Gson();
		LoteDTO loteDTO; 
		String loteID;
		
		try {
			loteDTO = new LoteDTO(id, quantidade); 
			String lote = gson.toJson(loteDTO);
			
			loteID = loteService.addLote(lote);

		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Lote cadastrado com ID: " + loteID, HttpStatus.OK);
	}
	
	//Lista lotes
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() { 
		List<Lote> lotes = loteService.listarLotes();
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
	//Deleta lote
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarLote(@PathVariable("id") String id) {
		
		try { 
			this.loteService.deletLote(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Lote deletado", HttpStatus.OK);
	}
	

}