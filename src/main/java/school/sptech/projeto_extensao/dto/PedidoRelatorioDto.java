package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;

import java.time.LocalDateTime;

@Schema(description = "Dados resumidos de um pedido para exibição no relatório")
public class PedidoRelatorioDto {
    @Schema(description = "ID do pedido", example = "1")
    private Integer id;

    @Schema(description = "Nome do produto", example = "Bolo de Chocolate")
    private String produto;

    @Schema(description = "Data de criação do pedido", example = "2026-04-06T19:41:58")
    private LocalDateTime dataCriacao;

    @Schema(description = "Status de entrega do pedido")
    private Entrega entrega;

    @Schema(description = "Status de pagamento do pedido")
    private Pagamento pagamento;

    public PedidoRelatorioDto(Integer id, String produto, LocalDateTime dataCriacao, Entrega entrega, Pagamento pagamento) {
        this.id = id;
        this.produto = produto;
        this.dataCriacao = dataCriacao;
        this.entrega = entrega;
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
}
