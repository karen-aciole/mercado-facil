package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeInvalidaException;
import com.ufcg.psoft.mercadofacil.repository.ItemCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

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

		Produto produto = produtoRepo.getProd(itemCompraDTO.getIdProduto());
		ItemCompra item = new ItemCompra(produto, itemCompraDTO.getQuantidade());
		Lote lote = loteService.getLoteClosestToExpirationDate(produto, itemCompraDTO.getQuantidade());

		lote.setQuantidade(lote.getQuantidade() - itemCompraDTO.getQuantidade());
		item.setIdLote(lote.getId());

		carrinho.addItemNoCarrinho(item);
	}

	public void removeItensDoCarrinho(Usuario usuario, ItemCompraDTO itemCompraDTO) throws ProductNotFoundException, QuantidadeInvalidaException, LoteNotFoundException {
		Carrinho carrinho = usuario.getCarrinho();
		Produto produto = produtoRepo.getProd(itemCompraDTO.getIdProduto());

		if (carrinho.getItemNoCarrinho(produto) == null) throw new ProductNotFoundException("Produto não encontrado no carrinho");

		ItemCompra itemDocarrinho = carrinho.getItemNoCarrinho(produto);

		if (itemDocarrinho.getQuantidade() < itemCompraDTO.getQuantidade()) throw new QuantidadeInvalidaException("Quantidade não existe no carrinho");

		if (itemDocarrinho.getQuantidade() == itemCompraDTO.getQuantidade()) {
			carrinho.removeItemDoCarrinho(carrinho.getItemNoCarrinho(produto));
		} else {
			itemDocarrinho.setQuantidade(itemDocarrinho.getQuantidade() - itemCompraDTO.getQuantidade());
			Lote lote = loteService.getLoteById(itemDocarrinho.getIdLote());
			lote.setQuantidade(lote.getQuantidade() + itemCompraDTO.getQuantidade());
		}

		if (carrinho.getItensDoCarrinho().isEmpty())
			carrinhoRepo.removeCarrinho(carrinho.getId());
	}

	public void descartaCarrinho(Usuario usuario) throws LoteNotFoundException {
		Carrinho carrinho = usuario.getCarrinho();
		if (carrinho == null)
			throw new IllegalArgumentException("Este usuário não possui carrinho ativo.");

		for (ItemCompra item : carrinho.getItensDoCarrinho()) {
			Lote lote = loteService.getLoteById(item.getIdLote());
			lote.setQuantidade(lote.getQuantidade() + item.getQuantidade());
		}
		carrinhoRepo.removeCarrinho(carrinho.getId());
	}

	public Carrinho getCarrinho(Usuario usuario) {
		return usuario.getCarrinho();
	}
}

