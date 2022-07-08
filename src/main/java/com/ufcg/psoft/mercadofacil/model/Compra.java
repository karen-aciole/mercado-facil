package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.model.pagamento.Boleto;
import com.ufcg.psoft.mercadofacil.model.pagamento.CartaoDeCredito;
import com.ufcg.psoft.mercadofacil.model.pagamento.FormasDePagamento;
import com.ufcg.psoft.mercadofacil.model.pagamento.Paypal;
import com.ufcg.psoft.mercadofacil.model.usuario.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Compra {
    private String id;
    private LocalDate dataDaCompra;
    private Usuario usuario;
    private BigDecimal valorDaCompra;
    private String formaDePagamento;
    private List<ItemCompra> itensDaCompra;

    public Compra(Usuario usuario, List<ItemCompra> itensDaCompra, String formaDePagamento, BigDecimal valorDaCompra) {
        this.usuario = usuario;
        this.itensDaCompra = itensDaCompra;
        this.formaDePagamento = formaDePagamento;
        this.valorDaCompra = valorDaCompra;
        this.dataDaCompra = LocalDate.now();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getDataDaCompra() {
        return dataDaCompra;
    }

    public BigDecimal getValorDaCompra() {
        return valorDaCompra;
    }

    public List<ItemCompra> getItensDaCompra() {
        return itensDaCompra;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    private String getFormattedFormaDePagamento() {
        if (formaDePagamento.equals("CARTAODECREDITO")) {
            return new CartaoDeCredito().getFormaDePagamento();
        } else if (formaDePagamento.equals("PAYPAL")) {
            return new Paypal().getFormaDePagamento();
        } else if (formaDePagamento.equals("BOLETO")) {
            return new Boleto().getFormaDePagamento();
        }
        return "";
    }

    private BigDecimal getValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        BigDecimal valorComAcrescimo = new BigDecimal(0);
        if (getFormaDePagamento().equals("BOLETO")) {
            FormasDePagamento boleto = new Boleto();
            valorComAcrescimo = this.valorDaCompra.add(boleto.calculaValorDaCompraComAcrescimo(valorDaCompra));

        } else if (getFormaDePagamento().equals("CARTAODECREDITO")) {
            FormasDePagamento cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.calculaValorDaCompraComAcrescimo(valorDaCompra);
            valorComAcrescimo = this.valorDaCompra.add(cartaoDeCredito.calculaValorDaCompraComAcrescimo(valorDaCompra));

        } else if (getFormaDePagamento().equals("PAYPAL")) {
            FormasDePagamento paypal = new Paypal();
            paypal.calculaValorDaCompraComAcrescimo(valorDaCompra);
            valorComAcrescimo = this.valorDaCompra.add(paypal.calculaValorDaCompraComAcrescimo(valorDaCompra));
        }
        return valorComAcrescimo;
    }


    public String listaItensDaCompraFormatada() {
        StringBuilder sb = new StringBuilder();
        for (ItemCompra item : getItensDaCompra()) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return "\nID da Compra: " + getId() +
                "\nData da compra: " + getDataDaCompra() +
                "\nItens da compra:\n " +  listaItensDaCompraFormatada() +
                "\nForma de pagamento: " + getFormattedFormaDePagamento() +
                "\nValor da compra: R$" + getValorDaCompra() +
                "\nValor total da compra (com possíveis acréscimos): R$" + getValorDaCompraComAcrescimo(valorDaCompra) + "\n\n";
    }
}
