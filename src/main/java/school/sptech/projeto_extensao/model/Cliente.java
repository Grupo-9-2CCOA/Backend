package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Representa um cliente da loja")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Schema(description = "ID do cliente", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nome completo do cliente", example = "Ana Silva")
    private String nome;

    @Schema(description = "CPF do cliente", example = "11111111111")
    private String cpf;

    @Schema(description = "Telefone de contato do cliente", example = "11999990001")
    private String telefone;

    @Column(name = "is_ativo")
    @Schema(description = "Indica se o cliente está ativo no sistema", example = "true")
    private Boolean ativo = true;
}
