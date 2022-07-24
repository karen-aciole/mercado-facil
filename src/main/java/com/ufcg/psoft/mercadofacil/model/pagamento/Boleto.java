package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public class Boleto extends FormasDePagamento {

    public Boleto() {}

    @Override
    public BigDecimal calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        return valorDaCompra.multiply(BigDecimal.valueOf(0));
    }

    @Override
    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public String getFormaDePagamento() {
        return "BOLETO";
    }

}
