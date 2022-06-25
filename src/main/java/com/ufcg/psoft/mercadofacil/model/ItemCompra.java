package com.ufcg.psoft.mercadofacil.model;

public class ItemCompra {

    private Produto produto;
    private Lote lote;
    private int quantidade;

    public ItemCompra(Produto produto,  int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Item: " + getProduto().getNome() + " - Quantidade: " + getQuantidade();
    }

}
