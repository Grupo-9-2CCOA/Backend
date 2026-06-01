package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;

import java.time.LocalDateTime;

@Schema(description = "Dados de um pedido reagendado")
public class PedidosReagendadosDto {
    @Schema(description = "ID do pedido", example = "5")
    private Integer id;

    @Schema(description = "Nome do produto", example = "Bolo de Cenoura")
    private String produto;

    @Schema(description = "Status de entrega do pedido")
    private Entrega status;

    @Schema(description = "Data de criação original do pedido", example = "2026-04-06T19:41:58")
    private LocalDateTime dataCriacao;

    @Schema(description = "Nova data reagendada do pedido", example = "2026-04-10T10:00:00")
    private LocalDateTime dataReagendada;

    @Schema(description = "Status de pagamento do pedido")
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
