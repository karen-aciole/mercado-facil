package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Compra {
    private String idDaCompra;
    private LocalDate dataDaCompra;
    private Usuario usuario;
    private BigDecimal valorDaCompra;

    private Carrinho carrinho;

    public Compra(Carrinho carrinho, BigDecimal valorDaCompra) {
        this.usuario = usuario;
        this.carrinho = carrinho;
        this.valorDaCompra = valorDaCompra;
        this.dataDaCompra = LocalDate.now();
        this.idDaCompra = UUID.randomUUID().toString();
    }

    public String getIdDaCompra() {
        return idDaCompra;
    }

    public LocalDate getDataDaCompra() {
        return dataDaCompra;
    }

    public BigDecimal getValorDaCompra() {
        return valorDaCompra;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    @Override
    public String toString() {
        return "ID da Compra: " + getIdDaCompra() +
                "\n Data da compra: " + getDataDaCompra() +
                "\n Itens da compra: " + getCarrinho() +
                "\n Valor total da compra: " + getValorDaCompra();
    }
}
