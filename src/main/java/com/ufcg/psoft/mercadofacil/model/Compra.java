package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.model.pagamento.Boleto;
import com.ufcg.psoft.mercadofacil.model.pagamento.CartaoDeCredito;
import com.ufcg.psoft.mercadofacil.model.pagamento.FormasDePagamento;
import com.ufcg.psoft.mercadofacil.model.pagamento.Paypal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Compra {
    private String id;
    private LocalDate dataDaCompra;
    private Usuario usuario;
    private BigDecimal valorDaCompra;
    private String formaDePagamento;
    private List<ItemCompra> itensDaCompra;

    public Compra(Usuario usuario, List<ItemCompra> itensDaCompra, String formaDePagamento, BigDecimal valorDaCompra) {
        this.usuario = usuario;
        this.itensDaCompra = itensDaCompra;
        this.formaDePagamento = formaDePagamento;
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

    public LocalDate getDataDaCompra() {
        return dataDaCompra;
    }

    public BigDecimal getValorDaCompra() {
        return valorDaCompra;
    }

    public List<ItemCompra> getItensDaCompra() {
        return itensDaCompra;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    private String getFormattedFormaDePagamento() {
        switch (formaDePagamento) {
            case "CARTAODECREDITO":
                return new CartaoDeCredito().getFormaDePagamento();
            case "PAYPAL":
                return new Paypal().getFormaDePagamento();
            case "BOLETO":
                return new Boleto().getFormaDePagamento();
        }
        return "";
    }

    private BigDecimal getValorDaCompraComAcrescimo(BigDecimal valorDaCompra) {
        BigDecimal valorComAcrescimo = new BigDecimal(0);
        switch (getFormaDePagamento()) {
            case "BOLETO":
                FormasDePagamento boleto = new Boleto();
                valorComAcrescimo = this.valorDaCompra.add(boleto.calculaValorDaCompraComAcrescimo(valorDaCompra));
                break;

            case "CARTAODECREDITO":
                FormasDePagamento cartaoDeCredito = new CartaoDeCredito();
                cartaoDeCredito.calculaValorDaCompraComAcrescimo(valorDaCompra);
                valorComAcrescimo = this.valorDaCompra.add(cartaoDeCredito.calculaValorDaCompraComAcrescimo(valorDaCompra));
                break;

            case "PAYPAL":
                FormasDePagamento paypal = new Paypal();
                paypal.calculaValorDaCompraComAcrescimo(valorDaCompra);
                valorComAcrescimo = this.valorDaCompra.add(paypal.calculaValorDaCompraComAcrescimo(valorDaCompra));
                break;
        }
        return valorComAcrescimo;
    }


    public String listaItensDaCompraFormatada() {
        StringBuilder sb = new StringBuilder();
        for (ItemCompra item : getItensDaCompra()) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }

    private BigDecimal getValorDoDescontoDaCompra() {
        BigDecimal valorDoDesconto = new BigDecimal(0);
        BigDecimal desconto = getUsuario().getDescontoDeAcordoComPerfil();
        valorDoDesconto = valorDoDesconto.add(valorDaCompra.multiply(desconto));
        return valorDoDesconto;
    }

    @Override
    public String toString() {
        return "\nID da Compra: " + getId() +
                "\nData da compra: " + getDataDaCompra() +
                "\nItens da compra:\n " +  listaItensDaCompraFormatada() +
                "\nForma de pagamento: " + getFormattedFormaDePagamento() +
                "\nValor parcial da compra: R$" + getValorDaCompra() +
                "\nDescontos aplicados - 10% "+"(Usuário: "+ getUsuario().getPerfil()+")"+": -R$" + getValorDoDescontoDaCompra() +
                "\nValor total da compra (com possíveis acréscimos): R$" + getValorDaCompraComAcrescimo(valorDaCompra) +
                "\n\n";
    }
}
