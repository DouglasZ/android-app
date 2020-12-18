package com.example.apppedidos.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

    private Long id;
    private String nome;
    private String descricao;
    private Double valor;

    private int quantidade = 0;

    @Override
    public String toString() {
        return nome.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
