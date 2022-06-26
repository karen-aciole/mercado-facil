package com.ufcg.psoft.mercadofacil.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

	private Usuario usuario;

	private String id; // id do carrinho
	private List<ItemCompra> itensDoCarrinho;

	public Carrinho() {
		this.itensDoCarrinho = new ArrayList<>();
	}
	public String getId() {
		return id;
	}

	public Usuario getUsuario()	{
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemCompra> getItensDoCarrinho() {
		return itensDoCarrinho;
	}

	public void addItemNoCarrinho(ItemCompra item) {
		itensDoCarrinho.add(item);
	}

	public void removeItemDoCarrinho(ItemCompra item) {
		itensDoCarrinho.remove(item);
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
		StringBuilder itens = new StringBuilder();
		for (ItemCompra item : itensDoCarrinho) {
			itens.append(item).append("\n");
		}
		return itens.toString();

	}

}
