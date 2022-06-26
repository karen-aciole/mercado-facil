package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraRepository compraRepo;

    public List<Compra> listaCompras() {
        return new ArrayList<>(compraRepo.getAll());
    }

    public List<Compra> listaComprasDoUsuario(Usuario usuario) {
        List<Compra> comprasDoUsuario = new ArrayList<>();
       for (Compra compra : listaCompras()) {
           if (compra.getUsuario().equals(usuario)) {
               comprasDoUsuario.add(compra);
           }
       }
       return comprasDoUsuario;
    }

    public Compra getCompraById(Usuario usuario, String id) {
        for (Compra compra : listaCompras()) {
            if (compra.getUsuario().equals(usuario) && compra.getId().equals(id)) {
                return compra;
            }
        }
        return null;
    }

}
