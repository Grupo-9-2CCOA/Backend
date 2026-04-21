package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Schema(description = "Representa um pedido do sistema")
@Entity
public class Pedido {
    @Schema(description = "ID do histórico de pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Produto principal do pedido", example = "Bolo 5kg")
    private String produto;

    @Schema(description = "Descrição dos produtos relacionados ao pedido", example = "1 Bolo de kg decorado da Turma da Mônica; 100 coxinhas")
    private String descricao;

    @Schema(description = "O valor cobrado no pedido", example = "59.99")
    private Double valor;

    @Schema(description = "Booleano que informa se o pedido sofreu deleção lógica", example = "false")
    private Boolean isAtivo;

    @Schema(description = "Booleano que informa se o pedido foi reagendado em algum momento", example = "false")
    private Boolean isReagendado;

    @Schema(description = "Data programada para o pedido ser entregue", example = "2026-10-10")
    private LocalDateTime dataPedido;

    @Schema(description = "Data em que o pedido sofreu modicação", example = "2026-03-05")
    private LocalDateTime dataModificacao;

    @Schema(description = "Data em que o pedido foi criado", example = "2026-06-08")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "status_entrega")
    private Entrega entrega;

    @ManyToOne
    @JoinColumn(name = "status_pagamento")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    private Endereco endereco;

    public Pedido() {
    }

    public Pedido(Integer id, String produto, String descricao, Double valor, Boolean isAtivo, Boolean isReagendado,
                  LocalDateTime dataPedido, LocalDateTime dataModificacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isReagendado = isReagendado;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.dataModificacao = dataModificacao;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
    }

    public Pedido(String produto, String descricao, Double valor, Boolean isAtivo, LocalDateTime dataPedido, LocalDateTime dataCriacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.dataCriacao = dataCriacao;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
    }

    public Boolean getAtivo() {
        return isAtivo;
    }

    public void setAtivo(Boolean ativo) {
        isAtivo = ativo;
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

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Boolean getReagendado() {
        return isReagendado;
    }

    public void setReagendado(Boolean reagendado) {
        isReagendado = reagendado;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
