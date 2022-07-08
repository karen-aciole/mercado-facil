package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeInvalidaException;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import com.ufcg.psoft.mercadofacil.repository.ItemCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;

import static java.util.List.copyOf;

@Service
public class CarrinhoService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepo;
	@Autowired
	private ItemCompraRepository itensDoCarrinhoRepo;
	@Autowired
	private CompraRepository compraRepo;

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
	public void adicionaItensNoCarrinho(Usuario usuario, ItemCompraDTO itemCompraDTO) throws QuantidadeInvalidaException, ProductNotFoundException, LoteNotFoundException {
		Carrinho carrinho = usuario.getCarrinho();
		if (usuario.getCarrinho() == null)
			criaCarrinho(usuario);

		Produto produto = produtoRepo.getProd(itemCompraDTO.getIdProduto()); // busca o produto pelo id


		if (itemCompraDTO.getQuantidade() <= 0) throw new QuantidadeInvalidaException("Quantidade inválida");

		Lote lote = loteService.getLoteByProduct(produto, itemCompraDTO.getQuantidade());// busca o lote mais proximo de vencimento do produto
		if (lote == null) throw new LoteNotFoundException("Produto sem estoque/lote.");

		ItemCompra item = new ItemCompra(produto, itemCompraDTO.getQuantidade()); // cria o item de compra
		carrinho.addItemNoCarrinho(item);
		item.setIdLote(lote.getId()); // armazena o lote do item em caso de desistência da compra ou remoção do item do carrinho
		lote.setQuantidade(lote.getQuantidade() - itemCompraDTO.getQuantidade());
	}

	public void removeItensDoCarrinho(Usuario usuario, ItemCompraDTO itemCompraDTO) throws QuantidadeInvalidaException, LoteNotFoundException, CarrinhoVazioException {
		Carrinho carrinho = usuario.getCarrinho();
		Produto produto = produtoRepo.getProd(itemCompraDTO.getIdProduto());

		if (carrinho.getItensDoCarrinho().isEmpty())
			throw new CarrinhoVazioException("Este usuário não possui carrinho ativo.");

		if (itemCompraDTO.getQuantidade() < 0) throw new QuantidadeInvalidaException("Quantidade inválida");

		ItemCompra itemDoCarrinho = carrinho.getItemNoCarrinho(produto);

		if (itemDoCarrinho.getQuantidade() < itemCompraDTO.getQuantidade()) throw new QuantidadeInvalidaException("Quantidade não existe no carrinho");

		if (itemDoCarrinho.getQuantidade() == itemCompraDTO.getQuantidade()) {
			carrinho.removeItemDoCarrinho(carrinho.getItemNoCarrinho(produto));
		} else {
			itemDoCarrinho.setQuantidade(itemDoCarrinho.getQuantidade() - itemCompraDTO.getQuantidade());
			Lote lote = loteService.getLoteById(itemDoCarrinho.getIdLote());
			lote.setQuantidade(lote.getQuantidade() + itemCompraDTO.getQuantidade());
		}

		if (carrinho.getItensDoCarrinho().isEmpty()) {
			carrinho.limpaCarrinho();
			carrinhoRepo.removeCarrinho(carrinho.getId());
		}
	}

	public void descartaCarrinho(Usuario usuario) throws LoteNotFoundException, CarrinhoVazioException {
		Carrinho carrinho = usuario.getCarrinho();
		if (carrinho.getItensDoCarrinho().isEmpty())
			throw new CarrinhoVazioException("Este usuário não possui carrinho ativo.");

		for (ItemCompra item : carrinho.getItensDoCarrinho()) {
			Lote lote = loteService.getLoteById(item.getIdLote());
			lote.setQuantidade(lote.getQuantidade() + item.getQuantidade());
		}
		carrinho.limpaCarrinho();
		carrinhoRepo.removeCarrinho(carrinho.getId());
	}

	public Compra finalizaCarrinho(Usuario usuario) throws CarrinhoVazioException {
		Carrinho carrinho = usuario.getCarrinho();
		if (carrinho.getItensDoCarrinho().isEmpty())
			throw new CarrinhoVazioException("Este usuário não possui carrinho ativo.");

		List<ItemCompra> itensDaCompra = copyOf(carrinho.getItensDoCarrinho());
		BigDecimal valorDaCompra = calculaValorTotalDoCarrinho(carrinho);

		Compra compra = new Compra(usuario, itensDaCompra, valorDaCompra);
		compraRepo.addCompra(compra);
		usuario.addCompra(compra);

		carrinho.limpaCarrinho();
		carrinhoRepo.removeCarrinho(carrinho.getId());

		return compra;
	}

	private BigDecimal calculaValorTotalDoCarrinho(Carrinho carrinho) {
		BigDecimal valorTotal = new BigDecimal(0);
		for (ItemCompra item : carrinho.getItensDoCarrinho()) {
			valorTotal = valorTotal.add(item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())));
		}
		return valorTotal;
	}
}

