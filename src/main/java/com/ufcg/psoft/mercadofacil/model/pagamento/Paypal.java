package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public class Paypal extends FormasDePagamento {

    public Paypal() {}

    @Override
    public void calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        BigDecimal acrescimo = new BigDecimal(0.02);
        BigDecimal valorTotal = valorDaCompra.multiply(acrescimo);
        this.acrescimo.add(valorTotal);
    }

    public BigDecimal getValorTotal() {
        return this.acrescimo;
    }

    public String getFormaDePagamento() {
        return "PAYPAL";
    }
}
