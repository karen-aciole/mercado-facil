package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public class CartaoDeCredito extends FormasDePagamento {

    public CartaoDeCredito() {}

    @Override
    public BigDecimal calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        return valorDaCompra.multiply(BigDecimal.valueOf(0.05));
    }

    @Override
    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public String getFormaDePagamento() {
        return "CARTAO DE CREDITO";
    }
}

