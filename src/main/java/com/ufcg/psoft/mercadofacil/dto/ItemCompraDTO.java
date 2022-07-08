package com.ufcg.psoft.mercadofacil.dto;

public class ItemCompraDTO {


    private String idProduto;
    private int quantidade;

    public ItemCompraDTO(String idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return "Item: idProduto: " + idProduto + ", quantidade: " + quantidade;
    }

}
