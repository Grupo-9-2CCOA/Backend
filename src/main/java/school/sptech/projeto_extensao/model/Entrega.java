package school.sptech.projeto_extensao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;
    private LocalDate dataEntrega;
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
