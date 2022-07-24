package com.ufcg.psoft.mercadofacil.model.Perfil;

import java.math.BigDecimal;

public class Normal extends Perfil {

    public Normal() {}


    @Override
    public BigDecimal aplicaDesconto() {
        return desconto.add(desconto);
    }

    @Override
    public BigDecimal getDesconto() {
        return desconto;
    }

    public String getPerfil() {
        return "NORMAL";
    }

}

