package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Schema(description = "Representa o histórico de atualizações de um pedido")
@Entity
public class HistoricoPedido {
    @Schema(description = "ID do histórico de pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Pedido idPedido;
    @ManyToOne
    private Cliente idCliente;
    @ManyToOne
    private Entrega statusEntrega;
    @ManyToOne
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
