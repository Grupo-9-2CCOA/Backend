package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;
import java.time.LocalDateTime;

@Schema(description = "Dados de um pedido reagendado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
