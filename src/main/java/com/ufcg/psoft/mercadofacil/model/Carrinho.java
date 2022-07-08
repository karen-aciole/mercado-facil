package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Carrinho {

	private Usuario usuario;

	private String id; // id do carrinho
	private List<ItemCompra> itensDoCarrinho;

	public Carrinho() {
		this.itensDoCarrinho = new ArrayList<>();
		this.id = UUID.randomUUID().toString();
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

	public void limpaCarrinho() {
		this.itensDoCarrinho.clear();
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
