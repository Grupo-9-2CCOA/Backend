package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;
import java.time.LocalDateTime;

@Schema(description = "Dados resumidos de um pedido para exibição no relatório")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
