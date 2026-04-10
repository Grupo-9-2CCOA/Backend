package school.sptech.projeto_extensao.dto;

import java.util.ArrayList;
import java.util.List;

public class RelatorioDto {
    private Integer qtdPedidos;
    private Integer diferencaQtdPedidos;
    private Integer pedidosCancelados;
    private Integer diferencaCanceladas;
    private Integer pedidosReagendados;
    private Integer diferencaReagendadas;
    private Integer clientesFidelizados;
    private Integer clientesNovos;

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
