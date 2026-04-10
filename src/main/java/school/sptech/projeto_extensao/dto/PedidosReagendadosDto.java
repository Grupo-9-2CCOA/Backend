package school.sptech.projeto_extensao.dto;

import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;

import java.time.LocalDateTime;

public class PedidosReagendadosDto {
    private Integer id;
    private String produto;
    private Entrega status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataReagendada; //data pedido
    private Pagamento pagamento;

    public PedidosReagendadosDto(Integer id, String produto, Entrega status, LocalDateTime dataCriacao, LocalDateTime dataReagendada, Pagamento pagamento) {
        this.id = id;
        this.produto = produto;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataReagendada = dataReagendada;
        this.pagamento = pagamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Entrega getStatus() {
        return status;
    }

    public void setStatus(Entrega status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataReagendada() {
        return dataReagendada;
    }

    public void setDataReagendada(LocalDateTime dataReagendada) {
        this.dataReagendada = dataReagendada;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
