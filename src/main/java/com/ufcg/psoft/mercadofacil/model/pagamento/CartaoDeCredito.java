package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public class CartaoDeCredito extends FormasDePagamento {

    public CartaoDeCredito() {}

    @Override
    public void calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        BigDecimal acrescimo = new BigDecimal(0.05);
        BigDecimal valorTotal = valorDaCompra.multiply(acrescimo);
        this.acrescimo.add(valorTotal);
    }

    public BigDecimal getValorTotal() {
        return this.acrescimo;
    }

    public String getFormaDePagamento() {
        return "CARTAO DE CREDITO";
    }
}

