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

@Schema(description = "Representa o status e informações de entrega de um pedido")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
