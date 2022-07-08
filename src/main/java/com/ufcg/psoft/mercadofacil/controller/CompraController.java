package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.CompraNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.usuario.Usuario;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    CarrinhoRepository carrinhoRepo;

    @RequestMapping(value = "/compra/{idUsuario}/finalizar", method = RequestMethod.POST)
    public ResponseEntity<?> finalizarCompra(@PathVariable ("idUsuario") String idUsuario, @RequestParam ("formaDePagamento") String formaDePagamento) throws CarrinhoVazioException {
        Usuario user;
        Compra compra;

        try {
            user = usuarioService.getUserById(idUsuario);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            compra = carrinhoService.finalizaCarrinho(user, formaDePagamento);
        } catch (CarrinhoVazioException e) {
            return new ResponseEntity<String>("Carrinho vazio", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Compra finalizada com sucesso!\n" + compra, HttpStatus.OK);
    }
    @RequestMapping(value = "/compra/{idUsuario}/", method = RequestMethod.GET)
    public ResponseEntity<?> consultarCompra(@PathVariable("idUsuario") String idUsuario, @RequestParam(value = "idCompra") String idCompra) {
        Usuario user;
        Compra compra;

        try {
            user = usuarioService.getUserById(idUsuario);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            compra = compraService.getCompraDoUsuarioById(user, idCompra);
        } catch (CompraNotFoundException e) {
            return new ResponseEntity<String>("Compra com ID: " + idCompra + " não existe", HttpStatus.BAD_REQUEST);
        }

       return new ResponseEntity<String>(" " + compra, HttpStatus.OK);
    }

    @RequestMapping(value = "/compras/{idUsuario}/", method = RequestMethod.GET)
    public ResponseEntity<?> historicoDeCompras(@PathVariable("idUsuario") String idUsuario) {

        Usuario user;
        try {
            user = usuarioService.getUserById(idUsuario);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        List<Compra> compras = compraService.listaComprasDoUsuario(user);

        return new ResponseEntity<String>("Histórico de compras do usuário: "+ idUsuario + "\n\n" + compras, HttpStatus.OK);
    }

    @RequestMapping(value = "/compra/formasDePagamento/", method = RequestMethod.GET)
    public ResponseEntity<?> listaFormasDePagamentos() {
        String listaFormasDePagamento = "Formas de pagamento:\n" +
                "BOLETO\n" +
                "PAYPAL (acréscimo de 2% no valor da compra)\n" +
                "CARTAO DE CREDITO (acréscimo de 5% no valor da compra)\n";

        return new ResponseEntity<String>(listaFormasDePagamento, HttpStatus.OK);
    }

}
