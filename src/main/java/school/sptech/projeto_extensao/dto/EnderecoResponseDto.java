package school.sptech.projeto_extensao.dto;

public class EnderecoResponseDto {
    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;

    public EnderecoResponseDto() {
    }

    public EnderecoResponseDto(Integer id, String logradouro, String numero, String complemento, String cep) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    public EnderecoResponseDto(String logradouro, String numero, String complemento, String cep) {
        this(null, logradouro, numero, complemento, cep);
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
