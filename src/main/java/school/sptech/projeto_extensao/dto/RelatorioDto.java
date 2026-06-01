package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.projeto_extensao.dto.pedido.PedidoRelatorioDto;

import java.util.ArrayList;

@Schema(description = "Dados do relatório geral de vendas do período")
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

    @Schema(description = "Total de clientes fidelizados (mais de 1 compra) que compraram no período", example = "4")
    private Integer clientesFidelizados;

    @Schema(description = "Total de clientes novos (primeira compra) no período", example = "1")
    private Integer clientesNovos;

    @Schema(description = "Lista dos 15 pedidos mais recentes do período")
    private ArrayList<PedidoRelatorioDto> pedidos;

    public RelatorioDto(Integer qtdPedidos, Integer diferencaQtdPedidos, Integer pedidosCancelados, Integer diferencaCanceladas,
                        Integer pedidosReagendados, Integer diferencaReagendadas, Integer clientesFidelizados, Integer clientesNovos,
                        ArrayList<PedidoRelatorioDto> pedidos) {
        this.qtdPedidos = qtdPedidos;
        this.diferencaQtdPedidos = diferencaQtdPedidos;
        this.pedidosCancelados = pedidosCancelados;
        this.diferencaCanceladas = diferencaCanceladas;
        this.pedidosReagendados = pedidosReagendados;
        this.diferencaReagendadas = diferencaReagendadas;
        this.clientesFidelizados = clientesFidelizados;
        this.clientesNovos = clientesNovos;
        this.pedidos = pedidos;
    }



    public RelatorioDto() {
    }

    public Integer getQtdPedidos() {
        return qtdPedidos;
    }

    public void setQtdPedidos(Integer qtdPedidos) {
        this.qtdPedidos = qtdPedidos;
    }

    public Integer getDiferencaQtdPedidos() {
        return diferencaQtdPedidos;
    }

    public void setDiferencaQtdPedidos(Integer diferencaQtdPedidos) {
        this.diferencaQtdPedidos = diferencaQtdPedidos;
    }

    public Integer getPedidosCancelados() {
        return pedidosCancelados;
    }

    public void setPedidosCancelados(Integer pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }

    public Integer getDiferencaCanceladas() {
        return diferencaCanceladas;
    }

    public void setDiferencaCanceladas(Integer diferencaCanceladas) {
        this.diferencaCanceladas = diferencaCanceladas;
    }

    public Integer getPedidosReagendados() {
        return pedidosReagendados;
    }

    public void setPedidosReagendados(Integer pedidosReagendados) {
        this.pedidosReagendados = pedidosReagendados;
    }

    public Integer getDiferencaReagendadas() {
        return diferencaReagendadas;
    }

    public void setDiferencaReagendadas(Integer diferencaReagendadas) {
        this.diferencaReagendadas = diferencaReagendadas;
    }

    public Integer getClientesFidelizados() {
        return clientesFidelizados;
    }

    public void setClientesFidelizados(Integer clientesFidelizados) {
        this.clientesFidelizados = clientesFidelizados;
    }

    public Integer getClientesNovos() {
        return clientesNovos;
    }

    public void setClientesNovos(Integer clientesNovos) {
        this.clientesNovos = clientesNovos;
    }

    public ArrayList<PedidoRelatorioDto> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<PedidoRelatorioDto> pedidos) {
        this.pedidos = pedidos;
    }
}
