package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemCompraRepository {
    private Map<String, ItemCompra> itensDoCarrinho;


    public ItemCompraRepository() {
        this.itensDoCarrinho = new HashMap<String, ItemCompra>();
    }

    public void addItem(ItemCompra item) {
        this.itensDoCarrinho.put(item.getProduto().getId(), item);
    }

    public ItemCompra getItem(String id) {
        return this.itensDoCarrinho.get(id);
    }

    public void delItem(String id) {
        this.itensDoCarrinho.remove(id);
    }

    public Map<String, ItemCompra> getAll() {
        return itensDoCarrinho;
    }
}
