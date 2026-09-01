package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Dados de um pedido cancelado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidosCanceladosDto {
    @Schema(description = "ID do pedido", example = "4")
    private Integer id;

    @Schema(description = "Nome do produto", example = "Cupcake Red Velvet")
    private String produto;

    @Schema(description = "Data em que o pedido foi cancelado", example = "2026-04-06T19:41:58")
    private LocalDateTime dataCancelamento;
}
