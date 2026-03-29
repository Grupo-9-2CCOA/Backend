package school.sptech.projeto_extensao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;
    private LocalDate dataPagamento;
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
