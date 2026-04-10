package school.sptech.projeto_extensao.dto;

import java.time.LocalDateTime;

public class PedidosCanceladosDto {
    private Integer id;
    private String produto;
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
