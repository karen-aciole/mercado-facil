package com.ufcg.psoft.mercadofacil.model.pagamento;

import java.math.BigDecimal;

public abstract class FormasDePagamento {

    protected BigDecimal acrescimo;
    protected BigDecimal valorTotal;

    public FormasDePagamento(){
        this.acrescimo = new BigDecimal(0);
    }

    public abstract BigDecimal calculaValorDaCompraComAcrescimo(BigDecimal valorDaCompra);

    public abstract BigDecimal getAcrescimo();

    public abstract String getFormaDePagamento();

}
