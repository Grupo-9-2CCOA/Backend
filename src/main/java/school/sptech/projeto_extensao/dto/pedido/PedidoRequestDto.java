package school.sptech.projeto_extensao.dto.pedido;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoRequestDto {
    @NotBlank
    private String produto;
    @NotBlank
    private String descricao;
    @Positive
    private Double valor;
    private Boolean isAtivo;
    private Boolean isReagendado;
    @FutureOrPresent
    private LocalDateTime dataPedido;
    private Entrega entrega;
    private Pagamento pagamento;
    private Cliente cliente;
    private Endereco endereco;
    private String eventoGoogleCalendarId;

    public PedidoRequestDto(String produto, String descricao, Double valor,
                            LocalDateTime dataPedido, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco, String eventoGoogleCalendarId) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = true;
        this.isReagendado = false;
        this.dataPedido = dataPedido;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
        this.eventoGoogleCalendarId = eventoGoogleCalendarId;
    }
}
