package com.ufcg.psoft.mercadofacil.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

	private String id;

	private List<ItemCompra> itensDoCarrinho;

	public Carrinho(String id, List<ItemCompra> itensDoCarrinho) {
		this.id = id;
		this.itensDoCarrinho = itensDoCarrinho;
	}
	public Carrinho(){}

	public List<ItemCompra> getItensDoCarrinho() {
		return itensDoCarrinho;
	}

	public void setItensDoCarrinho(List<ItemCompra> itensDoCarrinho) {
		this.itensDoCarrinho = itensDoCarrinho;
	}

	public ItemCompra addItemNoCarrinho(Produto produto, int quantidade) {
		ItemCompra item = new ItemCompra(produto, quantidade);
		itensDoCarrinho.add(item);
		return item;
	}

	public String getId() {
		return id;
	}

	public ItemCompra getItemNoCarrinho(Produto produto) {
		for (ItemCompra item : itensDoCarrinho) {
			if (item.getProduto().equals(produto)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Carrinho [id=" +
				id +
				", itensDoCarrinho=" +
				itensDoCarrinho +
				"]";
	}
}
