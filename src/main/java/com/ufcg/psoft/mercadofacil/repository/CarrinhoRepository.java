package com.ufcg.psoft.mercadofacil.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Carrinho;

@Repository
public class CarrinhoRepository {
	
	private Map<String, Carrinho> carrinhos;
	
	public CarrinhoRepository() {
		this.carrinhos = new HashMap<>();
	}
	public void adicionaCarrinho(Carrinho carrinho) {
		this.carrinhos.put(carrinho.getId(), carrinho);
	}
	public void removeCarrinho(String id) { 
		this.carrinhos.remove(id);
	}
	
	public Carrinho getCarrinho(String id) { 
		return this.carrinhos.get(id);
	}

    public ArrayList<Carrinho> listaCarrinhos() {
		return new ArrayList<Carrinho>(carrinhos.values());
    }
}


