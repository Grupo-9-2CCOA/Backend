package school.sptech.projeto_extensao.dto;

import java.time.LocalDate;

public class PedidoRequestDto {
    private String produto;
    private String descricao;
    private Integer valor;
    private LocalDate dataModificacao;
    private LocalDate dataPedido;

    public PedidoRequestDto() {
    }

    public PedidoRequestDto(String produto, String descricao, Integer valor, LocalDate dataModificacao, LocalDate dataPedido) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataModificacao = dataModificacao;
        this.dataPedido = dataPedido;
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
}
