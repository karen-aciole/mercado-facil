package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.exception.CompraNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Compra getCompraDoUsuarioById(Usuario usuario, String id) throws CompraNotFoundException {
        Compra compra = getCompraById(id);
        if (compra.getUsuario().equals(usuario)) {
            return compra;
        }
        return null;
    }
    private Compra getCompraById(String id) throws CompraNotFoundException {
        Compra compra = compraRepo.getCompra(id);
        if (compra == null) throw new CompraNotFoundException("Compra com id " + id + " não encontrada");

        return compra;
    }

}
