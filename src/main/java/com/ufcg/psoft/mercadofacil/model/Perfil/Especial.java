package com.ufcg.psoft.mercadofacil.model.Perfil;

import java.math.BigDecimal;

public class Especial extends Perfil {

    public Especial() {}

    @Override
    public BigDecimal aplicaDesconto() {
        return desconto.add(BigDecimal.valueOf(0.1));
    }

    @Override
    public BigDecimal getDesconto() {
        return desconto;
    }

    @Override
    public String getPerfil() {
        return "ESPECIAL";
    }
}

