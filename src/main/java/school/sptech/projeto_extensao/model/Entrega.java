package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Schema(description = "Representa o status e informações de entrega de um pedido")
@Entity
public class Entrega {

    @Schema(description = "ID da entrega", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Estado atual da entrega", example = "Pendente")
    private String estado;

    @Schema(description = "Data em que a entrega foi realizada", example = "2026-04-06")
    private LocalDate dataEntrega;

    @Schema(description = "Data da última modificação do status da entrega", example = "2026-04-06")
    private LocalDate dataModificacao;
    public Entrega() {
    }

    public Entrega(Integer id, String estado, LocalDate dataEntrega, LocalDate dataModificacao) {
        this.id = id;
        this.estado = estado;
        this.dataEntrega = dataEntrega;
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

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}
