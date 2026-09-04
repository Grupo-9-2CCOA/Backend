package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Status de pagamento e entrega de um pedido")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoStatusRequestDto {

    @NotNull
    @Schema(description = "ID do status de pagamento", example = "2")
    private Integer pagamentoId;

    @NotNull
    @Schema(description = "ID do status de entrega", example = "2")
    private Integer entregaId;
}
