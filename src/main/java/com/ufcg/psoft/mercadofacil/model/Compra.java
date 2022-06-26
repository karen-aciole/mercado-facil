package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Compra {
    private String idDaCompra;
    private LocalDate dataDaCompra;
    private Usuario usuario;
    private BigDecimal valorDaCompra;

    private List<ItemCompra> itensDaCompra;

    public Compra(List<ItemCompra> itensDaCompra, BigDecimal valorDaCompra) {
        this.usuario = usuario;
        this.itensDaCompra = itensDaCompra;
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

    public List<ItemCompra> itensDaCompra() {
        return itensDaCompra;
    }

    public String listaItensDaCompraFormatada() {
        StringBuilder sb = new StringBuilder();
        for (ItemCompra item : itensDaCompra) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return "ID da Compra: " + getIdDaCompra() +
                "\n Data da compra: " + getDataDaCompra() +
                "\n Itens da compra: \n" +  listaItensDaCompraFormatada() +
                "\n Valor total da compra: " + getValorDaCompra();
    }
}
