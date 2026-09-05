package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.projeto_extensao.dto.pedido.PedidoRelatorioDto;

import java.util.ArrayList;

@Schema(description = "Dados do relatório geral de vendas do período")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelatorioDto {
    @Schema(description = "Total de pedidos no período", example = "6")
    private Integer qtdPedidos;

    @Schema(description = "Diferença de pedidos em relação ao período anterior", example = "2")
    private Integer diferencaQtdPedidos;

    @Schema(description = "Total de pedidos cancelados no período", example = "1")
    private Integer pedidosCancelados;

    @Schema(description = "Diferença de cancelamentos em relação ao período anterior", example = "0")
    private Integer diferencaCanceladas;

    @Schema(description = "Total de pedidos reagendados no período", example = "1")
    private Integer pedidosReagendados;

    @Schema(description = "Diferença de reagendamentos em relação ao período anterior", example = "0")
    private Integer diferencaReagendadas;

    @Schema(description = "Total de clientes que atingiram o mínimo de compras para fidelização e compraram no período", example = "4")
    private Integer clientesFidelizados;

    @Schema(description = "Total de clientes não fidelizados que compraram no período", example = "2")
    private Integer clientesNaoFidelizados;

    @Schema(description = "Total de clientes novos (primeira compra) no período", example = "1")
    private Integer clientesNovos;

    @Schema(description = "Lista dos 15 pedidos mais recentes do período")
    private ArrayList<PedidoRelatorioDto> pedidos;
}
