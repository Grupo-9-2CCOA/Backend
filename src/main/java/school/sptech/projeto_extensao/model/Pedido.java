package school.sptech.projeto_extensao.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String produto;
    private String descricao;
    private Integer valor;
    private Boolean isAtivo;
    private Boolean isReagendado;
    private LocalDateTime dataPedido;
    private LocalDateTime dataModificacao;
    private LocalDateTime dataCriacao;

    @ManyToOne
    private Entrega entrega;

    @ManyToOne
    private Pagamento pagamento;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Endereco endereco;

    public Pedido() {
    }

    public Pedido(Integer id, String produto, String descricao, Integer valor, Boolean isAtivo, Boolean isReagendado, LocalDateTime dataPedido, LocalDateTime dataModificacao, LocalDateTime dataCriacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.isReagendado = isReagendado;
        this.dataPedido = dataPedido;
        this.dataModificacao = dataModificacao;
        this.dataCriacao = dataCriacao;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
    }

    public Pedido(Integer id, String produto, String descricao, Integer valor, Boolean isAtivo, LocalDateTime dataPedido, LocalDateTime dataModificacao, LocalDateTime dataCriacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.dataModificacao = dataModificacao;
        this.dataCriacao = dataCriacao;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
    }

    public Pedido(String produto, String descricao, Integer valor, Boolean isAtivo, LocalDateTime dataPedido, LocalDateTime dataModificacao, LocalDateTime dataCriacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.dataModificacao = dataModificacao;
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

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
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

    public Boolean getReagendado() {
        return isReagendado;
    }

    public void setReagendado(Boolean reagendado) {
        isReagendado = reagendado;
    }
}
