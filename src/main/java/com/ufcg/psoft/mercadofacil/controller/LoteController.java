package com.ufcg.psoft.mercadofacil.controller;
import java.time.LocalDate;
import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.InvalidDateException;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
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

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.service.LoteService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {
	
	@Autowired
	private LoteService loteService;
	@Autowired ProdutoService produtoService;
	
	//CriaLote
	@RequestMapping(value = "/lote/", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@RequestBody LoteDTO loteDTO, UriComponentsBuilder ucBuilder) {
		
		String loteID;
		
		try {
			loteDTO.getDataDeValidade().isAfter(LocalDate.now());
			loteID = loteService.addLote(loteDTO);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		} catch (InvalidDateException e) {
			return new ResponseEntity<String>("Data inválida", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<String>("Lote cadastrado com ID: " + loteID, HttpStatus.OK);
	}
	
	//Lista lotes
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() { 
		List<Lote> lotes = loteService.listaLotes();
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
	//Deleta lote
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarLote(@PathVariable("id") String id) {
		
		try { 
			this.loteService.deletLote(id);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Lote deletado", HttpStatus.OK);
	}
	
	//Consultar Lote pelo ID
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarLotePeloID(@PathVariable("id") String id) {
		
		Lote lote; 
		try {
			lote = loteService.getLoteById(id);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Lote>(lote, HttpStatus.OK);
	}
	
	
	//Edita lote
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> editarLote(@PathVariable("id") String id, @RequestBody LoteDTO updateLote, UriComponentsBuilder ucBuilder) {
	
		Lote lote; 
		try { 
			lote = loteService.getLoteById(id);
			loteService.editLote(updateLote, lote);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>("Lote atualizado.\n" + lote, HttpStatus.OK);
	}
}