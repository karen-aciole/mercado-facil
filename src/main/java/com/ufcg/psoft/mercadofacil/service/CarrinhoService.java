package com.ufcg.psoft.mercadofacil.service;

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
	
	
	public void adicionaProdutoNoCarrinho(Produto produto, int quantidade) {
		List<Carrinho> listaDecarrinhos = carrinhoRepo.listaCarrinhos();
		for (Carrinho carrinho : listaDecarrinhos) {
			if (carrinho.getItemNoCarrinho(produto) != null) {
				ItemCompra item = carrinho.getItemNoCarrinho(produto);
				item.setQuantidade(item.getQuantidade() + quantidade);
				itensDoCarrinhoRepo.addItem(item);
			}
		}
	}

}

