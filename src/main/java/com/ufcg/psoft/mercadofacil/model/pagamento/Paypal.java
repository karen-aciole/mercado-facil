package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public class Paypal extends FormasDePagamento {

    public Paypal() {}

    @Override
    public BigDecimal calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        return valorDaCompra.multiply(BigDecimal.valueOf(0.02));
    }

    @Override
    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public String getFormaDePagamento() {
        return "PAYPAL";
    }
}
