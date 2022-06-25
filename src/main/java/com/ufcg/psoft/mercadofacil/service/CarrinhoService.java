package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.repository.ItemCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import java.util.List;


@Service
public class CarrinhoService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepo;
	@Autowired
	private ItemCompraRepository itensDoCarrinhoRepo;

	@Autowired
	private ProdutoRepository produtoRepo;
	@Autowired
	private LoteService loteService;


	private void criaCarrinho(Usuario usuario) {
		Carrinho carrinho = new Carrinho();
		carrinho.setUsuario(usuario);
		usuario.setCarrinho(carrinho);
		this.carrinhoRepo.adicionaCarrinho(carrinho);
	}
	public void adicionaItensNoCarrinho(Usuario usuario, ItemCompraDTO itemCompraDTO) {
		Carrinho carrinho = usuario.getCarrinho();
		if (carrinho == null) {
			criaCarrinho(usuario);
		}
		//carrinho.setUsuario(usuario);
		Produto produto = produtoRepo.getProd(itemCompraDTO.getIdProduto());
		ItemCompra item = new ItemCompra(produto, itemCompraDTO.getQuantidade());
		Lote lote = loteService.getLoteClosestToExpirationDate(produto);

		if (lote.getQuantidade() >= itemCompraDTO.getQuantidade()) {
			lote.setQuantidade(lote.getQuantidade() - itemCompraDTO.getQuantidade());
			carrinho.addItemNoCarrinho(item);
		} else {
			Lote outroLote = loteService.getLoteByQuantidade(itemCompraDTO.getQuantidade());
			outroLote.setQuantidade(outroLote.getQuantidade() - itemCompraDTO.getQuantidade());
			carrinho.addItemNoCarrinho(item);
		}
	}

	public Carrinho getCarrinho(Usuario usuario) {
		return usuario.getCarrinho();
	}
}

