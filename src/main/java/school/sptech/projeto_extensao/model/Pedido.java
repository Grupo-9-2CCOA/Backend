package school.sptech.projeto_extensao.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String produto;
    private String descricao;
    private Integer valor;
    private LocalDate dataModificacao;
    private LocalDate dataPedido;

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



    public Pedido(Integer id, String produto, String descricao, Integer valor, LocalDate dataModificacao, LocalDate dataPedido, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataModificacao = dataModificacao;
        this.dataPedido = dataPedido;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
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

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
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
