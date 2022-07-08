package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Compra;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CompraRepository {

    private Map<String, Compra> compras;

    public CompraRepository() {
        this.compras = new HashMap<>();
    }

    public Collection<Compra> getAll() {
        return compras.values();
    }

    public Compra getCompra(String id) {
        return this.compras.get(id);
    }

    public void addCompra(Compra compra) {
        this.compras.put(compra.getId(), compra);
    }

}
