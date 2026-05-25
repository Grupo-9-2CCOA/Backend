package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "Representa o status e informações de pagamento de um pedido")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
