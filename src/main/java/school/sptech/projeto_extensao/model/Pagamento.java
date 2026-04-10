package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Schema(description = "Representa o status e informações de pagamento de um pedido")
@Entity
public class Pagamento {

    @Schema(description = "ID do pagamento", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Estado atual do pagamento", example = "Pago")
    private String estado;

    @Schema(description = "Data em que o pagamento foi realizado", example = "2026-04-06")
    private LocalDate dataPagamento;

    @Schema(description = "Data da última modificação do status do pagamento", example = "2026-04-06")
    private LocalDate dataModificacao;
    public Pagamento() {
    }

    public Pagamento(Integer id, String estado, LocalDate dataPagamento, LocalDate dataModificacao) {
        this.id = id;
        this.estado = estado;
        this.dataPagamento = dataPagamento;
        this.dataModificacao = dataModificacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}
