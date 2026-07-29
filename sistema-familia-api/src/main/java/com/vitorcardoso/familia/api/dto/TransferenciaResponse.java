package com.vitorcardoso.familia.api.dto;

public class TransferenciaResponse {

    private String tipoBem;
    private Long bemId;
    private String bemNome;
    private double valorBem;
    private String vendedor;
    private String comprador;
    private double valorPago;
    private double valorEmprestimo;
    private double carteiraVendedorApos;
    private double carteiraCompradorApos;
    private double dividaCompradorApos;
    private String mensagem;

    public String getTipoBem() {
        return tipoBem;
    }

    public void setTipoBem(String tipoBem) {
        this.tipoBem = tipoBem;
    }

    public Long getBemId() {
        return bemId;
    }

    public void setBemId(Long bemId) {
        this.bemId = bemId;
    }

    public String getBemNome() {
        return bemNome;
    }

    public void setBemNome(String bemNome) {
        this.bemNome = bemNome;
    }

    public double getValorBem() {
        return valorBem;
    }

    public void setValorBem(double valorBem) {
        this.valorBem = valorBem;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getValorEmprestimo() {
        return valorEmprestimo;
    }

    public void setValorEmprestimo(double valorEmprestimo) {
        this.valorEmprestimo = valorEmprestimo;
    }

    public double getCarteiraVendedorApos() {
        return carteiraVendedorApos;
    }

    public void setCarteiraVendedorApos(double carteiraVendedorApos) {
        this.carteiraVendedorApos = carteiraVendedorApos;
    }

    public double getCarteiraCompradorApos() {
        return carteiraCompradorApos;
    }

    public void setCarteiraCompradorApos(double carteiraCompradorApos) {
        this.carteiraCompradorApos = carteiraCompradorApos;
    }

    public double getDividaCompradorApos() {
        return dividaCompradorApos;
    }

    public void setDividaCompradorApos(double dividaCompradorApos) {
        this.dividaCompradorApos = dividaCompradorApos;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
