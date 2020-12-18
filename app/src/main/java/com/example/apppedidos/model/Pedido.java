package com.example.apppedidos.model;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {

    private Long id;
    private String nomeComprador;
    private List<ItemPedido> pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public List<ItemPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ItemPedido> pedidos) {
        this.pedidos = pedidos;
    }
}
