package school.sptech.projeto_extensao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class HistoricoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "status_entrega")
    private Entrega statusEntrega;

    @ManyToOne
    @JoinColumn(name = "status_pagamento")
    private Pagamento statusPagamento;

    private LocalDateTime dataCriacao;

    public HistoricoPedido() {
    }

    public HistoricoPedido(Pedido idPedido, Cliente idCliente, Entrega statusEntrega, Pagamento statusPagamento, LocalDateTime dataCriacao) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.statusEntrega = statusEntrega;
        this.statusPagamento = statusPagamento;
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Entrega getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(Entrega statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public Pagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(Pagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
