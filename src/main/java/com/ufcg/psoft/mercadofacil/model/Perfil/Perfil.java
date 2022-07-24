package com.ufcg.psoft.mercadofacil.model.Perfil;

import java.math.BigDecimal;

public abstract class Perfil {

    protected BigDecimal desconto;

    public Perfil(){
        this.desconto = new BigDecimal(0);
    }

    public abstract BigDecimal aplicaDesconto();

    public abstract BigDecimal getDesconto();

    public abstract String getPerfil();
}
