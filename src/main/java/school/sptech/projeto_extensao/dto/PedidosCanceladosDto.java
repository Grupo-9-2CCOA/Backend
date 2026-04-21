package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dados de um pedido cancelado")
public class PedidosCanceladosDto {
    @Schema(description = "ID do pedido", example = "4")
    private Integer id;

    @Schema(description = "Nome do produto", example = "Cupcake Red Velvet")
    private String produto;

    @Schema(description = "Data em que o pedido foi cancelado", example = "2026-04-06T19:41:58")
    private LocalDateTime dataCancelamento;

    public PedidosCanceladosDto(Integer id, String produto, LocalDateTime dataCancelamento) {
        this.id = id;
        this.produto = produto;
        this.dataCancelamento = dataCancelamento;
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

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }
}
