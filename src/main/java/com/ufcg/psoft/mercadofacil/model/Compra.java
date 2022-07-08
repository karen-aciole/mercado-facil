package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Compra {
    private String id;
    private LocalDate dataDaCompra;
    private Usuario usuario;
    private BigDecimal valorDaCompra;

    private List<ItemCompra> itensDaCompra;

    public Compra(Usuario usuario, List<ItemCompra> itensDaCompra, BigDecimal valorDaCompra) {
        this.usuario = usuario;
        this.itensDaCompra = itensDaCompra;
        this.valorDaCompra = valorDaCompra;
        this.dataDaCompra = LocalDate.now();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

   // public void setUsuario(Usuario usuario) {
      //  this.usuario = usuario;
   // }

    public void setValorDaCompra(BigDecimal valorDaCompra) {
        this.valorDaCompra = valorDaCompra;
    }


    public LocalDate getDataDaCompra() {
        return dataDaCompra;
    }

    public BigDecimal getValorDaCompra() {
        return valorDaCompra;
    }

    public List<ItemCompra> getItensDaCompra() {
        return itensDaCompra;
    }

    public String listaItensDaCompraFormatada() {
        StringBuilder sb = new StringBuilder();
        for (ItemCompra item : getItensDaCompra()) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return "\nID da Compra: " + getId() +
                "\nData da compra: " + getDataDaCompra() +
                "\nItens da compra:\n " +  listaItensDaCompraFormatada() +
                "\nValor total da compra: R$" + getValorDaCompra() + "\n\n";
    }
}
