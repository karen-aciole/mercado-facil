package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.exception.CompraNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    CompraService compraService;

    @RequestMapping(value = "/compras/{idUsuario}/{idCompra}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarCompra(@PathVariable("idUsuario") String idUsuario, @PathVariable("idCompra") String idCompra)
            throws UsuarioNotFoundException {

        Usuario user = usuarioService.getUserById(idUsuario);
        if (user == null) return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NO_CONTENT);

        Compra compra = compraService.getCompraById(user, idCompra);
        if (compra == null) return new ResponseEntity<String>("Compra com ID: " + idCompra + " não existe", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Compra>(compra, HttpStatus.OK);
    }

    @RequestMapping(value = "/compras/{idUsuario}/", method = RequestMethod.GET)
    public ResponseEntity<?> historicoDeCompras(@PathVariable("idUsuario") String idUsuario) throws UsuarioNotFoundException {
        Usuario user = usuarioService.getUserById(idUsuario);
        if (user == null) return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NO_CONTENT);

        List <Compra> compras = compraService.listaComprasDoUsuario(user);
        if (compras.isEmpty()) return new ResponseEntity<String>("Nenhuma compra encontrada", HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
    }

}
