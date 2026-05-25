package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Representa o endereço de entrega de um cliente")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Schema(description = "ID do endereço", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Número do endereço", example = "10")
    private String numero;

    @Schema(description = "CEP do endereço", example = "01001000")
    private String cep;

    @Schema(description = "Cliente dono do endereço")
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
