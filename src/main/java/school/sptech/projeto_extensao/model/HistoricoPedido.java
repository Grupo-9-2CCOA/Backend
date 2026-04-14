package school.sptech.projeto_extensao.model;

import java.time.LocalDateTime;

public class HistoricoPedido {
    private Integer id;
    private Pedido idPedido;
    private Cliente idCliente;
    private Entrega statusEntrega;
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
