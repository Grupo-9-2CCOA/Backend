package school.sptech.projeto_extensao.model;

import jakarta.persistence.*;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String logradouro;
    private String numero;
    private String cep;

    @ManyToOne
    private Cliente cliente;

    public Endereco() {
    }

    public Endereco(Integer id, String logradouro, String numero, String cep, Cliente cliente) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
