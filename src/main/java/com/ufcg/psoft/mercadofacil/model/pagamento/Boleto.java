package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public class Boleto extends FormasDePagamento {

    public Boleto() {}

    @Override
    public void calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        BigDecimal acrescimo = new BigDecimal(1);
        BigDecimal valorTotal = valorDaCompra.multiply(acrescimo);
        this.acrescimo.add(valorTotal);
    }

    @Override
    public BigDecimal getValorTotal() {
        return this.acrescimo;
    }


    public String getFormaDePagamento() {
        return "BOLETO";
    }

}
