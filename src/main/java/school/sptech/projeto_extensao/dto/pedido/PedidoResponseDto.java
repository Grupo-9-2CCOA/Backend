package school.sptech.projeto_extensao.dto.pedido;

import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;

import java.time.LocalDateTime;

public class PedidoResponseDto {
    private String produto;
    private String descricao;
    private Double valor;
    private Boolean isAtivo;
    private LocalDateTime dataPedido;
    private Entrega entrega;
    private Pagamento pagamento;
    private Cliente cliente;
    private Endereco endereco;

    public PedidoResponseDto() {
    }

    public PedidoResponseDto(String produto, String descricao, Double valor, Boolean isAtivo, LocalDateTime dataPedido, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
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
